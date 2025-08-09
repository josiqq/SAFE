// safe/src/main/java/com/josiqq/safe/models/Rol.java
package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre; // jefe de unidad, bombero, aspirante
    private String descripcion;
}