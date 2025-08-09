package com.josiqq.safe.controller;

import com.josiqq.safe.models.Rol;
import com.josiqq.safe.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolService.findAll());
        return "roles/lista"; // /templates/roles/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoRol(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/formulario"; // /templates/roles/formulario.html
    }

    @PostMapping
    public String guardarRol(@ModelAttribute("rol") Rol rol) {
        rolService.save(rol);
        return "redirect:/roles";
    }
}