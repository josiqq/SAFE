package com.josiqq.safe.controller;

import com.josiqq.safe.models.Proveedor;
import com.josiqq.safe.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.findAll());
        return "proveedores/lista"; // /templates/proveedores/lista.html
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoProveedor(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/formulario"; // /templates/proveedores/formulario.html
    }

    @PostMapping
    public String guardarProveedor(@ModelAttribute("proveedor") Proveedor proveedor) {
        proveedorService.save(proveedor);
        return "redirect:/proveedores";
    }
    
    @GetMapping("/{id}/eliminar")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.deleteById(id);
        return "redirect:/proveedores";
    }
}