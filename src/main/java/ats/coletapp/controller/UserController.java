package ats.coletapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ats.coletapp.controller.dto.person.PersonRequest;
import ats.coletapp.controller.dto.person.PersonUpdateRequest;
import ats.coletapp.controller.dto.security.AuthenticationRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.controller.dto.user.UserRecoveryLoginDTO;
import ats.coletapp.service.security.AuthenticationService;
import ats.coletapp.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    private final AuthenticationService authService;

    public UserController(UserService userService, AuthenticationService authService){
        this.userService = userService;
        this.authService = authService;
    }
    
    @PostMapping("/cadUser")
    public String cadastrarUsuario(PersonRequest request, Model model) {
        try {
            this.userService.createUser(request);
            return "redirect:/";  
        } catch (Exception e) { 
            model.addAttribute("erro", "Email inválido!");
            return "redirect:/cadUser";
        }      
    }

    @PostMapping("/updateUser")
    public String atualizarUsuario(@ModelAttribute PersonUpdateRequest personRequest, Model model,HttpSession session) {
        AuthenticationResponse auth = (AuthenticationResponse) session.getAttribute("auth");
        if (auth == null || !auth.user().getId().equals(personRequest.userId())) {
            return "redirect:/";
        }
        this.userService.update(personRequest.userId(), personRequest);
        AuthenticationRequest request = new AuthenticationRequest(personRequest.email(), personRequest.password());
        AuthenticationResponse response = this.authService.authenticate(request);

        session.setAttribute("auth", response );

        return "redirect:/home";
    }

    @PostMapping("/recoveryPassword")
    public String recoveryPassword(@ModelAttribute UserRecoveryLoginDTO userRecoveryLoginDTO) throws MessagingException{
        this.userService.userRecoveryPassword(userRecoveryLoginDTO);
        return "redirect:/";
    }
}
