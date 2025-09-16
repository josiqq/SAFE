package com.josiqq.safe.service;

import com.josiqq.safe.model.Equipo;
import com.josiqq.safe.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Equipo> findById(Long id) {
        return equipoRepository.findById(id);
    }

    @Transactional
    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Transactional
    public void deleteById(Long id) {
        equipoRepository.deleteById(id);
    }

    public long count() {
        return equipoRepository.count();
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Equipo> findByEstado(String estado) {
        return equipoRepository.findByEstado(estado);
    }
    
    @Transactional(readOnly = true)
    public List<Equipo> findEquiposQueNecesitanMantenimiento(Date fechaLimite) {
        return equipoRepository.findByFechaUltimoMantenimientoBefore(fechaLimite);
    }
}