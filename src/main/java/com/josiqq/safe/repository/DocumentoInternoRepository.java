package com.josiqq.safe.repository;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.DocumentoInterno;
import com.josiqq.safe.models.DocumentoInterno.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentoInternoRepository extends JpaRepository<DocumentoInterno, Long> {

    /**
     * Busca todos los documentos de un tipo específico.
     * @param tipoDocumento El tipo de documento (CIRCULAR, PERMISO, ACTA, etc.).
     * @return Una lista de documentos de ese tipo.
     */
    List<DocumentoInterno> findByTipoDocumento(TipoDocumento tipoDocumento);

    /**
     * Encuentra todos los documentos creados por un autor específico.
     * @param autor El bombero que es autor de los documentos.
     * @return Una lista de documentos creados por ese autor.
     */
    List<DocumentoInterno> findByAutor(Bombero autor);
}