package com.josiqq.safe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluaciones_desempeno")
public class EvaluacionDesempeno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bombero_evaluado_id", nullable = false)
    private Bombero bomberoEvaluado;

    @ManyToOne
    @JoinColumn(name = "evaluador_id", nullable = false)
    private Bombero evaluador;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Integer puntuacion; // Por ejemplo, en una escala de 1 a 10

    @Lob
    @Column(columnDefinition = "TEXT")
    private String observaciones;
}