package ats.coletapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ats.coletapp.controller.dto.security.AuthenticationRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.controller.dto.security.RegisterUserRequest;
import ats.coletapp.model.Complaint;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.service.complaint.ComplaintService;
import ats.coletapp.service.security.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ComplaintService complaintService;

    public AuthenticationController(AuthenticationService authService, ComplaintService complaintService) {
        this.authService = authService;
        this.complaintService = complaintService;
    }


    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody @Valid RegisterUserRequest request) {

        return ResponseEntity.ok(authService.register(request, PermissionTypeEnum.ROLE_ADMIN));

    }

    @PostMapping("/login")
    public String authenticate(AuthenticationRequest request, Model model, HttpSession session) {
        try {
            AuthenticationResponse auth = authService.authenticate(request);
            List<Complaint> complaints = complaintService.getAllComplaint();
            session.setAttribute("auth", auth);
            session.setAttribute("listComplaints", complaints);
            return "redirect:/home";
        } catch (AuthenticationCredentialsNotFoundException e) {
            model.addAttribute("erro", "Credenciais inválidas!");
            return "redirect:/";
        }
    }
}
