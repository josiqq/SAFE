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
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String placa;
    private String marca;
    private String modelo;
    private Integer anio;
    private String tipo; // autobomba, cisterna, ambulancia
    private String estado; // operativo, en mantenimiento
    private Date fechaAdquisicion;
}