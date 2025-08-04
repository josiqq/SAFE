package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Capacitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Date fecha;
    private String tipo; // Teórica, práctica, simulacro

    @ManyToMany
    private Set<Bombero> participantes;

    // Getters y Setters
}