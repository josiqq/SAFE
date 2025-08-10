package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Alerta;
import com.josiqq.safe.model.Rol;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    /**
     * Busca todas las alertas con una prioridad específica (ALTA, MEDIA, BAJA).
     * @param prioridad La prioridad de las alertas a buscar.
     * @return Una lista de alertas que coinciden con la prioridad.
     */
    List<Alerta> findByPrioridad(String prioridad);

    /**
     * Encuentra todas las alertas destinadas a un conjunto de roles.
     * @param rol El rol destinatario.
     * @return Una lista de alertas asociadas a ese rol.
     */
    List<Alerta> findByRolesDestinatariosContains(Rol rol);
}