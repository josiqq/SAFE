package com.josiqq.safe.controller;

import com.josiqq.safe.model.EvaluacionSimulacro;
import com.josiqq.safe.model.Simulacro;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.EvaluacionSimulacroService;
import com.josiqq.safe.service.SimulacroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/simulacros")
public class SimulacroController {

    @Autowired
    private SimulacroService simulacroService;
    
    @Autowired
    private BomberoService bomberoService;
    
    @Autowired
    private EvaluacionSimulacroService evaluacionSimulacroService;

    @GetMapping
    public String listarSimulacros(Model model) {
        model.addAttribute("simulacros", simulacroService.findAll());
        return "simulacros/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoSimulacro(Model model) {
        model.addAttribute("simulacro", new Simulacro());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "simulacros/formulario";
    }

    @PostMapping
    public String guardarSimulacro(@ModelAttribute("simulacro") Simulacro simulacro) {
        simulacroService.save(simulacro);
        return "redirect:/simulacros";
    }

    // --- Evaluaciones de Simulacro ---

    @GetMapping("/{simulacroId}/evaluaciones/nueva")
    public String mostrarFormularioDeNuevaEvaluacion(@PathVariable Long simulacroId, Model model) {
        Simulacro simulacro = simulacroService.findById(simulacroId).orElseThrow();
        EvaluacionSimulacro evaluacion = new EvaluacionSimulacro();
        evaluacion.setSimulacro(simulacro);
        
        model.addAttribute("evaluacion", evaluacion);
        model.addAttribute("participantes", simulacro.getParticipantes());
        return "evaluaciones/formulario_simulacro";
    }

    @PostMapping("/{simulacroId}/evaluaciones")
    public String guardarEvaluacion(@PathVariable Long simulacroId, @ModelAttribute("evaluacion") EvaluacionSimulacro evaluacion) {
        Simulacro simulacro = simulacroService.findById(simulacroId).orElseThrow();
        evaluacion.setSimulacro(simulacro);
        evaluacionSimulacroService.save(evaluacion);
        return "redirect:/simulacros"; // O a una vista de detalle del simulacro
    }
}