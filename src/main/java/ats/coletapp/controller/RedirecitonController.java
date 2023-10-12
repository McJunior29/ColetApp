package ats.coletapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping()
public class RedirecitonController {
    
    @GetMapping
    @RequestBody
    public String loadIndex(){
        return "index";
    }

    @GetMapping("/cadUser")
    @RequestBody
    public String cadUser(){
        return "pages/register/index";
    }
}
