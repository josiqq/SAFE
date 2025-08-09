package com.josiqq.safe.service;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.DocumentoInterno;
import com.josiqq.safe.models.DocumentoInterno.TipoDocumento;
import com.josiqq.safe.repository.DocumentoInternoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoInternoService {

    @Autowired
    private DocumentoInternoRepository documentoInternoRepository;

    @Transactional(readOnly = true)
    public List<DocumentoInterno> findAll() {
        return documentoInternoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DocumentoInterno> findById(Long id) {
        return documentoInternoRepository.findById(id);
    }

    @Transactional
    public DocumentoInterno save(DocumentoInterno documento) {
        if (documento.getAutor() == null) {
            throw new IllegalStateException("Un documento debe tener un autor.");
        }
        return documentoInternoRepository.save(documento);
    }

    @Transactional
    public void deleteById(Long id) {
        documentoInternoRepository.deleteById(id);
    }

    // --- Métodos de lógica de negocio específicos ---

    @Transactional(readOnly = true)
    public List<DocumentoInterno> findByTipo(TipoDocumento tipo) {
        return documentoInternoRepository.findByTipoDocumento(tipo);
    }

    @Transactional(readOnly = true)
    public List<DocumentoInterno> findByAutor(Bombero autor) {
        return documentoInternoRepository.findByAutor(autor);
    }
}