package com.josiqq.safe.controller;

import com.josiqq.safe.model.Mantenimiento;
import com.josiqq.safe.service.EquipoService;
import com.josiqq.safe.service.MantenimientoService;
import com.josiqq.safe.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public String listarMantenimientos(Model model) {
        model.addAttribute("mantenimientos", mantenimientoService.findAll());
        return "mantenimientos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoMantenimiento(Model model) {
        model.addAttribute("mantenimiento", new Mantenimiento());
        model.addAttribute("vehiculos", vehiculoService.findAll());
        model.addAttribute("equipos", equipoService.findAll());
        return "mantenimientos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicionDeMantenimiento(@PathVariable Long id, Model model) {
        Mantenimiento mantenimiento = mantenimientoService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mantenimiento no encontrado"));
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("vehiculos", vehiculoService.findAll());
        model.addAttribute("equipos", equipoService.findAll());
        return "mantenimientos/formulario";
    }

    @PostMapping
    public String guardarMantenimiento(@ModelAttribute("mantenimiento") Mantenimiento mantenimiento) {
        mantenimientoService.save(mantenimiento);
        return "redirect:/mantenimientos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarMantenimiento(@PathVariable Long id) {
        mantenimientoService.deleteById(id);
        return "redirect:/mantenimientos";
    }
}
