package com.josiqq.safe.service;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.Certificado;
import com.josiqq.safe.repository.CertificadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CertificadoService {

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Transactional(readOnly = true)
    public List<Certificado> findAll() {
        return certificadoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Certificado> findById(Long id) {
        return certificadoRepository.findById(id);
    }

    @Transactional
    public Certificado save(Certificado certificado) {
        // Un certificado debe pertenecer a un bombero
        if (certificado.getBombero() == null) {
            throw new IllegalStateException("El certificado debe estar asociado a un bombero.");
        }
        return certificadoRepository.save(certificado);
    }

    @Transactional
    public void deleteById(Long id) {
        certificadoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<Certificado> findByBombero(Bombero bombero) {
        return certificadoRepository.findByBombero(bombero);
    }

    /**
     * Lógica de negocio para encontrar certificados que ya están vencidos.
     * @return Una lista de certificados vencidos.
     */
    @Transactional(readOnly = true)
    public List<Certificado> findCertificadosVencidos() {
        return certificadoRepository.findByFechaVencimientoBefore(new Date());
    }
}