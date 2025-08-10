package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Turno;

import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    /**
     * Busca un turno por su nombre.
     * @param nombre El nombre del turno (ej: "Turno A").
     * @return Un Optional que contendrá el turno si existe.
     */
    Optional<Turno> findByNombre(String nombre);
}