package com.josiqq.safe.models;

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
    private Incidente incidente;

    public Foto(String nombreArchivo, Incidente incidente) {
        this.nombreArchivo = nombreArchivo;
        this.incidente = incidente;
    }
}