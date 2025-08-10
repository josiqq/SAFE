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
public class Comunicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Incidente incidente;
    
    private String entidadExterna; // policía, hospital, etc.
    private String tipoContacto; // llamada, radio, email
    private Date fechaHora;
    private String descripcion;
    
    @ManyToOne
    private Bombero responsable;
}