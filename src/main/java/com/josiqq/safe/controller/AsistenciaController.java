package com.josiqq.safe.controller;

import com.josiqq.safe.model.Asistencia;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.service.AsistenciaService;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/asistencias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private BomberoService bomberoService;

    @Autowired
    private TurnoService turnoService;

    /**
     * Lista las asistencias con filtros
     */
    @GetMapping
    public String listarAsistencias(
            @RequestParam(value = "fecha", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(value = "bombero", required = false) Long bomberoId,
            @RequestParam(value = "estado", required = false) String estado,
            Model model) {
        
        // Si no se especifica fecha, usar hoy
        LocalDate fechaConsulta = fecha != null ? fecha : LocalDate.now();
        
        List<Asistencia> asistencias;
        
        // Aplicar filtros
        if (bomberoId != null) {
            Optional<Bombero> bombero = bomberoService.findById(bomberoId);
            if (bombero.isPresent()) {
                asistencias = asistenciaService.findByBomberoAndFecha(bombero.get(), fechaConsulta);
            } else {
                asistencias = asistenciaService.findByFecha(fechaConsulta);
            }
        } else {
            asistencias = asistenciaService.findByFecha(fechaConsulta);
        }
        
        // Filtro adicional por estado si se especifica
        if (estado != null && !estado.isEmpty()) {
            asistencias = asistencias.stream()
                    .filter(a -> a.getEstado().name().equals(estado))
                    .collect(Collectors.toList());
        }
        
        // Calcular estadísticas del día
        Map<String, Long> estadisticas = calcularEstadisticas(asistencias);
        
        model.addAttribute("asistencias", asistencias);
        model.addAttribute("fechaSeleccionada", fechaConsulta);
        model.addAttribute("estadisticas", estadisticas);
        model.addAttribute("bomberos", bomberoService.findAll());
        
        return "asistencias/lista";
    }

    /**
     * Muestra el formulario para registrar nueva asistencia
     */
    @GetMapping("/registrar")
    public String mostrarFormularioDeAsistencia(Model model) {
        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setFecha(LocalDate.now()); // Fecha por defecto
        
        model.addAttribute("asistencia", nuevaAsistencia);
        model.addAttribute("bomberos", bomberoService.findAll());
        model.addAttribute("turnos", turnoService.findAll());
        
        return "asistencias/formulario";
    }

    /**
     * Procesa el formulario de nueva asistencia
     */
    @PostMapping
    public String guardarAsistencia(@ModelAttribute("asistencia") Asistencia asistencia,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal) {
        try {
            // Validaciones adicionales
            validarAsistencia(asistencia);
            
            // Si es estado PRESENTE o RETRASO y no tiene hora de entrada, usar hora actual
            if ((asistencia.getEstado() == Asistencia.EstadoAsistencia.PRESENTE || 
                 asistencia.getEstado() == Asistencia.EstadoAsistencia.RETRASO) && 
                asistencia.getHoraEntrada() == null) {
                asistencia.setHoraEntrada(LocalTime.now());
            }
            
            // Verificar si ya existe asistencia para este bombero en esta fecha
            if (yaExisteAsistencia(asistencia.getBombero().getId(), asistencia.getFecha())) {
                throw new IllegalStateException("Ya existe un registro de asistencia para este bombero en la fecha seleccionada");
            }
            
            asistenciaService.save(asistencia);
            
            redirectAttributes.addFlashAttribute("success", 
                "Asistencia registrada correctamente para " + 
                asistencia.getBombero().getNombre() + " " + asistencia.getBombero().getApellido());
            
            return "redirect:/asistencias?fecha=" + asistencia.getFecha().toString();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/asistencias/registrar";
        }
    }

    /**
     * Endpoint AJAX para obtener información de un bombero
     */
    @GetMapping("/api/bombero/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerInfoBombero(@PathVariable Long id) {
        Optional<Bombero> bombero = bomberoService.findById(id);
        
        if (bombero.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Bombero b = bombero.get();
        
        // Obtener estadísticas del bombero
        List<Asistencia> asistenciasDelMes = asistenciaService.findByBomberoAndFechaRange(
            b, LocalDate.now().withDayOfMonth(1), LocalDate.now());
        
        long presentesDelMes = asistenciasDelMes.stream()
                .filter(a -> a.getEstado() == Asistencia.EstadoAsistencia.PRESENTE)
                .count();
        
        Map<String, Object> info = Map.of(
            "id", b.getId(),
            "nombre", b.getNombre() + " " + b.getApellido(),
            "username", b.getUsername(),
            "rol", b.getRol() != null ? b.getRol().getNombre() : "Sin rol",
            "presentesDelMes", presentesDelMes,
            "totalRegistrosDelMes", asistenciasDelMes.size()
        );
        
        return ResponseEntity.ok(info);
    }

    /**
     * Registro rápido de entrada - marcar presente ahora
     */
    @PostMapping("/entrada-rapida")
    @ResponseBody
    public ResponseEntity<Map<String, String>> registrarEntradaRapida(@RequestParam Long bomberoId,
                                                                     Principal principal) {
        try {
            Optional<Bombero> bombero = bomberoService.findById(bomberoId);
            if (bombero.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Bombero no encontrado"));
            }
            
            LocalDate hoy = LocalDate.now();
            
            // Verificar si ya tiene asistencia hoy
            if (yaExisteAsistencia(bomberoId, hoy)) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ya existe registro de asistencia para hoy"));
            }
            
            // Crear nueva asistencia
            Asistencia asistencia = new Asistencia();
            asistencia.setBombero(bombero.get());
            asistencia.setFecha(hoy);
            asistencia.setEstado(Asistencia.EstadoAsistencia.PRESENTE);
            asistencia.setHoraEntrada(LocalTime.now());
            
            asistenciaService.save(asistencia);
            
            return ResponseEntity.ok(Map.of("success", "Entrada registrada correctamente"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Marcar salida de un bombero
     */
    @PostMapping("/salida/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> marcarSalida(@PathVariable Long id) {
        try {
            Optional<Asistencia> asistencia = asistenciaService.findById(id);
            if (asistencia.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Asistencia no encontrada"));
            }
            
            Asistencia a = asistencia.get();
            if (a.getHoraSalida() != null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Ya se ha registrado la salida"));
            }
            
            a.setHoraSalida(LocalTime.now());
            asistenciaService.save(a);
            
            return ResponseEntity.ok(Map.of("success", "Salida registrada correctamente"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Dashboard de estadísticas de asistencias
     */
    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        
        // Estadísticas del día
        List<Asistencia> asistenciasHoy = asistenciaService.findByFecha(hoy);
        Map<String, Long> estadisticasHoy = calcularEstadisticas(asistenciasHoy);
        
        // Estadísticas del mes
        List<Asistencia> asistenciasMes = asistenciaService.findByFechaRange(inicioMes, hoy);
        Map<String, Long> estadisticasMes = calcularEstadisticas(asistenciasMes);
        
        // Top 5 bomberos con más asistencias este mes
        Map<Bombero, Long> topBomberos = asistenciasMes.stream()
                .filter(a -> a.getEstado() == Asistencia.EstadoAsistencia.PRESENTE)
                .collect(Collectors.groupingBy(Asistencia::getBombero, Collectors.counting()));
        
        List<Map.Entry<Bombero, Long>> topBomberosLista = topBomberos.entrySet().stream()
                .sorted(Map.Entry.<Bombero, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toList());
        
        model.addAttribute("estadisticasHoy", estadisticasHoy);
        model.addAttribute("estadisticasMes", estadisticasMes);
        model.addAttribute("topBomberos", topBomberosLista);
        model.addAttribute("fechaHoy", hoy);
        model.addAttribute("totalBomberos", bomberoService.findAll().size());
        
        return "asistencias/dashboard";
    }

    // Métodos auxiliares privados
    
    private void validarAsistencia(Asistencia asistencia) {
        if (asistencia.getBombero() == null) {
            throw new IllegalArgumentException("Debe seleccionar un bombero");
        }
        
        if (asistencia.getFecha() == null) {
            throw new IllegalArgumentException("Debe especificar una fecha");
        }
        
        if (asistencia.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar asistencia para fechas futuras");
        }
        
        if (asistencia.getEstado() == null) {
            throw new IllegalArgumentException("Debe especificar un estado");
        }
        
        // Validar horarios si están presentes
        if (asistencia.getHoraEntrada() != null && asistencia.getHoraSalida() != null) {
            if (asistencia.getHoraEntrada().isAfter(asistencia.getHoraSalida())) {
                throw new IllegalArgumentException("La hora de entrada no puede ser posterior a la hora de salida");
            }
        }
    }
    
    private boolean yaExisteAsistencia(Long bomberoId, LocalDate fecha) {
        Optional<Bombero> bombero = bomberoService.findById(bomberoId);
        if (bombero.isEmpty()) return false;
        
        List<Asistencia> asistenciasExistentes = asistenciaService.findByBomberoAndFecha(bombero.get(), fecha);
        return !asistenciasExistentes.isEmpty();
    }
    
    private Map<String, Long> calcularEstadisticas(List<Asistencia> asistencias) {
        return asistencias.stream()
                .collect(Collectors.groupingBy(
                    a -> a.getEstado().name(),
                    Collectors.counting()
                ));
    }
}