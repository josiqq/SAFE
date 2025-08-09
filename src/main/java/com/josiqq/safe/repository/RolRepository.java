package com.josiqq.safe.repository;

import com.josiqq.safe.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre.
     * @param nombre El nombre del rol a buscar (ej: "jefe de unidad", "bombero").
     * @return Un Optional que contendrá el rol si existe.
     */
    Optional<Rol> findByNombre(String nombre);
}