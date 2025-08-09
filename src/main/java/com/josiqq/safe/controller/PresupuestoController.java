package com.josiqq.safe.controller;

import com.josiqq.safe.models.Presupuesto;
import com.josiqq.safe.service.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    @GetMapping
    public String listarPresupuestos(Model model) {
        model.addAttribute("presupuestos", presupuestoService.findAll());
        return "presupuestos/lista"; // /templates/presupuestos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoPresupuesto(Model model) {
        model.addAttribute("presupuesto", new Presupuesto());
        return "presupuestos/formulario"; // /templates/presupuestos/formulario.html
    }

    @PostMapping
    public String guardarPresupuesto(@ModelAttribute("presupuesto") Presupuesto presupuesto) {
        presupuestoService.save(presupuesto);
        return "redirect:/presupuestos";
    }
}