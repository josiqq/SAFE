package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String categoria; // equipamiento, combustible, mantenimiento
    private BigDecimal montoAsignado;
    private BigDecimal montoGastado;
    private Integer anio;
    private Integer mes;
}