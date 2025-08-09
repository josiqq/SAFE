package com.josiqq.safe.service;

import com.josiqq.safe.models.Alerta;
import com.josiqq.safe.models.Rol;
import com.josiqq.safe.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Transactional(readOnly = true)
    public List<Alerta> findAll() {
        return alertaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Alerta> findById(Long id) {
        return alertaRepository.findById(id);
    }

    @Transactional
    public Alerta save(Alerta alerta) {
        if (alerta.getFechaCreacion() == null) {
            alerta.setFechaCreacion(LocalDateTime.now());
        }
        return alertaRepository.save(alerta);
    }

    @Transactional
    public void deleteById(Long id) {
        alertaRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Alerta> findByPrioridad(String prioridad) {
        return alertaRepository.findByPrioridad(prioridad);
    }

    @Transactional(readOnly = true)
    public List<Alerta> findByRol(Rol rol) {
        return alertaRepository.findByRolesDestinatariosContains(rol);
    }
}