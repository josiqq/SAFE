package com.josiqq.safe.service;

import com.josiqq.safe.dto.MensajeDTO;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.MensajeInterno;
import com.josiqq.safe.model.Notificacion.TipoNotificacion;
import com.josiqq.safe.repository.MensajeInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeInternoService {

    @Autowired
    private MensajeInternoRepository mensajeRepository;

    @Autowired
    private NotificacionService notificacionService; 

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Transactional(readOnly = true)
    public List<MensajeInterno> findAll() {
        return mensajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MensajeInterno> findById(Long id) {
        return mensajeRepository.findById(id);
    }

    @Transactional
    public MensajeInterno enviarMensaje(MensajeInterno mensaje) {
        if (mensaje.getRemitente() == null || mensaje.getDestinatario() == null) {
            throw new IllegalStateException("El mensaje debe tener un remitente y un destinatario.");
        }
        
        MensajeInterno mensajeGuardado = mensajeRepository.save(mensaje);

        String textoNotificacion = mensaje.getRemitente().getNombre() + " te envió un mensaje.";
        notificacionService.crearNotificacion(
            mensaje.getDestinatario(),
            textoNotificacion,
            TipoNotificacion.MENSAJE,
            "/chat"
        );
        
        messagingTemplate.convertAndSendToUser(
            mensajeGuardado.getDestinatario().getUsername(),
            "/queue/messages",
            new MensajeDTO(mensajeGuardado)
        );

        return mensajeGuardado;
    }

    @Transactional(readOnly = true)
    public List<MensajeInterno> findByDestinatario(Bombero destinatario) {
        return mensajeRepository.findByDestinatario(destinatario);
    }

    @Transactional(readOnly = true)
    public List<MensajeInterno> findMensajesNoLeidos(Bombero destinatario) {
        return mensajeRepository.findByDestinatarioAndLeido(destinatario, false);
    }

    @Transactional(readOnly = true)
    public List<MensajeInterno> findChatHistory(Bombero usuario1, Bombero usuario2) {
        return mensajeRepository.findChatHistory(usuario1, usuario2);
    }
}