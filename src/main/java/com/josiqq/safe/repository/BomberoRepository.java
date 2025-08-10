package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Bombero;

import java.util.Optional;

public interface BomberoRepository extends JpaRepository<Bombero, Long> {
    Optional<Bombero> findByUsername(String username);
}