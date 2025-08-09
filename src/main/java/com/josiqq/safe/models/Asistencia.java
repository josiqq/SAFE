package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    private LocalTime horaEntrada;
    private LocalTime horaSalida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAsistencia estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bombero_id", nullable = false)
    private Bombero bombero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_id")
    private Turno turno;

    public enum EstadoAsistencia {
        PRESENTE,
        AUSENTE,
        JUSTIFICADO,
        RETRASO
    }
}