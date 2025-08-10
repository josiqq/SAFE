package com.josiqq.safe.service;

import com.josiqq.safe.model.Equipo;
import com.josiqq.safe.model.Mantenimiento;
import com.josiqq.safe.model.Vehiculo;
import com.josiqq.safe.model.Mantenimiento.TipoMantenimiento;
import com.josiqq.safe.repository.MantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Transactional(readOnly = true)
    public List<Mantenimiento> findAll() {
        return mantenimientoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Mantenimiento> findById(Long id) {
        return mantenimientoRepository.findById(id);
    }

    @Transactional
    public Mantenimiento save(Mantenimiento mantenimiento) {
        if (mantenimiento.getVehiculo() != null && mantenimiento.getEquipo() != null) {
            throw new IllegalStateException("Un mantenimiento no puede estar asociado a un vehículo y a un equipo al mismo tiempo.");
        }
        if (mantenimiento.getVehiculo() == null && mantenimiento.getEquipo() == null) {
            throw new IllegalStateException("Un mantenimiento debe estar asociado a un vehículo o a un equipo.");
        }
        return mantenimientoRepository.save(mantenimiento);
    }

    @Transactional
    public void deleteById(Long id) {
        mantenimientoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Mantenimiento> findByVehiculo(Vehiculo vehiculo) {
        return mantenimientoRepository.findByVehiculo(vehiculo);
    }

    @Transactional(readOnly = true)
    public List<Mantenimiento> findByEquipo(Equipo equipo) {
        return mantenimientoRepository.findByEquipo(equipo);
    }

    @Transactional(readOnly = true)
    public List<Mantenimiento> findByTipo(TipoMantenimiento tipo) {
        return mantenimientoRepository.findByTipo(tipo);
    }
}