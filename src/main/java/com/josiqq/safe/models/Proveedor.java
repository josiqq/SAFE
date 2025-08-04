package com.josiqq.safe.models;

import jakarta.persistence.*;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;

    // Getters y Setters
}