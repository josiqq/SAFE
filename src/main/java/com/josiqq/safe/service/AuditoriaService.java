package com.josiqq.safe.service;

import com.josiqq.safe.model.Auditoria;
import com.josiqq.safe.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    /**
     * Guarda un nuevo evento de auditoría.
     * Este es el método principal que usarán otros servicios para registrar acciones.
     * @param usuario El username del usuario que realiza la acción.
     * @param accion La acción realizada (ej: "CREAR_INCIDENTE").
     * @param entidadAfectada El nombre de la entidad afectada (ej: "Incidente").
     * @param idEntidadAfectada El ID de la entidad.
     */
    @Transactional
    public void registrarEvento(String usuario, String accion, String entidadAfectada, Long idEntidadAfectada) {
        Auditoria evento = new Auditoria();
        evento.setUsuario(usuario);
        evento.setAccion(accion);
        evento.setEntidadAfectada(entidadAfectada);
        evento.setIdEntidadAfectada(idEntidadAfectada);
        evento.setFechaHora(LocalDateTime.now());
        auditoriaRepository.save(evento);
    }

    // --- Métodos de consulta ---

    @Transactional(readOnly = true)
    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Auditoria> findByUsuario(String usuario) {
        return auditoriaRepository.findByUsuario(usuario);
    }
    
    @Transactional(readOnly = true)
    public List<Auditoria> findByFecha(LocalDateTime inicio, LocalDateTime fin) {
        return auditoriaRepository.findByFechaHoraBetween(inicio, fin);
    }
}