package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // bombero, paramédico, jefe, administrativo

    @OneToMany(mappedBy = "rol")
    private Set<Bombero> bomberos;

    // --- Constructor requerido por JPA ---

    public Rol() {
    }

    // --- Getters y Setters ---

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Bombero> getBomberos() {
        return this.bomberos;
    }

    public void setBomberos(Set<Bombero> bomberos) {
        this.bomberos = bomberos;
    }
}