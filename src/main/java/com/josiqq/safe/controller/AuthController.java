package com.josiqq.safe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String mostrarFormularioDeLogin() {
        return "login";
    }
    
    @GetMapping("/")
    public String mostrarPaginaDeInicio() {
        return "home"; // Devuelve el nombre de la plantilla: /templates/index.html
    }
}