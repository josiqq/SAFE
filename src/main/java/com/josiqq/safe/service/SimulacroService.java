package com.josiqq.safe.service;

import com.josiqq.safe.models.Simulacro;
import com.josiqq.safe.repository.SimulacroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SimulacroService {

    @Autowired
    private SimulacroRepository simulacroRepository;

    @Transactional(readOnly = true)
    public List<Simulacro> findAll() {
        return simulacroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Simulacro> findById(Long id) {
        return simulacroRepository.findById(id);
    }

    @Transactional
    public Simulacro save(Simulacro simulacro) {
        // Lógica de negocio: Un simulacro debe tener al menos un escenario y una fecha.
        if (simulacro.getEscenario() == null || simulacro.getEscenario().isEmpty() || simulacro.getFecha() == null) {
            throw new IllegalStateException("El simulacro debe tener un escenario y una fecha definidos.");
        }
        return simulacroRepository.save(simulacro);
    }

    @Transactional
    public void deleteById(Long id) {
        simulacroRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Simulacro> findByLugar(String lugar) {
        return simulacroRepository.findByLugar(lugar);
    }

    @Transactional(readOnly = true)
    public List<Simulacro> findByFechaBetween(Date fechaInicio, Date fechaFin) {
        return simulacroRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}