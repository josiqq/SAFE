package com.josiqq.safe.controller;

import com.josiqq.safe.model.Capacitacion;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.CapacitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;

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
        return "capacitaciones/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioDeNuevaCapacitacion(Model model) {
        // Inicializa el Set de participantes para evitar NullPointerException
        Capacitacion nuevaCapacitacion = new Capacitacion();
        nuevaCapacitacion.setParticipantes(new HashSet<>());
        
        model.addAttribute("capacitacion", nuevaCapacitacion);
        model.addAttribute("bomberos", bomberoService.findAll());
        return "capacitaciones/formulario";
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
        // Corrige la ruta de retorno
        return "capacitaciones/formulario";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarCapacitacion(@PathVariable Long id) {
        capacitacionService.deleteById(id);
        return "redirect:/capacitaciones";
    }
}