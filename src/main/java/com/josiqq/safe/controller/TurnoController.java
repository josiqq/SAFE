package com.josiqq.safe.controller;

import com.josiqq.safe.models.Turno;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    
    @Autowired
    private BomberoService bomberoService; // Para asignar bomberos a un turno

    @GetMapping
    public String listarTurnos(Model model) {
        model.addAttribute("turnos", turnoService.findAll());
        return "turnos/lista"; // /templates/turnos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoTurno(Model model) {
        model.addAttribute("turno", new Turno());
        model.addAttribute("bomberos", bomberoService.findAll()); // Pasamos la lista de bomberos
        return "turnos/formulario"; // /templates/turnos/formulario.html
    }

    @PostMapping
    public String guardarTurno(@ModelAttribute("turno") Turno turno) {
        turnoService.save(turno);
        return "redirect:/turnos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Turno> turnoOpt = turnoService.findById(id);
        if (turnoOpt.isEmpty()) {
            return "redirect:/turnos";
        }
        model.addAttribute("turno", turnoOpt.get());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "turnos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarTurno(@PathVariable Long id, @ModelAttribute("turno") Turno turno) {
        turno.setId(id);
        turnoService.save(turno);
        return "redirect:/turnos";
    }
}