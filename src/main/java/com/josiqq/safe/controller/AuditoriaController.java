package com.josiqq.safe.controller;

import com.josiqq.safe.models.Auditoria;
import com.josiqq.safe.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping
    public String listarEventosDeAuditoria(@RequestParam(value = "usuario", required = false) String usuario, Model model) {
        List<Auditoria> eventos;
        if (usuario != null && !usuario.isEmpty()) {
            eventos = auditoriaService.findByUsuario(usuario);
        } else {
            eventos = auditoriaService.findAll();
        }
        model.addAttribute("eventos", eventos);
        return "auditoria/lista"; // /templates/auditoria/lista.html
    }
}