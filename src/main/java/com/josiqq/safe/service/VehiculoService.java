package com.josiqq.safe.service;

import com.josiqq.safe.model.Vehiculo;
import com.josiqq.safe.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Transactional(readOnly = true)
    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehiculo> findById(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Transactional
    public Vehiculo save(Vehiculo vehiculo) {
        // Lógica de negocio: Validar que la placa no esté duplicada
        vehiculoRepository.findByPlaca(vehiculo.getPlaca()).ifPresent(v -> {
            if (!v.getId().equals(vehiculo.getId())) {
                throw new IllegalStateException("La placa ya está registrada en otro vehículo.");
            }
        });
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public void deleteById(Long id) {
        vehiculoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Vehiculo> findByEstado(String estado) {
        return vehiculoRepository.findByEstado(estado);
    }

}