package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;

    @ManyToMany
    @JoinTable(
        name = "bombero_turno",
        joinColumns = @JoinColumn(name = "turno_id"),
        inverseJoinColumns = @JoinColumn(name = "bombero_id"))
    private Set<Bombero> bomberos;

    // Getters y Setters
}