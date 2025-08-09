package com.josiqq.safe.repository;

import com.josiqq.safe.models.Mantenimiento;
import com.josiqq.safe.models.Vehiculo;
import com.josiqq.safe.models.Equipo;
import com.josiqq.safe.models.Mantenimiento.TipoMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {

    /**
     * Busca todos los mantenimientos registrados para un vehículo específico.
     * @param vehiculo El vehículo cuyo historial de mantenimiento se quiere consultar.
     * @return Una lista de mantenimientos para ese vehículo.
     */
    List<Mantenimiento> findByVehiculo(Vehiculo vehiculo);

    /**
     * Busca todos los mantenimientos registrados para una pieza de equipo específica.
     * @param equipo El equipo cuyo historial de mantenimiento se quiere consultar.
     * @return Una lista de mantenimientos para ese equipo.
     */
    List<Mantenimiento> findByEquipo(Equipo equipo);

    /**
     * Encuentra todos los mantenimientos de un tipo específico.
     * @param tipo El tipo de mantenimiento (PREVENTIVO o CORRECTIVO).
     * @return Una lista de mantenimientos de ese tipo.
     */
    List<Mantenimiento> findByTipo(TipoMantenimiento tipo);
}