package com.josiqq.safe.repository;

import com.josiqq.safe.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    /**
     * Busca un vehículo por su número de placa, que debería ser único.
     * @param placa La placa del vehículo a buscar.
     * @return Un Optional que puede contener el vehículo si se encuentra.
     */
    Optional<Vehiculo> findByPlaca(String placa);

    /**
     * Busca todos los vehículos que se encuentran en un estado específico.
     * @param estado El estado del vehículo (ej: "operativo", "en mantenimiento").
     * @return Una lista de vehículos en ese estado.
     */
    List<Vehiculo> findByEstado(String estado);
}