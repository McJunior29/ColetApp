package ats.coletapp.service.user; 

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ats.coletapp.controller.dto.person.PersonRequest;
import ats.coletapp.controller.dto.person.PersonUpdateRequest;
import ats.coletapp.exceptions.ConfirmPasswordException;
import ats.coletapp.exceptions.ConflictException;
import ats.coletapp.exceptions.ResourceNotFoundException;
import ats.coletapp.model.Address;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.model.Person;
import ats.coletapp.model.User;
import ats.coletapp.repository.UserRepository;
import ats.coletapp.service.address.AddressService;
import ats.coletapp.service.person.PersonService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final PersonService personService;
    private final AddressService addressService;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JavaMailSender emailSender,PasswordEncoder passwordEncoder,PersonService personService, AddressService addressService ) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
    }

    public User save(User user){
        return this.userRepository.save(user);
    }

    public User createUser(PersonRequest personRequest) {

        if (existsByLogin(personRequest.email())) {
            throw new ConflictException("The  email provided is already being used.");
        }
        if(!personRequest.password().equals(personRequest.confirmPassword())){
            throw new ConfirmPasswordException("Passwords are different.");
        }

        User newUser = User.builder().login(personRequest.email())
                        .password(passwordEncoder.encode(personRequest.password()))
                        .person(this.personService.createdPerson(personRequest))
                        .permission(List.of(PermissionTypeEnum.ROLE_USER))
                        .build();
        return this.userRepository.save(newUser);
    }

    
    public List<User> search() {
        return this.userRepository.findAll();
    }

    public User searchByID(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user was not found in the database, " +
                        "please check the registered user."));
    }

    public User findByLogin(String login) {
        return this.userRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("No user with that email was found in the database, " +
                        "check the registered users."));
    }


    public boolean userRecoveryPassword(String userRecoveryLoginDTO ) throws MessagingException {
        
        try{
        User user = findByLogin(userRecoveryLoginDTO);
        String email = user.getLogin();
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        
        String verificationCode = generateVerificationCode();
        if (user.getVerificationCode() != null) {
            while (user.getVerificationCode().equals(verificationCode)) {
                verificationCode = generateVerificationCode();
            }
        }

        user.setVerificationCode(verificationCode);
        user = this.saveUser(user);

        String htmlContent = "<html><div style='margin-left: 50px; margin-right: 50px;'><body style='background-color: #f9a400; color: white; text-align: center; padding-top:20px;'>"
                + "<h2 style='color: white;font-size: 24px;'>Envio de Senha</h2>"
                + "<p style='color: white;font-size: 18px;'>Olá,</p>"
                + "<p style='color: white;font-size: 18px;'>Aqui está a senha de acesso ao sistema:</p>"
                + "<p style='font-size: 26px; font-weight: bold; color: white;'>" + verificationCode + "</p>"
                + "<p style='font-size: 18px; color: white;padding-bottom:20px;'>Siga os passos no sistema e bom uso.</p>"
                + "</body></div></html>";

        helper.setTo(email);
        helper.setSubject("Email para recuperação de Senha");
        helper.setText("", htmlContent);
        helper.setFrom("testes.caninde@darmlabs.ifce.edu.br");
        
        emailSender.send(mimeMessage);

        return true;
            
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email not sent");
        }
    }

    public User verificationCode(String code){
        return this.findByVerificationCode(code);
    }

    public User findByVerificationCode(String code) {
        User user = this.userRepository.findByVerificationCode(code)
        .orElseThrow(() -> new ResourceNotFoundException("No user with that verification code was found in the database, " +
                        "check the registered users."));
        return user;
    }

    @Transactional
    public User update(Long id, PersonUpdateRequest requestDTO) {
        
        User oldUser = this.searchByID(id);

        if (!oldUser.getLogin().equals(requestDTO.email())
                && this.existsByLogin(requestDTO.email())) {
            throw new ConflictException("The email provided is already being used.");
        }

        User newUser = oldUser;
        newUser.setLogin(requestDTO.email());
        newUser.setPassword(passwordEncoder.encode(requestDTO.password()));    

        Address address = newUser.getPerson().getAddress();

        address.setStreet(requestDTO.street());
        address.setNumber(requestDTO.number());
        address.setNeighborhood(requestDTO.neighborhood());
        this.addressService.save(address);

        Person person = newUser.getPerson();

        person.setEmail(requestDTO.email());
        person.setName(requestDTO.name());
        person.setAddress(address);
        this.personService.savePerson(person);

        newUser.setPerson(person);

        return userRepository.save(newUser);
    }

    @Transactional
    public Boolean delete(User userModel) {
        User user = this.searchByID(userModel.getId());      
        this.userRepository.delete(user);
        return true;
    }

    public User searchByLogin(String login) {
        return this.findByLogin(login);
    }

    public boolean existsByLogin(String login) {
        Optional<User> exist = this.userRepository.findByLogin(login);

        return exist.isPresent();
    }

    public boolean existsByID(Long id) {
        Optional<User> exist = this.userRepository.findById(id);

        return exist.isPresent();
    }

    public String generateVerificationCode() {
        String CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);

        sb.append(CHARSET.charAt(random.nextInt(26))); 
        sb.append(CHARSET.charAt(26 + random.nextInt(26)));
        sb.append(CHARSET.charAt(52 + random.nextInt(10))); 
        sb.append(CHARSET.charAt(62 + random.nextInt(6))); 

        for (int i = 4; i < 8; i++) {
            int index = random.nextInt(CHARSET.length());
            sb.append(CHARSET.charAt(index));
        }

        String password = sb.toString();
        char[] passwordArray = password.toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }
        return new String(passwordArray);
    }


    public User updatePassword(User user, String password) {

        User userModel = this.searchByID(user.getId());
        userModel.setPassword(passwordEncoder.encode(password));

        this.saveUser(userModel);
        
        return userModel;
    }

    public User findByPersonId(Long id) {
        return this.userRepository.findByPersonId(id)
                .orElseThrow(() -> new ResourceNotFoundException("The user was not found in the database, " +
                        "please check the registered user."));
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteByPersonId(Long id) {
        this.userRepository.deleteByPersonId(id);
    }
}

