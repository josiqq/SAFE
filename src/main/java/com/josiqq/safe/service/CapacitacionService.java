package com.josiqq.safe.service;

import com.josiqq.safe.models.Capacitacion;
import com.josiqq.safe.repository.CapacitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CapacitacionService {

    @Autowired
    private CapacitacionRepository capacitacionRepository;

    @Transactional(readOnly = true)
    public List<Capacitacion> findAll() {
        return capacitacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Capacitacion> findById(Long id) {
        return capacitacionRepository.findById(id);
    }

    @Transactional
    public Capacitacion save(Capacitacion capacitacion) {
        return capacitacionRepository.save(capacitacion);
    }

    @Transactional
    public void deleteById(Long id) {
        capacitacionRepository.deleteById(id);
    }
    
    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Capacitacion> findByTipo(String tipo) {
        return capacitacionRepository.findByTipo(tipo);
    }

    @Transactional(readOnly = true)
    public List<Capacitacion> findCapacitacionesPasadas() {
        return capacitacionRepository.findByFechaBefore(new Date());
    }
}