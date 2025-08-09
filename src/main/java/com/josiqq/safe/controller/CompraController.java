package com.josiqq.safe.controller;

import com.josiqq.safe.models.Compra;
import com.josiqq.safe.service.CompraService;
import com.josiqq.safe.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private ProveedorService proveedorService; // Para seleccionar el proveedor

    @GetMapping
    public String listarCompras(Model model) {
        model.addAttribute("compras", compraService.findAll());
        return "compras/lista"; // /templates/compras/lista.html
    }

    @GetMapping("/nueva")
    public String mostrarFormularioDeNuevaCompra(Model model) {
        model.addAttribute("compra", new Compra());
        model.addAttribute("proveedores", proveedorService.findAll()); // Pasamos los proveedores
        return "compras/formulario"; // /templates/compras/formulario.html
    }

    @PostMapping
    public String guardarCompra(@ModelAttribute("compra") Compra compra) {
        compraService.save(compra);
        return "redirect:/compras";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarCompra(@PathVariable Long id) {
        compraService.deleteById(id);
        return "redirect:/compras";
    }
}