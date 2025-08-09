package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad que representa un mensaje en el sistema de
 * comunicación interna entre bomberos.
 */
@Data
@Entity
@Table(name = "mensajes_internos")
public class MensajeInterno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Bombero remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Bombero destinatario;

    @Column(nullable = false, length = 255)
    private String asunto;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String cuerpo;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    private boolean leido = false;

    @PrePersist
    public void prePersist() {
        fechaEnvio = LocalDateTime.now();
    }
}
