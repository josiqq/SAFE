package com.josiqq.safe.service;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.EvaluacionDesempeno;
import com.josiqq.safe.repository.EvaluacionDesempenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionDesempenoService {

    @Autowired
    private EvaluacionDesempenoRepository evaluacionRepository;

    @Transactional(readOnly = true)
    public List<EvaluacionDesempeno> findAll() {
        return evaluacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EvaluacionDesempeno> findById(Long id) {
        return evaluacionRepository.findById(id);
    }

    @Transactional
    public EvaluacionDesempeno save(EvaluacionDesempeno evaluacion) {
        if (evaluacion.getBomberoEvaluado() == null || evaluacion.getEvaluador() == null) {
            throw new IllegalStateException("La evaluación debe tener un bombero evaluado y un evaluador.");
        }
        return evaluacionRepository.save(evaluacion);
    }

    @Transactional
    public void deleteById(Long id) {
        evaluacionRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<EvaluacionDesempeno> findByBomberoEvaluado(Bombero bombero) {
        return evaluacionRepository.findByBomberoEvaluado(bombero);
    }

    @Transactional(readOnly = true)
    public List<EvaluacionDesempeno> findByEvaluador(Bombero bombero) {
        return evaluacionRepository.findByEvaluador(bombero);
    }
}