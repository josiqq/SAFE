package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.EvaluacionDesempeno;

import java.util.List;

public interface EvaluacionDesempenoRepository extends JpaRepository<EvaluacionDesempeno, Long> {

    /**
     * Busca todas las evaluaciones de desempeño para un bombero específico.
     * @param bomberoEvaluado El bombero que fue evaluado.
     * @return Una lista con su historial de evaluaciones.
     */
    List<EvaluacionDesempeno> findByBomberoEvaluado(Bombero bomberoEvaluado);

    /**
     * Encuentra todas las evaluaciones realizadas por un evaluador específico.
     * @param evaluador El bombero que realizó las evaluaciones.
     * @return Una lista de las evaluaciones que ha emitido.
     */
    List<EvaluacionDesempeno> findByEvaluador(Bombero evaluador);
}