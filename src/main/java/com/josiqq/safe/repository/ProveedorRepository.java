package com.josiqq.safe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josiqq.safe.model.Proveedor;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Busca un proveedor por su número de RUC.
     * @param ruc El RUC del proveedor a buscar.
     * @return Un Optional que puede contener al proveedor si se encuentra.
     */
    Optional<Proveedor> findByRuc(String ruc);

    /**
     * Busca un proveedor por su dirección de correo electrónico.
     * @param email El email del proveedor.
     * @return Un Optional que puede contener al proveedor si se encuentra.
     */
    Optional<Proveedor> findByEmail(String email);
}