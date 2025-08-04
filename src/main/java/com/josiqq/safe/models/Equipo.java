package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String estado; // Optimo, en reparación, fuera de servicio
    private String ubicacion;
    private Date fechaAdquisicion;
    private Date fechaUltimoMantenimiento;

    // Getters y Setters
}