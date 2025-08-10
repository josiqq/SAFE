package com.josiqq.safe.service;

import com.josiqq.safe.model.Asistencia;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Transactional(readOnly = true)
    public List<Asistencia> findAll() {
        return asistenciaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Asistencia> findById(Long id) {
        return asistenciaRepository.findById(id);
    }

    @Transactional
    public Asistencia save(Asistencia asistencia) {
        if (asistencia.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("No se puede registrar una asistencia para una fecha futura.");
        }
        return asistenciaRepository.save(asistencia);
    }

    @Transactional
    public void deleteById(Long id) {
        asistenciaRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Asistencia> findByBombero(Bombero bombero) {
        return asistenciaRepository.findByBombero(bombero);
    }

    @Transactional(readOnly = true)
    public List<Asistencia> findByFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha);
    }
}