package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Bombero;
import com.josiqq.safe.model.Certificado;

import java.util.Date;
import java.util.List;

public interface CertificadoRepository extends JpaRepository<Certificado, Long> {

    /**
     * Busca todos los certificados pertenecientes a un bombero específico.
     * @param bombero El bombero cuyos certificados se quieren obtener.
     * @return Una lista de sus certificados.
     */
    List<Certificado> findByBombero(Bombero bombero);

    /**
     * Encuentra certificados que están a punto de vencer o ya vencieron.
     * Ideal para generar alertas de renovación.
     * @param fechaVencimiento La fecha límite de vencimiento.
     * @return Una lista de certificados que vencen antes o en la fecha dada.
     */
    List<Certificado> findByFechaVencimientoBefore(Date fechaVencimiento);
}