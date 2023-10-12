package ats.coletapp.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ats.coletapp.controller.dto.security.AuthenticationRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.controller.dto.security.RegisterUserRequest;
import ats.coletapp.model.Complaint;
import ats.coletapp.model.Enum.PermissionTypeEnum;
import ats.coletapp.service.complaint.ComplaintService;
import ats.coletapp.service.security.AuthenticationService;




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
    public String authenticate(AuthenticationRequest request, Model model) {
        AuthenticationResponse auth = authService.authenticate(request);
        List<Complaint> complaints = complaintService.getAllComplaint();
        model.addAttribute("listComplaints", complaints); 
        return "pages/home/index";
    }

}
