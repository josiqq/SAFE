package com.josiqq.safe.controller;

import com.josiqq.safe.models.Vehiculo;
import com.josiqq.safe.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public String listarVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoService.findAll());
        return "vehiculos/lista"; // /templates/vehiculos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculos/formulario"; // /templates/vehiculos/formulario.html
    }

    @PostMapping
    public String guardarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo) {
        vehiculoService.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Vehiculo> vehiculoOpt = vehiculoService.findById(id);
        if (vehiculoOpt.isEmpty()) {
            return "redirect:/vehiculos";
        }
        model.addAttribute("vehiculo", vehiculoOpt.get());
        return "vehiculos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarVehiculo(@PathVariable Long id, @ModelAttribute("vehiculo") Vehiculo vehiculo) {
        vehiculo.setId(id);
        vehiculoService.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.deleteById(id);
        return "redirect:/vehiculos";
    }
}