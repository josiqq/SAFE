package com.josiqq.safe.controller;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.MensajeInterno;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.MensajeInternoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MensajeController {

    @Autowired
    private MensajeInternoService mensajeService;

    @Autowired
    private BomberoService bomberoService;

    @MessageMapping("/chat.enviar")
    public void enviarMensaje(@Payload MensajeInterno mensaje, Principal principal) {
        Bombero remitente = bomberoService.findByUsername(principal.getName()).orElseThrow();
        mensaje.setRemitente(remitente);

        Bombero destinatario = bomberoService.findByUsername(mensaje.getDestinatario().getUsername()).orElseThrow();
        mensaje.setDestinatario(destinatario);

        mensaje.setAsunto("Mensaje de Chat" + " " + mensaje.getRemitente().getUsername());

        mensajeService.enviarMensaje(mensaje);
    }
}