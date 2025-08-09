package com.josiqq.safe.service;

import com.josiqq.safe.models.Proveedor;
import com.josiqq.safe.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Proveedor> findById(Long id) {
        return proveedorRepository.findById(id);
    }

    @Transactional
    public Proveedor save(Proveedor proveedor) {
        // Lógica de negocio: Validar que el RUC no esté ya registrado por otro proveedor.
        proveedorRepository.findByRuc(proveedor.getRuc()).ifPresent(p -> {
            if (!p.getId().equals(proveedor.getId())) {
                throw new IllegalStateException("El RUC ya está registrado por otro proveedor.");
            }
        });
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public Optional<Proveedor> findByRuc(String ruc) {
        return proveedorRepository.findByRuc(ruc);
    }
}