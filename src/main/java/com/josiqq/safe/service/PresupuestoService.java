package com.josiqq.safe.service;

import com.josiqq.safe.model.Presupuesto;
import com.josiqq.safe.repository.PresupuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Transactional(readOnly = true)
    public List<Presupuesto> findAll() {
        return presupuestoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Presupuesto> findById(Long id) {
        return presupuestoRepository.findById(id);
    }

    @Transactional
    public Presupuesto save(Presupuesto presupuesto) {
        if (presupuesto.getMontoGastado().compareTo(presupuesto.getMontoAsignado()) > 0) {
            throw new IllegalStateException("El monto gastado no puede exceder el monto asignado.");
        }
        return presupuestoRepository.save(presupuesto);
    }

    @Transactional
    public void deleteById(Long id) {
        presupuestoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Presupuesto> findByAnio(Integer anio) {
        return presupuestoRepository.findByAnio(anio);
    }

    @Transactional(readOnly = true)
    public Optional<Presupuesto> findByCategoriaAndAnio(String categoria, Integer anio) {
        return presupuestoRepository.findByCategoriaAndAnio(categoria, anio);
    }
}