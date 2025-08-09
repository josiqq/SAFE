package com.josiqq.safe.service;

import com.josiqq.safe.models.DenunciaPublica;
import com.josiqq.safe.repository.DenunciaPublicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DenunciaPublicaService {

    @Autowired
    private DenunciaPublicaRepository denunciaPublicaRepository;

    // Podríamos inyectar IncidenteService para crear un incidente desde una denuncia.
    // @Autowired
    // private IncidenteService incidenteService;

    @Transactional(readOnly = true)
    public List<DenunciaPublica> findAll() {
        return denunciaPublicaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DenunciaPublica> findById(Long id) {
        return denunciaPublicaRepository.findById(id);
    }

    @Transactional
    public DenunciaPublica save(DenunciaPublica denuncia) {
        return denunciaPublicaRepository.save(denuncia);
    }

    // Generalmente, las denuncias no se eliminan, se marcan como "descartada" o "atendida".
    // public void deleteById(Long id) { ... }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<DenunciaPublica> findByEstado(String estado) {
        return denunciaPublicaRepository.findByEstado(estado);
    }

    /*
    @Transactional
    public Incidente generarIncidenteDesdeDenuncia(Long denunciaId) {
        DenunciaPublica denuncia = findById(denunciaId)
            .orElseThrow(() -> new RuntimeException("Denuncia no encontrada"));

        // Lógica para transformar una denuncia en un nuevo incidente
        // y luego guardarlo usando incidenteService.
        // ...

        denuncia.setEstado("Atendida");
        denunciaPublicaRepository.save(denuncia);

        // return nuevoIncidente;
    }
    */
}