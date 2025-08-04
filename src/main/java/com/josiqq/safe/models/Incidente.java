package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // incendio, rescate, materiales peligrosos
    private String ubicacion;
    private Date fechaHoraInicio;
    private Date fechaHoraFin;
    private String descripcion;
    private String estado; // Activo, cerrado

    @ManyToMany
    private List<Bombero> personalAsignado;

    @ManyToMany
    private List<Vehiculo> vehiculosAsignados;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bitacora_id", referencedColumnName = "id")
    private BitacoraPostMision bitacora;

    // Getters y Setters
}