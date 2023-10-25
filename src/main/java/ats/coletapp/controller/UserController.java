package ats.coletapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ats.coletapp.controller.dto.person.PersonRequest;
import ats.coletapp.controller.dto.person.PersonUpdateRequest;
import ats.coletapp.controller.dto.security.AuthenticationRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.exceptions.ResourceNotFoundException;
import ats.coletapp.model.User;
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
        } catch (ResourceNotFoundException e) { 
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
    public String recoveryPassword(@RequestParam("email") String email, Model model) throws MessagingException{

        do {
            if (this.userService.userRecoveryPassword(email)) {
                return "redirect:/pin";
            } else {
                model.addAttribute("erro", "Email inválido");
                return "redirect:/";
            }
        } while (!this.userService.userRecoveryPassword(email));
    }

    @PostMapping("/verificationCode")
    public String verificationCode(@RequestParam("verificationCode") String verificationCode, Model model,HttpSession session ){
        try{
        User user = this.userService.verificationCode(verificationCode);
        session.setAttribute("user", user);

        return "pages/forgot_password/password";
        }catch(ResourceNotFoundException e ){
            model.addAttribute("erro", "Codigo inválido");
            return "pages/forgot_password/pin";
        }
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("password") String password, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        User userUpdate = this.userService.updatePassword(user, password);
        session.setAttribute("user", userUpdate);
        return "redirect:/";
    }
}
