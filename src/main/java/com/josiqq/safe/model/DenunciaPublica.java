package com.josiqq.safe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DenunciaPublica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String tipoEmergencia;
    private String descripcion;
    private Date fechaCreacion;
    private String estado; // pendiente, atendida, derivada
    
    @ManyToOne
    private Incidente incidenteGenerado; // si se crea un incidente
}