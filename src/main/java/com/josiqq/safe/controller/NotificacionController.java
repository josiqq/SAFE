package com.josiqq.safe.controller;

import com.josiqq.safe.service.NotificacionService;
import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.Notificacion;
import com.josiqq.safe.service.BomberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private BomberoService bomberoService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> getNotificacionesNoLeidas(Principal principal) {
        Bombero destinatario = bomberoService.findByUsername(principal.getName()).orElseThrow();
        List<Notificacion> noLeidas = notificacionService.findNoLeidasByDestinatario(destinatario);
        return ResponseEntity.ok(noLeidas);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCountNotificacionesNoLeidas(Principal principal) {
        Bombero destinatario = bomberoService.findByUsername(principal.getName()).orElseThrow();
        long count = notificacionService.countNoLeidasByDestinatario(destinatario);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PostMapping("/marcar-leidas")
    public ResponseEntity<Void> marcarComoLeidas(Principal principal) {
        Bombero destinatario = bomberoService.findByUsername(principal.getName()).orElseThrow();
        List<Notificacion> noLeidas = notificacionService.findNoLeidasByDestinatario(destinatario);
        if (!noLeidas.isEmpty()) {
            notificacionService.marcarComoLeidas(noLeidas);
        }
        return ResponseEntity.ok().build();
    }
}