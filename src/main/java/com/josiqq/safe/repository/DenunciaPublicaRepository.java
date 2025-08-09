package com.josiqq.safe.repository;

import com.josiqq.safe.models.DenunciaPublica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DenunciaPublicaRepository extends JpaRepository<DenunciaPublica, Long> {

    /**
     * Busca todas las denuncias públicas que se encuentran en un estado específico.
     * @param estado El estado de la denuncia (ej: "pendiente", "atendida", "derivada").
     * @return Una lista de denuncias que coinciden con el estado.
     */
    List<DenunciaPublica> findByEstado(String estado);

    /**
     * Encuentra denuncias por el tipo de emergencia reportado.
     * @param tipoEmergencia El tipo de emergencia (ej: "incendio", "rescate").
     * @return Una lista de denuncias para ese tipo de emergencia.
     */
    List<DenunciaPublica> findByTipoEmergencia(String tipoEmergencia);
}