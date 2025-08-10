package com.josiqq.safe.service;

import com.josiqq.safe.model.Turno;
import com.josiqq.safe.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    @Transactional(readOnly = true)
    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Turno> findById(Long id) {
        return turnoRepository.findById(id);
    }

    @Transactional
    public Turno save(Turno turno) {
        // Lógica de negocio: Validar que el nombre del turno no esté ya registrado.
        turnoRepository.findByNombre(turno.getNombre()).ifPresent(t -> {
            if (!t.getId().equals(turno.getId())) {
                throw new IllegalStateException("El nombre del turno ya existe.");
            }
        });

        // Lógica de negocio: La hora de inicio no puede ser posterior a la hora de fin.
        if (turno.getHoraInicio() != null && turno.getHoraFin() != null && turno.getHoraInicio().isAfter(turno.getHoraFin())) {
            throw new IllegalStateException("La hora de inicio no puede ser posterior a la hora de fin.");
        }

        return turnoRepository.save(turno);
    }

    @Transactional
    public void deleteById(Long id) {
        turnoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public Optional<Turno> findByNombre(String nombre) {
        return turnoRepository.findByNombre(nombre);
    }
}