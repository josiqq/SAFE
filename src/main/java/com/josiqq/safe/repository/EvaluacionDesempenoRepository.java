package com.josiqq.safe.repository;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.EvaluacionDesempeno;
import org.springframework.data.jpa.repository.JpaRepository;
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