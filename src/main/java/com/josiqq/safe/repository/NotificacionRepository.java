package com.josiqq.safe.repository;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByDestinatarioAndLeidoOrderByFechaDesc(Bombero destinatario, boolean leido);

    long countByDestinatarioAndLeido(Bombero destinatario, boolean leido);
}