package ats.coletapp.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ats.coletapp.controller.dto.security.AuthenticationRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.controller.dto.security.RegisterUserRequest;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.service.security.AuthenticationService;
;



@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody @Valid RegisterUserRequest request) {

        return ResponseEntity.ok(authService.register(request, PermissionTypeEnum.ROLE_ADMIN));

    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.ok(authService.authenticate(request));

    }

}
