package com.josiqq.safe.controller;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.MensajeInterno;
import com.josiqq.safe.service.BomberoService;
import com.josiqq.safe.service.MensajeInternoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    private BomberoService bomberoService;

    @Autowired
    private MensajeInternoService mensajeService;

    @GetMapping("/chat")
    public String mostrarInterfazDeChat(Model model, Principal principal) {
        String username = principal.getName();
        List<Bombero> otrosBomberos = bomberoService.findAll().stream()
                .filter(b -> !b.getUsername().equals(username))
                .collect(Collectors.toList());

        model.addAttribute("currentUserUsername", username);
        model.addAttribute("otrosBomberos", otrosBomberos);
        return "chat/interfaz";
    }

    @GetMapping("/api/mensajes/{destinatarioUsername}")
    @ResponseBody
    public ResponseEntity<List<MensajeInterno>> getHistorialDeMensajes(
            @PathVariable String destinatarioUsername,
            Principal principal) {
        
        Bombero remitente = bomberoService.findByUsername(principal.getName()).orElseThrow();
        Bombero destinatario = bomberoService.findByUsername(destinatarioUsername).orElseThrow();

        List<MensajeInterno> historial = mensajeService.findChatHistory(remitente, destinatario);
        return ResponseEntity.ok(historial);
    }
}