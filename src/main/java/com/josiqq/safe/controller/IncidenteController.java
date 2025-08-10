package com.josiqq.safe.controller;

import com.josiqq.safe.model.Incidente;
import com.josiqq.safe.service.IncidenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/incidentes")
public class IncidenteController {

    @Autowired
    private IncidenteService incidenteService;

    /**
     * READ (All) - Muestra una lista de todos los incidentes.
     * GET /incidentes
     */
    @GetMapping
    public String listarIncidentes(Model model) {
        List<Incidente> listaIncidentes = incidenteService.findAll();
        model.addAttribute("incidentes", listaIncidentes);
        return "incidentes/lista";
    }

    /**
     * CREATE (Step 1: Show form) - Muestra el formulario para crear un nuevo incidente.
     * GET /incidentes/nuevo
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoIncidente(Model model) {
        model.addAttribute("incidente", new Incidente());
        return "incidentes/formulario";
    }

    /**
     * CREATE (Step 2: Process form) - Procesa los datos del formulario y guarda el nuevo incidente.
     * POST /incidentes
     */
    @PostMapping
    public String guardarIncidente(@ModelAttribute("incidente") Incidente incidente) {
        incidenteService.save(incidente);
        return "redirect:/incidentes";
    }

    /**
     * READ (One) - Muestra los detalles de un incidente específico.
     * GET /incidentes/{id}
     */
    @GetMapping("/{id}")
    public String verDetalleIncidente(@PathVariable Long id, Model model) {
        Optional<Incidente> incidenteOpt = incidenteService.findById(id);
        if (incidenteOpt.isEmpty()) {
            return "redirect:/incidentes"; // O una página de error 404
        }
        model.addAttribute("incidente", incidenteOpt.get());
        return "incidentes/detalle"; // Necesitarás crear /templates/incidentes/detalle.html
    }

    /**
     * UPDATE (Step 1: Show form) - Muestra el formulario para editar un incidente existente.
     * GET /incidentes/{id}/editar
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Incidente> incidenteOpt = incidenteService.findById(id);
        if (incidenteOpt.isEmpty()) {
            return "redirect:/incidentes";
        }
        model.addAttribute("incidente", incidenteOpt.get());
        return "incidentes/formulario"; // Reutilizamos la misma vista del formulario
    }

    /**
     * UPDATE (Step 2: Process form) - Procesa los datos del formulario de edición.
     * POST /incidentes/{id}
     */
    @PostMapping("/{id}")
    public String actualizarIncidente(@PathVariable Long id, @ModelAttribute("incidente") Incidente incidente) {
        incidente.setId(id); // Aseguramos que estamos actualizando el incidente correcto
        incidenteService.save(incidente);
        return "redirect:/incidentes";
    }

    /**
     * DELETE - Elimina un incidente.
     * GET /incidentes/{id}/eliminar
     */
    @GetMapping("/{id}/eliminar")
    public String eliminarIncidente(@PathVariable Long id) {
        incidenteService.deleteById(id);
        return "redirect:/incidentes";
    }
}