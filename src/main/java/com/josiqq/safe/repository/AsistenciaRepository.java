// Métodos adicionales para AsistenciaRepository.java

package com.josiqq.safe.repository;

import com.josiqq.safe.model.Asistencia;
import com.josiqq.safe.model.Bombero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    /**
     * Busca asistencias por bombero específico
     */
    List<Asistencia> findByBombero(Bombero bombero);

    /**
     * Busca asistencias por fecha específica
     */
    List<Asistencia> findByFecha(LocalDate fecha);

    /**
     * Busca asistencias por bombero y fecha específica
     */
    List<Asistencia> findByBomberoAndFecha(Bombero bombero, LocalDate fecha);

    /**
     * Busca asistencias por bombero en un rango de fechas
     */
    List<Asistencia> findByBomberoAndFechaBetween(Bombero bombero, LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Busca asistencias en un rango de fechas
     */
    List<Asistencia> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Busca asistencias por estado específico
     */
    List<Asistencia> findByEstado(Asistencia.EstadoAsistencia estado);

    /**
     * Busca asistencias por estado en una fecha específica
     */
    List<Asistencia> findByFechaAndEstado(LocalDate fecha, Asistencia.EstadoAsistencia estado);

    /**
     * Cuenta asistencias por bombero en un rango de fechas
     */
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.bombero = :bombero AND a.fecha BETWEEN :fechaInicio AND :fechaFin")
    Long countByBomberoAndFechaBetween(@Param("bombero") Bombero bombero, 
                                       @Param("fechaInicio") LocalDate fechaInicio, 
                                       @Param("fechaFin") LocalDate fechaFin);

    /**
     * Cuenta asistencias por bombero y estado en un rango de fechas
     */
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE a.bombero = :bombero AND a.estado = :estado AND a.fecha BETWEEN :fechaInicio AND :fechaFin")
    Long countByBomberoAndEstadoAndFechaBetween(@Param("bombero") Bombero bombero,
                                                @Param("estado") Asistencia.EstadoAsistencia estado,
                                                @Param("fechaInicio") LocalDate fechaInicio,
                                                @Param("fechaFin") LocalDate fechaFin);

    /**
     * Obtiene estadísticas de asistencia por fecha
     */
    @Query("SELECT a.estado, COUNT(a) FROM Asistencia a WHERE a.fecha = :fecha GROUP BY a.estado")
    List<Object[]> getEstadisticasByFecha(@Param("fecha") LocalDate fecha);

    /**
     * Obtiene estadísticas de asistencia en un rango de fechas
     */
    @Query("SELECT a.estado, COUNT(a) FROM Asistencia a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY a.estado")
    List<Object[]> getEstadisticasByFechaRange(@Param("fechaInicio") LocalDate fechaInicio, 
                                               @Param("fechaFin") LocalDate fechaFin);

    /**
     * Encuentra bomberos con más asistencias en un período
     */
    @Query("SELECT a.bombero, COUNT(a) as total FROM Asistencia a WHERE a.estado = :estado AND a.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY a.bombero ORDER BY total DESC")
    List<Object[]> findTopBomberosByEstadoAndFecha(@Param("estado") Asistencia.EstadoAsistencia estado,
                                                    @Param("fechaInicio") LocalDate fechaInicio,
                                                    @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca bomberos que no han registrado asistencia en una fecha
     */
    @Query("SELECT b FROM Bombero b WHERE b.id NOT IN (SELECT DISTINCT a.bombero.id FROM Asistencia a WHERE a.fecha = :fecha)")
    List<Bombero> findBomberosSinAsistenciaEnFecha(@Param("fecha") LocalDate fecha);

    /**
     * Busca asistencias con retraso (hora entrada después de hora inicio del turno)
     */
    @Query("SELECT a FROM Asistencia a JOIN a.turno t WHERE a.horaEntrada > t.horaInicio AND a.fecha = :fecha")
    List<Asistencia> findAsistenciasConRetrasoEnFecha(@Param("fecha") LocalDate fecha);

    /**
     * Cuenta total de asistencias por mes y año
     */
    @Query("SELECT COUNT(a) FROM Asistencia a WHERE MONTH(a.fecha) = :mes AND YEAR(a.fecha) = :anio")
    Long countByMesAndAnio(@Param("mes") int mes, @Param("anio") int anio);

    /**
     * Obtiene asistencias ordenadas por fecha descendente
     */
    List<Asistencia> findByBomberoOrderByFechaDesc(Bombero bombero);

    /**
     * Busca últimas N asistencias de un bombero
     */
    @Query("SELECT a FROM Asistencia a WHERE a.bombero = :bombero ORDER BY a.fecha DESC, a.id DESC")
    List<Asistencia> findTopNByBomberoOrderByFechaDesc(@Param("bombero") Bombero bombero, 
                                                       org.springframework.data.domain.Pageable pageable);

    /**
     * Verifica si existe asistencia para un bombero en una fecha específica
     */
    boolean existsByBomberoAndFecha(Bombero bombero, LocalDate fecha);

    /**
     * Busca asistencias con horas de entrada y salida registradas
     */
    @Query("SELECT a FROM Asistencia a WHERE a.horaEntrada IS NOT NULL AND a.horaSalida IS NOT NULL AND a.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Asistencia> findAsistenciasCompletasEnPeriodo(@Param("fechaInicio") LocalDate fechaInicio, 
                                                       @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca patrones de ausentismo - bomberos con X días consecutivos de ausencia
     */
    @Query(value = "WITH RECURSIVE fecha_serie AS (" +
           "    SELECT DATE_SUB(CURDATE(), INTERVAL 30 DAY) as fecha " +
           "    UNION ALL " +
           "    SELECT DATE_ADD(fecha, INTERVAL 1 DAY) " +
           "    FROM fecha_serie " +
           "    WHERE fecha < CURDATE() " +
           "), " +
           "ausencias_consecutivas AS (" +
           "    SELECT b.id as bombero_id, " +
           "           fs.fecha, " +
           "           CASE WHEN a.estado = 'AUSENTE' OR a.id IS NULL THEN 1 ELSE 0 END as es_ausencia " +
           "    FROM Bombero b " +
           "    CROSS JOIN fecha_serie fs " +
           "    LEFT JOIN Asistencia a ON a.bombero_id = b.id AND a.fecha = fs.fecha " +
           ") " +
           "SELECT bombero_id, COUNT(*) as dias_consecutivos " +
           "FROM ausencias_consecutivas " +
           "WHERE es_ausencia = 1 " +
           "GROUP BY bombero_id " +
           "HAVING COUNT(*) >= :diasMinimos", 
           nativeQuery = true)
    List<Object[]> findPatronesAusentismo(@Param("diasMinimos") int diasMinimos);

    /**
     * Obtiene promedio de horas trabajadas por bombero en un período
     */
    @Query("SELECT a.bombero, AVG(TIMESTAMPDIFF(MINUTE, a.horaEntrada, a.horaSalida)) as promedioMinutos " +
           "FROM Asistencia a " +
           "WHERE a.horaEntrada IS NOT NULL AND a.horaSalida IS NOT NULL " +
           "AND a.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY a.bombero")
    List<Object[]> getPromedioHorasTrabajadasPorBombero(@Param("fechaInicio") LocalDate fechaInicio,
                                                         @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca asistencias por turno en una fecha específica
     */
    @Query("SELECT a FROM Asistencia a WHERE a.turno.id = :turnoId AND a.fecha = :fecha")
    List<Asistencia> findByTurnoAndFecha(@Param("turnoId") Long turnoId, @Param("fecha") LocalDate fecha);

    /**
     * Obtiene estadísticas por turno en un período
     */
    @Query("SELECT a.turno.nombre, a.estado, COUNT(a) " +
           "FROM Asistencia a " +
           "WHERE a.turno IS NOT NULL AND a.fecha BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY a.turno.nombre, a.estado " +
           "ORDER BY a.turno.nombre, a.estado")
    List<Object[]> getEstadisticasPorTurnoEnPeriodo(@Param("fechaInicio") LocalDate fechaInicio,
                                                    @Param("fechaFin") LocalDate fechaFin);

}