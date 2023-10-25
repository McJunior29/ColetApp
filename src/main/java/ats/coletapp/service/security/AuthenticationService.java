package ats.coletapp.service.security;

import java.util.List;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ats.coletapp.controller.dto.security.AuthenticationRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.controller.dto.security.RegisterPersonRequest;
import ats.coletapp.controller.dto.security.RegisterUserRequest;
import ats.coletapp.controller.dto.user.UserResponseDTO;
import ats.coletapp.exceptions.ConflictException;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.model.Person;
import ats.coletapp.model.User;
import ats.coletapp.repository.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }


    public AuthenticationResponse register(RegisterUserRequest request, PermissionTypeEnum role) {

        if (userRepository.existsByLogin(request.email())) {
            throw new ConflictException("This email is already being used.");
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        Person person = createPerson(request.personRequest());

        User user = User.builder()
                .login(request.email())
                .password(passwordEncoder.encode(request.password()))
                .person(person)
                .permission(List.of(role))
                .build();
        userRepository.save(user);
        userResponseDTO.setId(user.getId());
        userResponseDTO.setLogin(user.getLogin());
        userResponseDTO.setPersonDTO(user.getPerson());
        String token = jwtService.generateToken(user);

        return createRegisterResponse(token, userResponseDTO);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByLogin(request.email())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found"));

        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                  request.email(),
                  request.password()));

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setLogin(user.getLogin());
        userResponseDTO.setPermission(user.getPermission());
        userResponseDTO.setPersonDTO(user.getPerson());
        String token = jwtService.generateToken(user);
        
        return createRegisterResponse(token, userResponseDTO);
    }

    private Person createPerson(RegisterPersonRequest request) {

        return Person.builder()
                .name(request.name())
                .email(request.email())
                .build();
    }

    private AuthenticationResponse createRegisterResponse(String token, UserResponseDTO user) {

        return new AuthenticationResponse(token, user);

    }
}
