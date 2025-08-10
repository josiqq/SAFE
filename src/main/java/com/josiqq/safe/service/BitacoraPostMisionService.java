package com.josiqq.safe.service;

import com.josiqq.safe.model.BitacoraPostMision;
import com.josiqq.safe.model.Incidente;
import com.josiqq.safe.repository.BitacoraPostMisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BitacoraPostMisionService {

    @Autowired
    private BitacoraPostMisionRepository bitacoraRepository;

    @Transactional(readOnly = true)
    public List<BitacoraPostMision> findAll() {
        return bitacoraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<BitacoraPostMision> findById(Long id) {
        return bitacoraRepository.findById(id);
    }

    @Transactional
    public BitacoraPostMision save(BitacoraPostMision bitacora) {
        if (bitacora.getIncidente() == null) {
            throw new IllegalStateException("La bitácora debe estar asociada a un incidente.");
        }
        return bitacoraRepository.save(bitacora);
    }

    @Transactional
    public void deleteById(Long id) {
        bitacoraRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    /**
     * Permite encontrar la bitácora de un incidente específico,
     * que es la forma más común de consultarla.
     * @param incidente El incidente del cual se busca la bitácora.
     * @return Un Optional que puede contener la bitácora.
     */
    @Transactional(readOnly = true)
    public Optional<BitacoraPostMision> findByIncidente(Incidente incidente) {
        return bitacoraRepository.findByIncidente(incidente);
    }
}