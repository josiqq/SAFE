package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre; // Turno A, B, C
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    @ManyToMany
    @JoinTable(
        name = "bombero_turno",
        joinColumns = @JoinColumn(name = "turno_id"),
        inverseJoinColumns = @JoinColumn(name = "bombero_id")
    )
    private Set<Bombero> bomberos;
}