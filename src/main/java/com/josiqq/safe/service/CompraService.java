package com.josiqq.safe.service;

import com.josiqq.safe.model.Compra;
import com.josiqq.safe.model.Proveedor;
import com.josiqq.safe.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Compra> findById(Long id) {
        return compraRepository.findById(id);
    }

    @Transactional
    public Compra save(Compra compra) {
        // Validar que la compra tenga un proveedor asociado
        if (compra.getProveedor() == null) {
            throw new IllegalStateException("Toda compra debe tener un proveedor asociado.");
        }
        return compraRepository.save(compra);
    }

    @Transactional
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }
    
    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Compra> findByProveedor(Proveedor proveedor) {
        return compraRepository.findByProveedor(proveedor);
    }

    @Transactional(readOnly = true)
    public List<Compra> findByFechaBetween(Date fechaInicio, Date fechaFin) {
        return compraRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}