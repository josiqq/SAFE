package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // camión cisterna, ambulancia, escalera
    private String placa;
    private String modelo;
    private int anio;
    private String estado; // Disponible, en uso, en mantenimiento
    private Date fechaUltimoMantenimiento;

    // Getters y Setters
}