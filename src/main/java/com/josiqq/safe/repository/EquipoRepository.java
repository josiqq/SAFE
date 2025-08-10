package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Equipo;

import java.util.List;
import java.util.Date;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    /**
     * Busca equipos por su estado actual.
     * @param estado El estado del equipo (ej: "Optimo", "en reparación").
     * @return Una lista de equipos en ese estado.
     */
    List<Equipo> findByEstado(String estado);

    /**
     * Encuentra todos los equipos ubicados en un lugar específico.
     * @param ubicacion La ubicación a buscar (ej: "Almacén A", "Camión 1").
     * @return Una lista de equipos en esa ubicación.
     */
    List<Equipo> findByUbicacion(String ubicacion);

    /**
     * Busca equipos cuyo último mantenimiento fue antes de una fecha específica.
     * Útil para programar nuevos mantenimientos.
     * @param fecha La fecha límite.
     * @return Lista de equipos que necesitan revisión.
     */
    List<Equipo> findByFechaUltimoMantenimientoBefore(Date fecha);
}