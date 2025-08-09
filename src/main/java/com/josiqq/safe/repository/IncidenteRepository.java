package com.josiqq.safe.repository;

import com.josiqq.safe.models.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Date;

public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    /**
     * Busca todos los incidentes que se encuentran en un estado específico.
     * @param estado El estado del incidente (ej: "Activo", "Cerrado").
     * @return Una lista de incidentes que coinciden con el estado.
     */
    List<Incidente> findByEstado(String estado);

    /**
     * Encuentra incidentes por su tipo.
     * @param tipo El tipo de incidente (ej: "incendio", "rescate").
     * @return Una lista de incidentes de ese tipo.
     */
    List<Incidente> findByTipo(String tipo);

    /**
     * Busca incidentes que ocurrieron en un rango de fechas.
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Una lista de incidentes dentro de ese periodo.
     */
    List<Incidente> findByFechaHoraInicioBetween(Date fechaInicio, Date fechaFin);
}