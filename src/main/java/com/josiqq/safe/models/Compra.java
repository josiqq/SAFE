package com.josiqq.safe.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    private String descripcion;
    private BigDecimal monto;
    private Date fecha;

    // Getters y Setters
    
}
