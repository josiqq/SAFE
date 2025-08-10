package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.EvaluacionSimulacro;
import com.josiqq.safe.model.Simulacro;

import java.util.List;

public interface EvaluacionSimulacroRepository extends JpaRepository<EvaluacionSimulacro, Long> {

    /**
     * Busca todas las evaluaciones asociadas a un simulacro específico.
     * @param simulacro El simulacro del cual se quieren obtener las evaluaciones.
     * @return Una lista de evaluaciones para ese simulacro.
     */
    List<EvaluacionSimulacro> findBySimulacro(Simulacro simulacro);

    /**
     * Encuentra todas las evaluaciones de un bombero participante a lo largo de todos los simulacros.
     * @param participante El bombero que fue evaluado.
     * @return Una lista con su historial de evaluaciones en simulacros.
     */
    List<EvaluacionSimulacro> findByParticipante(Bombero participante);
}