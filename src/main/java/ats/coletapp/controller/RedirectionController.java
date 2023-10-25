package ats.coletapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.model.Address;
import ats.coletapp.model.Complaint;
import ats.coletapp.model.Schedules;
import ats.coletapp.service.complaint.ComplaintService;
import ats.coletapp.service.routes.RoutesService;
import ats.coletapp.service.routes.SchedulesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping()
public class RedirectionController {

    private final ComplaintService complaintService;
    private final RoutesService routesService;
    private final SchedulesService schedulesService;

    public RedirectionController(ComplaintService complaintService, RoutesService routesService, SchedulesService schedulesService) {
        this.complaintService = complaintService;
        this.routesService = routesService;
        this.schedulesService = schedulesService;
    }
    
    @GetMapping
    public String loadIndex() {
        return "index";
    }

    @GetMapping("/cadUser")
    public String cadUser() {
        return "pages/register/index";
    }

    @GetMapping("/pin")
    public String pin() {   
        return "pages/forgot_password/pin";
    }

    @GetMapping("/add_report")
    public String addReport(HttpSession session) {
        AuthenticationResponse auth = (AuthenticationResponse) session.getAttribute("auth");
        if (auth == null) {
            return "redirect:/";
        }

        return "pages/add_report/index";
    }

    @GetMapping("/profile")
    public String profileUser(HttpSession session, Model model) {
        AuthenticationResponse auth = (AuthenticationResponse) session.getAttribute("auth");
        if (auth == null) {
            return "redirect:/";
        }
        model.addAttribute("user", auth.user());
        return "pages/profile/index";
    }

    @GetMapping("/forgot_password")
    public String forgotPassword(){
        return "pages/forgot_password/index";
    }

    
    @GetMapping("/routes")
    public String listSchedules(Model model) {
        List<Schedules> schedules = schedulesService.getAllSchedules();
        Map<Address, List<Schedules>> groupedSchedules = schedulesService.groupSchedulesByAddress(schedules);
        model.addAttribute("groupedSchedules", groupedSchedules);
        return "pages/routes/index";
    }

    @GetMapping("/login{logout}")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        
        AuthenticationResponse auth = (AuthenticationResponse) session.getAttribute("auth");

        if (auth == null) {
            return "redirect:/";
        }

        List<Complaint> complaints = complaintService.getAllComplaint();

        model.addAttribute("auth", auth.user());
        model.addAttribute("listComplaints", complaints);
        return "pages/home/index";
    }

}
