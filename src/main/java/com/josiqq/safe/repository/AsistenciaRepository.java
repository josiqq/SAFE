package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Asistencia;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.Asistencia.EstadoAsistencia;

import java.time.LocalDate;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    /**
     * Busca todos los registros de asistencia para un bombero específico.
     * @param bombero El bombero cuya asistencia se quiere consultar.
     * @return Una lista de registros de asistencia.
     */
    List<Asistencia> findByBombero(Bombero bombero);

    /**
     * Encuentra todos los registros de asistencia en una fecha específica.
     * @param fecha La fecha para la consulta.
     * @return Una lista de asistencias en esa fecha.
     */
    List<Asistencia> findByFecha(LocalDate fecha);

    /**
     * Busca registros de asistencia por estado (PRESENTE, AUSENTE, etc.) en un rango de fechas.
     * @param estado El estado de la asistencia.
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Una lista de asistencias que coinciden con los criterios.
     */
    List<Asistencia> findByEstadoAndFechaBetween(EstadoAsistencia estado, LocalDate fechaInicio, LocalDate fechaFin);
}