package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Compra;
import com.josiqq.safe.model.Proveedor;

import java.util.Date;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    /**
     * Busca todas las compras realizadas a un proveedor específico.
     * @param proveedor El proveedor de las compras.
     * @return Una lista de compras asociadas al proveedor.
     */
    List<Compra> findByProveedor(Proveedor proveedor);

    /**
     * Encuentra todas las compras realizadas dentro de un rango de fechas.
     * @param fechaInicio La fecha de inicio del periodo.
     * @param fechaFin La fecha de fin del periodo.
     * @return Una lista de compras realizadas en ese rango.
     */
    List<Compra> findByFechaBetween(Date fechaInicio, Date fechaFin);
}