package com.josiqq.safe.service;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.Notificacion;
import com.josiqq.safe.models.Notificacion.TipoNotificacion;
import com.josiqq.safe.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Transactional
    public void crearNotificacion(Bombero destinatario, String mensaje, TipoNotificacion tipo, String enlace) {
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario(destinatario);
        notificacion.setMensaje(mensaje);
        notificacion.setTipo(tipo);
        notificacion.setEnlace(enlace);
        notificacionRepository.save(notificacion);
    }

    @Transactional(readOnly = true)
    public List<Notificacion> findNoLeidasByDestinatario(Bombero destinatario) {
        return notificacionRepository.findByDestinatarioAndLeidoOrderByFechaDesc(destinatario, false);
    }

    @Transactional(readOnly = true)
    public long countNoLeidasByDestinatario(Bombero destinatario) {
        return notificacionRepository.countByDestinatarioAndLeido(destinatario, false);
    }

    @Transactional
    public void marcarComoLeidas(List<Notificacion> notificaciones) {
        notificaciones.forEach(n -> n.setLeido(true));
        notificacionRepository.saveAll(notificaciones);
    }
}