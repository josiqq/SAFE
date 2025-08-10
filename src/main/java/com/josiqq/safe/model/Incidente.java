package com.josiqq.safe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidentes")
public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // incendio, rescate, materiales peligrosos
    private String ubicacion;
    private LocalDate fechaHoraInicio;
    private LocalDate fechaHoraFin;
    private String descripcion;
    private String estado; // Activo, cerrado

    @ManyToMany
    private List<Bombero> personalAsignado;

    @ManyToMany
    private List<Vehiculo> vehiculosAsignados;

    @ManyToMany
    private List<Equipo> equiposAsignados;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bitacora_id", referencedColumnName = "id")
    private BitacoraPostMision bitacora;

    @OneToMany(mappedBy = "incidente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos;
}