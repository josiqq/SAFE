package com.josiqq.safe.controller;

import com.josiqq.safe.models.Equipo;
import com.josiqq.safe.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoService.findAll());
        return "equipos/lista"; // /templates/equipos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoEquipo(Model model) {
        model.addAttribute("equipo", new Equipo());
        return "equipos/formulario"; // /templates/equipos/formulario.html
    }

    @PostMapping
    public String guardarEquipo(@ModelAttribute("equipo") Equipo equipo) {
        equipoService.save(equipo);
        return "redirect:/equipos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Equipo> equipoOpt = equipoService.findById(id);
        if (equipoOpt.isEmpty()) {
            return "redirect:/equipos";
        }
        model.addAttribute("equipo", equipoOpt.get());
        return "equipos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarEquipo(@PathVariable Long id, @ModelAttribute("equipo") Equipo equipo) {
        equipo.setId(id);
        equipoService.save(equipo);
        return "redirect:/equipos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarEquipo(@PathVariable Long id) {
        equipoService.deleteById(id);
        return "redirect:/equipos";
    }
}