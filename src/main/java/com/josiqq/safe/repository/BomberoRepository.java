package com.josiqq.safe.repository;

import com.josiqq.safe.models.Bombero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BomberoRepository extends JpaRepository<Bombero, Long> {
    Optional<Bombero> findByUsername(String username);
}