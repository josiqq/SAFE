package com.josiqq.safe.controller;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.Comunicacion;
import com.josiqq.safe.models.Incidente;
import com.josiqq.safe.service.ComunicacionService;
import com.josiqq.safe.service.IncidenteService;
import com.josiqq.safe.service.BomberoService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/incidentes/{incidenteId}/comunicaciones")
public class ComunicacionController {

    @Autowired
    private ComunicacionService comunicacionService;

    @Autowired
    private BomberoService bomberoService;

    @Autowired
    private IncidenteService incidenteService;

    @GetMapping("/nueva")
    public String mostrarFormularioDeNuevaComunicacion(@PathVariable Long incidenteId, Model model) {
        Incidente incidente = incidenteService.findById(incidenteId).orElseThrow();
        Comunicacion comunicacion = new Comunicacion();
        comunicacion.setIncidente(incidente);

        model.addAttribute("comunicacion", comunicacion);
        return "comunicaciones/formulario"; // /templates/comunicaciones/formulario.html
    }

    @PostMapping
    public String guardarComunicacion(@PathVariable Long incidenteId,
            @ModelAttribute("comunicacion") Comunicacion comunicacion,
            Principal principal) {

        Incidente incidente = incidenteService.findById(incidenteId).orElseThrow();
        comunicacion.setIncidente(incidente);

        String username = principal.getName();
        Bombero responsable = bomberoService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario responsable no encontrado"));

        comunicacion.setResponsable(responsable);
        comunicacionService.save(comunicacion);

        return "redirect:/incidentes/" + incidenteId;
    }
}