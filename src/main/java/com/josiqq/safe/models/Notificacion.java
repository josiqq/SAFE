package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Notificacion {

    public enum TipoNotificacion {
        MENSAJE,
        MANTENIMIENTO,
        ALERTA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Bombero destinatario;

    @Column(nullable = false)
    private String mensaje;

    private boolean leido = false;

    private LocalDateTime fecha = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    private String enlace; // URL a la que redirige la notificación (ej: /chat o /mantenimientos/5)
}