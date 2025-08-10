package com.josiqq.safe.service;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.repository.BomberoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BomberoService {

    @Autowired
    private BomberoRepository bomberoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Bombero> findAll() {
        return bomberoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Bombero> findById(Long id) {
        return bomberoRepository.findById(id);
    }

    @Transactional
    public Bombero save(Bombero bombero) {
        if (bombero.getPassword() != null && !bombero.getPassword().startsWith("$2a$")) {
            bombero.setPassword(passwordEncoder.encode(bombero.getPassword()));
        }
        return bomberoRepository.save(bombero);
    }

    @Transactional
    public void deleteById(Long id) {
        bomberoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public Optional<Bombero> findByUsername(String username) {
        return bomberoRepository.findByUsername(username);
    }
}