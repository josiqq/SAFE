package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; 
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Bombero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;

    @Lob
    private String historialMedico;

    @OneToMany(mappedBy = "bombero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificado> certificados;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @ManyToMany(mappedBy = "bomberos")
    private Set<Turno> turnos;
}