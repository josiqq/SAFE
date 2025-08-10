package com.josiqq.safe.service;

import com.josiqq.safe.model.Foto;
import com.josiqq.safe.model.Incidente;
import com.josiqq.safe.repository.FotoRepository;
import com.josiqq.safe.repository.IncidenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IncidenteService {

    @Autowired
    private IncidenteRepository incidenteRepository;

    // Inyectaremos otros servicios aquí cuando necesitemos coordinarlos
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FotoRepository fotoRepository;
    // @Autowired
    // private BomberoService bomberoService;
    // @Autowired
    // private VehiculoService vehiculoService;

    @Transactional(readOnly = true)
    public List<Incidente> findAll() {
        return incidenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Incidente> findById(Long id) {
        return incidenteRepository.findById(id);
    }

    @Transactional
    public Incidente save(Incidente incidente) {
        return incidenteRepository.save(incidente);
    }

    @Transactional
    public void deleteById(Long id) {
        incidenteRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Incidente> findByEstado(String estado) {
        return incidenteRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<Incidente> findByFecha(Date inicio, Date fin) {
        return incidenteRepository.findByFechaHoraInicioBetween(inicio, fin);
    }

    @Transactional
    public Foto agregarFotoAIncidente(Long incidenteId, MultipartFile archivoFoto) {
        // 1. Guardar el archivo en el disco y obtener su nombre único
        String nombreArchivo = fileStorageService.store(archivoFoto);

        // 2. Encontrar el incidente al que pertenece la foto
        Incidente incidente = incidenteRepository.findById(incidenteId)
                .orElseThrow(() -> new RuntimeException("Incidente no encontrado con id: " + incidenteId));

        // 3. Crear y guardar la entidad Foto en la base de datos
        Foto nuevaFoto = new Foto(nombreArchivo, incidente);
        return fotoRepository.save(nuevaFoto);
    }
}