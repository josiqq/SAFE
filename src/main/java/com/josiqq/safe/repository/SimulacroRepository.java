package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Simulacro;

import java.util.Date;
import java.util.List;

public interface SimulacroRepository extends JpaRepository<Simulacro, Long> {

    /**
     * Busca todos los simulacros realizados en un lugar específico.
     * @param lugar El lugar donde se llevó a cabo el simulacro.
     * @return Una lista de simulacros en esa ubicación.
     */
    List<Simulacro> findByLugar(String lugar);

    /**
     * Encuentra todos los simulacros planificados dentro de un rango de fechas.
     * @param fechaInicio La fecha de inicio del periodo.
     * @param fechaFin La fecha de fin del periodo.
     * @return Una lista de simulacros en ese rango.
     */
    List<Simulacro> findByFechaBetween(Date fechaInicio, Date fechaFin);
}