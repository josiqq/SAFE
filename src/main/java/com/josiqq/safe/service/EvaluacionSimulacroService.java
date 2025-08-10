package com.josiqq.safe.service;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.EvaluacionSimulacro;
import com.josiqq.safe.model.Simulacro;
import com.josiqq.safe.repository.EvaluacionSimulacroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionSimulacroService {

    @Autowired
    private EvaluacionSimulacroRepository evaluacionSimulacroRepository;

    @Transactional(readOnly = true)
    public List<EvaluacionSimulacro> findAll() {
        return evaluacionSimulacroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<EvaluacionSimulacro> findById(Long id) {
        return evaluacionSimulacroRepository.findById(id);
    }

    @Transactional
    public EvaluacionSimulacro save(EvaluacionSimulacro evaluacion) {
        if (evaluacion.getSimulacro() == null || evaluacion.getParticipante() == null) {
            throw new IllegalStateException("La evaluación debe estar asociada a un simulacro y a un participante.");
        }
        return evaluacionSimulacroRepository.save(evaluacion);
    }

    @Transactional
    public void deleteById(Long id) {
        evaluacionSimulacroRepository.deleteById(id);
    }
    
    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<EvaluacionSimulacro> findBySimulacro(Simulacro simulacro) {
        return evaluacionSimulacroRepository.findBySimulacro(simulacro);
    }

    @Transactional(readOnly = true)
    public List<EvaluacionSimulacro> findByParticipante(Bombero bombero) {
        return evaluacionSimulacroRepository.findByParticipante(bombero);
    }
}