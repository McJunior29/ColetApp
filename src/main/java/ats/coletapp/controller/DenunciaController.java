package ats.coletapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ats.coletapp.controller.dto.complaint.ComplaintRequest;
import ats.coletapp.controller.dto.security.AuthenticationResponse;
import ats.coletapp.service.complaint.ComplaintService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DenunciaController {

    private final ComplaintService complaintService;

    public DenunciaController(ComplaintService complaintService){
        this.complaintService = complaintService;
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("imagem") MultipartFile file,
                                   @RequestParam("descricao") String descricao,
                                   @RequestParam("rua") String rua,
                                   @RequestParam("numero") String numero,
                                   @RequestParam("bairro") String bairro,
                                   @RequestParam("complemento") String complemento,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session, Model model) {
        if (!file.isEmpty()) {
            try {
                String uploadDirectory = "src/main/resources/static/img/denuncias/";

                AuthenticationResponse auth = (AuthenticationResponse) session.getAttribute("auth");

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
                Files.write(path, bytes);
                ComplaintRequest complaintRequest = new ComplaintRequest("../../img/denuncias/"+file.getOriginalFilename(), descricao, rua, bairro, numero, complemento, auth.user().getId());
                this.complaintService.createComplaint(complaintRequest);
                
                redirectAttributes.addFlashAttribute("message", "Arquivo enviado com sucesso!");
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Falha ao enviar o arquivo.");
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "O arquivo está vazio.");
        }
        return "redirect:/home";
    }
}
