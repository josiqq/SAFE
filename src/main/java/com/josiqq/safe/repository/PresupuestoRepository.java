package com.josiqq.safe.repository;

import com.josiqq.safe.models.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    /**
     * Busca el presupuesto para una categoría en un año específico.
     * @param categoria La categoría del presupuesto (ej: "equipamiento", "combustible").
     * @param anio El año del presupuesto.
     * @return Un Optional que puede contener el registro del presupuesto.
     */
    Optional<Presupuesto> findByCategoriaAndAnio(String categoria, Integer anio);

    /**
     * Encuentra todos los registros de presupuesto para un año determinado.
     * @param anio El año para el cual se quieren obtener los presupuestos.
     * @return Una lista de todos los presupuestos de ese año.
     */
    List<Presupuesto> findByAnio(Integer anio);
}