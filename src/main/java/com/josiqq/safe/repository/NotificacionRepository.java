package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.Notificacion;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByDestinatarioAndLeidoOrderByFechaDesc(Bombero destinatario, boolean leido);

    long countByDestinatarioAndLeido(Bombero destinatario, boolean leido);
}