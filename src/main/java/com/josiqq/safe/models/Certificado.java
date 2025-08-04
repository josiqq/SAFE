package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String entidadEmisora;
    private Date fechaEmision;
    private Date fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "bombero_id")
    private Bombero bombero;

    // Getters y Setters
}