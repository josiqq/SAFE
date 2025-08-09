package com.josiqq.safe.repository;

import com.josiqq.safe.models.Capacitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface CapacitacionRepository extends JpaRepository<Capacitacion, Long> {

    /**
     * Busca capacitaciones por su tipo (Ej: "Teórica", "Práctica", "Simulacro").
     * @param tipo El tipo de capacitación a buscar.
     * @return Una lista de capacitaciones que coinciden con el tipo.
     */
    List<Capacitacion> findByTipo(String tipo);

    /**
     * Encuentra todas las capacitaciones programadas para una fecha específica.
     * @param fecha La fecha de la capacitación.
     * @return Una lista de capacitaciones en esa fecha.
     */
    List<Capacitacion> findByFecha(Date fecha);

    /**
     * Devuelve una lista de todas las capacitaciones cuya fecha es anterior a la actual.
     * @param fechaActual La fecha actual para comparar.
     * @return Una lista de capacitaciones pasadas.
     */
    List<Capacitacion> findByFechaBefore(Date fechaActual);
}