package com.josiqq.safe.repository;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.Comunicacion;
import com.josiqq.safe.models.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComunicacionRepository extends JpaRepository<Comunicacion, Long> {

    /**
     * Busca todas las comunicaciones registradas durante un incidente específico.
     * @param incidente El incidente del cual se quieren obtener las comunicaciones.
     * @return Una lista de registros de comunicación.
     */
    List<Comunicacion> findByIncidente(Incidente incidente);

    /**
     * Encuentra todas las comunicaciones registradas por un bombero responsable.
     * @param responsable El bombero que registró la comunicación.
     * @return Una lista de comunicaciones.
     */
    List<Comunicacion> findByResponsable(Bombero responsable);
}