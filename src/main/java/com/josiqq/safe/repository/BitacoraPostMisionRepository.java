package com.josiqq.safe.repository;

import com.josiqq.safe.models.BitacoraPostMision;
import com.josiqq.safe.models.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BitacoraPostMisionRepository extends JpaRepository<BitacoraPostMision, Long> {

    /**
     * Busca la bitácora asociada a un incidente específico.
     * @param incidente El incidente del cual se quiere obtener la bitácora.
     * @return Un Optional que puede contener la bitácora si existe.
     */
    Optional<BitacoraPostMision> findByIncidente(Incidente incidente);
}