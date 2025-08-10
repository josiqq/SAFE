package com.josiqq.safe.controller;

import com.josiqq.safe.model.Turno;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    
    @Autowired
    private BomberoService bomberoService;

    @InitBinder("turno")
    public void initBinder(WebDataBinder binder) {
        // Excluir el campo 'bomberos' del binding automático para evitar errores de índice
        binder.setDisallowedFields("bomberos");
    }

    @GetMapping
    public String listarTurnos(Model model) {
        model.addAttribute("turnos", turnoService.findAll());
        return "turnos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeNuevoTurno(Model model) {
        model.addAttribute("turno", new Turno());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "turnos/formulario";
    }

    @PostMapping
    public String guardarTurno(@ModelAttribute("turno") Turno turno,
                              @RequestParam(value = "bomberoIds", required = false) List<Long> bomberoIds,
                              Model model) {
        
        // Inicializar la colección de bomberos si es null
        if (turno.getBomberos() == null) {
            turno.setBomberos(new HashSet<>());
        }
        
        // Procesar la asignación de bomberos
        if (bomberoIds != null && !bomberoIds.isEmpty()) {
            Set<Bombero> bomberosAsignados = new HashSet<>();
            for (Long bomberoId : bomberoIds) {
                Optional<Bombero> bomberoOpt = bomberoService.findById(bomberoId);
                if (bomberoOpt.isPresent()) {
                    bomberosAsignados.add(bomberoOpt.get());
                }
            }
            turno.setBomberos(bomberosAsignados);
        } else {
            // Si no se seleccionaron bomberos, asegurar que la colección esté vacía pero no null
            turno.setBomberos(new HashSet<>());
        }
        
        try {
            turnoService.save(turno);
            return "redirect:/turnos?success=created";
        } catch (Exception e) {
            model.addAttribute("turno", turno);
            model.addAttribute("bomberos", bomberoService.findAll());
            model.addAttribute("error", "Error al crear el turno: " + e.getMessage());
            return "turnos/formulario";
        }
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEdicion(@PathVariable Long id, Model model) {
        Optional<Turno> turnoOpt = turnoService.findById(id);
        if (turnoOpt.isEmpty()) {
            return "redirect:/turnos?error=notfound";
        }
        model.addAttribute("turno", turnoOpt.get());
        model.addAttribute("bomberos", bomberoService.findAll());
        return "turnos/formulario";
    }

    @PostMapping("/{id}")
    public String actualizarTurno(@PathVariable Long id, 
                                 @ModelAttribute("turno") Turno turno,
                                 @RequestParam(value = "bomberoIds", required = false) List<Long> bomberoIds,
                                 Model model) {
        turno.setId(id);
        
        // Inicializar la colección de bomberos si es null
        if (turno.getBomberos() == null) {
            turno.setBomberos(new HashSet<>());
        }
        
        // Procesar la asignación de bomberos
        if (bomberoIds != null && !bomberoIds.isEmpty()) {
            Set<Bombero> bomberosAsignados = new HashSet<>();
            for (Long bomberoId : bomberoIds) {
                Optional<Bombero> bomberoOpt = bomberoService.findById(bomberoId);
                if (bomberoOpt.isPresent()) {
                    bomberosAsignados.add(bomberoOpt.get());
                }
            }
            turno.setBomberos(bomberosAsignados);
        } else {
            // Si no se seleccionaron bomberos, limpiar la colección
            turno.setBomberos(new HashSet<>());
        }
        
        try {
            turnoService.save(turno);
            return "redirect:/turnos?success=updated";
        } catch (Exception e) {
            model.addAttribute("turno", turno);
            model.addAttribute("bomberos", bomberoService.findAll());
            model.addAttribute("error", "Error al actualizar el turno: " + e.getMessage());
            return "turnos/formulario";
        }
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarTurno(@PathVariable Long id) {
        try {
            turnoService.deleteById(id);
            return "redirect:/turnos?success=deleted";
        } catch (Exception e) {
            return "redirect:/turnos?error=deletefailed";
        }
    }

    @GetMapping("/{id}/clonar")
    public String clonarTurno(@PathVariable Long id, Model model) {
        Optional<Turno> turnoOriginal = turnoService.findById(id);
        if (turnoOriginal.isEmpty()) {
            return "redirect:/turnos?error=notfound";
        }
        
        Turno turnoClonado = new Turno();
        turnoClonado.setNombre(turnoOriginal.get().getNombre() + " - Copia");
        turnoClonado.setHoraInicio(turnoOriginal.get().getHoraInicio());
        turnoClonado.setHoraFin(turnoOriginal.get().getHoraFin());
        turnoClonado.setBomberos(new HashSet<>(turnoOriginal.get().getBomberos()));
        
        model.addAttribute("turno", turnoClonado);
        model.addAttribute("bomberos", bomberoService.findAll());
        return "turnos/formulario";
    }

    @GetMapping("/{id}/detalle")
    public String verDetalleTurno(@PathVariable Long id, Model model) {
        Optional<Turno> turnoOpt = turnoService.findById(id);
        if (turnoOpt.isEmpty()) {
            return "redirect:/turnos?error=notfound";
        }
        model.addAttribute("turno", turnoOpt.get());
        return "turnos/detalle";
    }

    @PostMapping("/{id}/bombero/{bomberoId}/asignar")
    @ResponseBody
    public String asignarBombero(@PathVariable Long id, @PathVariable Long bomberoId) {
        try {
            Optional<Turno> turnoOpt = turnoService.findById(id);
            Optional<Bombero> bomberoOpt = bomberoService.findById(bomberoId);
            
            if (turnoOpt.isPresent() && bomberoOpt.isPresent()) {
                Turno turno = turnoOpt.get();
                Bombero bombero = bomberoOpt.get();
                
                if (turno.getBomberos() == null) {
                    turno.setBomberos(new HashSet<>());
                }
                
                turno.getBomberos().add(bombero);
                turnoService.save(turno);
                return "success";
            }
            return "error";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{id}/bombero/{bomberoId}/desasignar")
    @ResponseBody
    public String desasignarBombero(@PathVariable Long id, @PathVariable Long bomberoId) {
        try {
            Optional<Turno> turnoOpt = turnoService.findById(id);
            Optional<Bombero> bomberoOpt = bomberoService.findById(bomberoId);
            
            if (turnoOpt.isPresent() && bomberoOpt.isPresent()) {
                Turno turno = turnoOpt.get();
                Bombero bombero = bomberoOpt.get();
                
                if (turno.getBomberos() != null) {
                    turno.getBomberos().remove(bombero);
                    turnoService.save(turno);
                }
                return "success";
            }
            return "error";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{id}/activar")
    @ResponseBody
    public String activarTurno(@PathVariable Long id) {
        try {
            Optional<Turno> turnoOpt = turnoService.findById(id);
            if (turnoOpt.isPresent()) {
                Turno turno = turnoOpt.get();
                // Suponiendo que existe un campo 'activo' en el modelo Turno
                // turno.setActivo(true);
                turnoService.save(turno);
                return "success";
            }
            return "error";
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{id}/desactivar")
    @ResponseBody
    public String desactivarTurno(@PathVariable Long id) {
        try {
            Optional<Turno> turnoOpt = turnoService.findById(id);
            if (turnoOpt.isPresent()) {
                Turno turno = turnoOpt.get();
                // Suponiendo que existe un campo 'activo' en el modelo Turno
                // turno.setActivo(false);
                turnoService.save(turno);
                return "success";
            }
            return "error";
        } catch (Exception e) {
            return "error";
        }
    }
}