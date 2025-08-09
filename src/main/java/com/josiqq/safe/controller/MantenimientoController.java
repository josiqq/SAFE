package com.josiqq.safe.controller;

import com.josiqq.safe.models.Mantenimiento;
import com.josiqq.safe.service.EquipoService;
import com.josiqq.safe.service.MantenimientoService;
import com.josiqq.safe.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "mantenimientos/lista"; // /templates/mantenimientos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoMantenimiento(Model model) {
        model.addAttribute("mantenimiento", new Mantenimiento());
        model.addAttribute("vehiculos", vehiculoService.findAll());
        model.addAttribute("equipos", equipoService.findAll());
        return "mantenimientos/formulario"; // /templates/mantenimientos/formulario.html
    }

    @PostMapping
    public String guardarMantenimiento(@ModelAttribute("mantenimiento") Mantenimiento mantenimiento) {
        mantenimientoService.save(mantenimiento);
        // Aquí también deberías actualizar el estado del vehículo/equipo si es necesario
        return "redirect:/mantenimientos";
    }
}