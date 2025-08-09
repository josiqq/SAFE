package com.josiqq.safe.controller;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bomberos")
public class BomberoController {

    @Autowired
    private BomberoService bomberoService;

    @Autowired
    private RolService rolService; // Para poder seleccionar roles en el formulario

    @GetMapping
    public String listarBomberos(Model model) {
        List<Bombero> bomberos = bomberoService.findAll();
        model.addAttribute("bomberos", bomberos);
        return "bomberos/lista"; // /templates/bomberos/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoBombero(Model model) {
        model.addAttribute("bombero", new Bombero());
        model.addAttribute("roles", rolService.findAll()); // Pasamos los roles a la vista
        return "bomberos/formulario"; // /templates/bomberos/formulario.html
    }

    @PostMapping
    public String guardarBombero(@ModelAttribute("bombero") Bombero bombero) {
        bomberoService.save(bombero);
        return "redirect:/bomberos";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Bombero> bomberoOpt = bomberoService.findById(id);
        if (bomberoOpt.isEmpty()) {
            return "redirect:/bomberos";
        }
        model.addAttribute("bombero", bomberoOpt.get());
        model.addAttribute("roles", rolService.findAll());
        return "bomberos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarBombero(@PathVariable Long id, @ModelAttribute("bombero") Bombero bombero) {
        bombero.setId(id);
        bomberoService.save(bombero);
        return "redirect:/bomberos";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarBombero(@PathVariable Long id) {
        bomberoService.deleteById(id);
        return "redirect:/bomberos";
    }
}