package com.josiqq.safe.controller;

import com.josiqq.safe.models.Asistencia;
import com.josiqq.safe.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.TurnoService;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private BomberoService bomberoService;

    @Autowired
    private TurnoService turnoService;

    @GetMapping
    public String listarAsistencias(@RequestParam(value = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, Model model) {
        List<Asistencia> asistencias;
        if (fecha != null) {
            asistencias = asistenciaService.findByFecha(fecha);
        } else {
            asistencias = asistenciaService.findByFecha(LocalDate.now()); // Mostrar las de hoy por defecto
        }
        model.addAttribute("asistencias", asistencias);
        model.addAttribute("fechaSeleccionada", fecha != null ? fecha : LocalDate.now());
        return "asistencias/lista"; // /templates/asistencias/lista.html
    }

    @GetMapping("/registrar")
    public String mostrarFormularioDeAsistencia(Model model) {
        model.addAttribute("asistencia", new Asistencia());
        model.addAttribute("bomberos", bomberoService.findAll());
        model.addAttribute("turnos", turnoService.findAll());
        return "asistencias/formulario"; // /templates/asistencias/formulario.html
    }

    @PostMapping
    public String guardarAsistencia(@ModelAttribute("asistencia") Asistencia asistencia) {
        asistenciaService.save(asistencia);
        return "redirect:/asistencias?fecha=" + asistencia.getFecha().toString();
    }
}