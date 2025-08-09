package com.josiqq.safe.repository;

import com.josiqq.safe.models.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    /**
     * Busca todos los registros de auditoría realizados por un usuario específico.
     * @param usuario El nombre de usuario que realizó la acción.
     * @return Una lista de registros de auditoría.
     */
    List<Auditoria> findByUsuario(String usuario);

    /**
     * Encuentra registros de auditoría por el tipo de acción (CREAR, MODIFICAR, etc.)
     * @param accion El tipo de acción a buscar.
     * @return Una lista de registros de auditoría.
     */
    List<Auditoria> findByAccion(String accion);
    
    /**
     * Busca registros de auditoría dentro de un rango de fechas.
     * @param fechaInicio La fecha y hora de inicio.
     * @param fechaFin La fecha y hora de fin.
     * @return Una lista de registros de auditoría en el rango especificado.
     */
    List<Auditoria> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}