package com.josiqq.safe.controller;

import com.josiqq.safe.model.DenunciaPublica;
import com.josiqq.safe.service.DenunciaPublicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/denuncias")
public class DenunciaPublicaController {

    @Autowired
    private DenunciaPublicaService denunciaService;

    @GetMapping
    public String listarDenuncias(Model model) {
        // Por defecto, mostrar las pendientes
        model.addAttribute("denuncias", denunciaService.findByEstado("pendiente"));
        return "denuncias/lista"; // /templates/denuncias/lista.html
    }

    @GetMapping("/{id}")
    public String verDetalleDenuncia(@PathVariable Long id, Model model) {
        model.addAttribute("denuncia", denunciaService.findById(id).orElseThrow());
        return "denuncias/detalle"; // /templates/denuncias/detalle.html
    }

    @PostMapping("/{id}/atender")
    public String marcarComoAtendida(@PathVariable Long id) {
        // Lógica para cambiar el estado de la denuncia
        DenunciaPublica denuncia = denunciaService.findById(id).orElseThrow();
        denuncia.setEstado("atendida");
        denunciaService.save(denuncia);
        return "redirect:/denuncias";
    }
}