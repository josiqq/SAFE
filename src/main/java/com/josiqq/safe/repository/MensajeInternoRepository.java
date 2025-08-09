package com.josiqq.safe.repository;

import com.josiqq.safe.models.Bombero;
import com.josiqq.safe.models.MensajeInterno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeInternoRepository extends JpaRepository<MensajeInterno, Long> {

    /**
     * Busca todos los mensajes enviados por un remitente específico.
     * @param remitente El bombero que envió los mensajes.
     * @return Una lista de mensajes enviados.
     */
    List<MensajeInterno> findByRemitente(Bombero remitente);

    /**
     * Busca todos los mensajes recibidos por un destinatario específico.
     * @param destinatario El bombero que recibió los mensajes.
     * @return Una lista de mensajes recibidos.
     */
    List<MensajeInterno> findByDestinatario(Bombero destinatario);

    /**
     * Encuentra todos los mensajes no leídos para un destinatario.
     * Ideal para mostrar contadores de notificaciones.
     * @param destinatario El bombero destinatario.
     * @param leido El estado de lectura (debe ser 'false').
     * @return Una lista de mensajes no leídos.
     */
    List<MensajeInterno> findByDestinatarioAndLeido(Bombero destinatario, boolean leido);

    /**
     * Busca el historial de chat entre dos usuarios, ordenado por fecha.
     */
    @Query("SELECT m FROM MensajeInterno m WHERE " +
           "(m.remitente = :usuario1 AND m.destinatario = :usuario2) OR " +
           "(m.remitente = :usuario2 AND m.destinatario = :usuario1) " +
           "ORDER BY m.fechaEnvio ASC")
    List<MensajeInterno> findChatHistory(@Param("usuario1") Bombero usuario1, @Param("usuario2") Bombero usuario2);
}