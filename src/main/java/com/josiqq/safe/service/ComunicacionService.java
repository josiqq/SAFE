package com.josiqq.safe.service;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.Comunicacion;
import com.josiqq.safe.model.Incidente;
import com.josiqq.safe.repository.ComunicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComunicacionService {

    @Autowired
    private ComunicacionRepository comunicacionRepository;

    @Transactional(readOnly = true)
    public List<Comunicacion> findAll() {
        return comunicacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Comunicacion> findById(Long id) {
        return comunicacionRepository.findById(id);
    }

    @Transactional
    public Comunicacion save(Comunicacion comunicacion) {
        if (comunicacion.getIncidente() == null || comunicacion.getResponsable() == null) {
            throw new IllegalStateException("La comunicación debe tener un incidente y un bombero responsable asociados.");
        }
        return comunicacionRepository.save(comunicacion);
    }

    @Transactional
    public void deleteById(Long id) {
        comunicacionRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Comunicacion> findByIncidente(Incidente incidente) {
        return comunicacionRepository.findByIncidente(incidente);
    }

    @Transactional(readOnly = true)
    public List<Comunicacion> findByResponsable(Bombero responsable) {
        return comunicacionRepository.findByResponsable(responsable);
    }
}