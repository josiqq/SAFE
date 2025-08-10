package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre.
     * @param nombre El nombre del rol a buscar (ej: "jefe de unidad", "bombero").
     * @return Un Optional que contendrá el rol si existe.
     */
    Optional<Rol> findByNombre(String nombre);
}