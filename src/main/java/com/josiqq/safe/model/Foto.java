package com.josiqq.safe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidente_id")
    @JsonIgnore
    private Incidente incidente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    @JsonBackReference
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bombero_id")
    @JsonBackReference
    private Bombero bombero;

    public Foto(String nombreArchivo, Incidente incidente) {
        this.nombreArchivo = nombreArchivo;
        this.incidente = incidente;
    }

    public Foto(String nombreArchivo, Vehiculo vehiculo) {
        this.nombreArchivo = nombreArchivo;
        this.vehiculo = vehiculo;
    }

    public Foto(String nombreArchivo, Bombero bombero) {
        this.nombreArchivo = nombreArchivo;
        this.bombero = bombero;
    }
}