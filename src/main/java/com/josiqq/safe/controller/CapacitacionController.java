package com.josiqq.safe.controller;

import com.josiqq.safe.service.CapacitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.josiqq.safe.model.Capacitacion;
import com.josiqq.safe.service.BomberoService;

@Controller
@RequestMapping("/capacitaciones")
public class CapacitacionController {

    @Autowired
    private CapacitacionService capacitacionService;

    @Autowired
    private BomberoService bomberoService;

    @GetMapping
    public String listarCapacitaciones(Model model) {
        model.addAttribute("capacitaciones", capacitacionService.findAll());
        return "capacitaciones/lista"; // /templates/capacitaciones/lista.html
    }

    @GetMapping("/nueva")
    public String mostrarFormularioDeNuevaCapacitacion(Model model) {
        model.addAttribute("capacitacion", new Capacitacion());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "capacitaciones/formulario"; // /templates/capacitaciones/formulario.html
    }

    @PostMapping
    public String guardarCapacitacion(@ModelAttribute("capacitacion") Capacitacion capacitacion) {
        capacitacionService.save(capacitacion);
        return "redirect:/capacitaciones";
    }
    
    @GetMapping("/{id}/editar")
    public String editarCapacitacion(@PathVariable Long id, Model model) {
        Capacitacion capacitacion = capacitacionService.obtenerConParticipantes(id);
        model.addAttribute("capacitacion", capacitacion);
        return "capacitacion/formulario";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarCapacitacion(@PathVariable Long id) {
        capacitacionService.deleteById(id);
        return "redirect:/capacitaciones";
    }
}
