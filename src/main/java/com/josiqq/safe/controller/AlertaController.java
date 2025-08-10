package com.josiqq.safe.controller;

import com.josiqq.safe.model.Alerta;
import com.josiqq.safe.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.josiqq.safe.service.RolService;
import com.josiqq.safe.service.BomberoService;

import java.util.List;

@Controller
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private RolService rolService;

    @Autowired
    private BomberoService bomberoService;

    @GetMapping
    public String listarAlertas(Model model) {
        List<Alerta> alertas = alertaService.findAll();
        model.addAttribute("alertas", alertas);
        return "alertas/lista"; // Corresponde a /templates/alertas/lista.html
    }

    @GetMapping("/nueva")
    public String mostrarFormularioDeNuevaAlerta(Model model) {
        model.addAttribute("alerta", new Alerta());
        model.addAttribute("roles", rolService.findAll());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "alertas/formulario"; // Corresponde a /templates/alertas/formulario.html
    }

    @PostMapping
    public String guardarAlerta(@ModelAttribute("alerta") Alerta alerta) {
        alertaService.save(alerta);
        return "redirect:/alertas";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarAlerta(@PathVariable Long id) {
        alertaService.deleteById(id);
        return "redirect:/alertas";
    }
}