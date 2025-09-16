package com.josiqq.safe.service;

import com.josiqq.safe.dto.PatronAusentismoDTO;
import com.josiqq.safe.dto.ReporteAsistenciaDTO;
import com.josiqq.safe.model.Asistencia;
import com.josiqq.safe.model.Asistencia.EstadoAsistencia;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private BomberoService bomberoService;

    @Transactional(readOnly = true)
    public List<Asistencia> findAll() {
        return asistenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Asistencia> findById(Long id) {
        return asistenciaRepository.findById(id);
    }

    @Transactional
    public Asistencia save(Asistencia asistencia) {
        if (asistencia.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar una asistencia para una fecha futura.");
        }
        return asistenciaRepository.save(asistencia);
    }

    @Transactional
    public void deleteById(Long id) {
        asistenciaRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Asistencia> findByBombero(Bombero bombero) {
        return asistenciaRepository.findByBombero(bombero);
    }

    @Transactional(readOnly = true)
    public List<Asistencia> findByFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha);
    }

    /**
     * Busca asistencias por bombero y fecha específica
     */
    @Transactional(readOnly = true)
    public List<Asistencia> findByBomberoAndFecha(Bombero bombero, LocalDate fecha) {
        return asistenciaRepository.findByBomberoAndFecha(bombero, fecha);
    }

    /**
     * Busca asistencias por bombero en un rango de fechas
     */
    @Transactional(readOnly = true)
    public List<Asistencia> findByBomberoAndFechaRange(Bombero bombero, LocalDate fechaInicio, LocalDate fechaFin) {
        return asistenciaRepository.findByBomberoAndFechaBetween(bombero, fechaInicio, fechaFin);
    }

    /**
     * Busca asistencias en un rango de fechas
     */
    @Transactional(readOnly = true)
    public List<Asistencia> findByFechaRange(LocalDate fechaInicio, LocalDate fechaFin) {
        return asistenciaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    /**
     * Obtiene estadísticas de asistencia para un bombero en un período
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getEstadisticasBombero(Bombero bombero, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Asistencia> asistencias = findByBomberoAndFechaRange(bombero, fechaInicio, fechaFin);

        return asistencias.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getEstado().name(),
                        Collectors.counting()));
    }

    /**
     * Verifica si un bombero ya tiene asistencia registrada en una fecha
     */
    @Transactional(readOnly = true)
    public boolean existeAsistencia(Bombero bombero, LocalDate fecha) {
        return !findByBomberoAndFecha(bombero, fecha).isEmpty();
    }

    /**
     * Obtiene el porcentaje de asistencia de un bombero en un período
     */
    @Transactional(readOnly = true)
    public double getPorcentajeAsistencia(Bombero bombero, LocalDate fechaInicio, LocalDate fechaFin) {
        List<Asistencia> asistencias = findByBomberoAndFechaRange(bombero, fechaInicio, fechaFin);

        if (asistencias.isEmpty()) {
            return 0.0;
        }

        long presentes = asistencias.stream()
                .filter(a -> a.getEstado() == EstadoAsistencia.PRESENTE)
                .count();

        return (double) presentes / asistencias.size() * 100;
    }

    /**
     * Obtiene bomberos que no han registrado asistencia en una fecha
     */
    @Transactional(readOnly = true)
    public List<Bombero> getBomberosSinAsistencia(LocalDate fecha) {
        List<Bombero> todosBomberos = bomberoService.findAll();
        List<Asistencia> asistenciasDelDia = findByFecha(fecha);

        Set<Long> bomberosConAsistencia = asistenciasDelDia.stream()
                .map(a -> a.getBombero().getId())
                .collect(Collectors.toSet());

        return todosBomberos.stream()
                .filter(b -> !bomberosConAsistencia.contains(b.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Genera reporte de asistencias para exportación
     */
    @Transactional(readOnly = true)
    public List<ReporteAsistenciaDTO> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Asistencia> asistencias = findByFechaRange(fechaInicio, fechaFin);

        return asistencias.stream()
                .map(a -> new ReporteAsistenciaDTO(
                        a.getBombero().getNombre() + " " + a.getBombero().getApellido(),
                        a.getBombero().getUsername(),
                        a.getFecha(),
                        a.getEstado().name(),
                        a.getHoraEntrada(),
                        a.getHoraSalida(),
                        a.getTurno() != null ? a.getTurno().getNombre() : "Sin turno"))
                .collect(Collectors.toList());
    }

    /**
     * Calcula horas trabajadas en un día para un bombero
     */
    @Transactional(readOnly = true)
    public Duration calcularHorasTrabajadas(Long asistenciaId) {
        Optional<Asistencia> asistencia = findById(asistenciaId);

        if (asistencia.isEmpty() ||
                asistencia.get().getHoraEntrada() == null ||
                asistencia.get().getHoraSalida() == null) {
            return Duration.ZERO;
        }

        LocalTime entrada = asistencia.get().getHoraEntrada();
        LocalTime salida = asistencia.get().getHoraSalida();

        return Duration.between(entrada, salida);
    }

    /**
     * Obtiene resumen de asistencias por turno en una fecha
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getResumenPorTurno(LocalDate fecha) {
        List<Asistencia> asistenciasDelDia = findByFecha(fecha);

        return asistenciasDelDia.stream()
                .filter(a -> a.getTurno() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getTurno().getNombre(),
                        Collectors.counting()));
    }

    /**
     * Busca patrones de ausentismo - bomberos con más de X ausencias consecutivas
     */
    @Transactional(readOnly = true)
    public List<PatronAusentismoDTO> detectarPatronesAusentismo(int diasConsecutivos) {
        // Este método requeriría lógica más compleja para detectar patrones
        // Por ahora retornamos una lista vacía como placeholder
        return new ArrayList<>();
    }
}