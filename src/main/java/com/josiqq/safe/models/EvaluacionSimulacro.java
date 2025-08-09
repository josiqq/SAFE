package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EvaluacionSimulacro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Simulacro simulacro;
    
    @ManyToOne
    private Bombero participante;
    
    private Integer puntuacion;
    private String observaciones;
    private Date fecha;
}