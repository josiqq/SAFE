package com.josiqq.safe.repository;

import com.josiqq.safe.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query("SELECT DISTINCT t FROM Turno t LEFT JOIN FETCH t.bomberos")
    List<Turno> findAllWithBomberos();
    
    Optional<Turno> findByNombre(String nombre);
}

