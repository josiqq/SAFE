package com.josiqq.safe.service;

import com.josiqq.safe.model.Rol;
import com.josiqq.safe.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Rol> findById(Long id) {
        return rolRepository.findById(id);
    }

    @Transactional
    public Rol save(Rol rol) {
        // Validar que el nombre del rol sea único
        rolRepository.findByNombre(rol.getNombre()).ifPresent(r -> {
            if (!r.getId().equals(rol.getId())) {
                throw new IllegalStateException("El nombre del rol ya existe.");
            }
        });
        return rolRepository.save(rol);
    }

    @Transactional
    public void deleteById(Long id) {
        rolRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }
}