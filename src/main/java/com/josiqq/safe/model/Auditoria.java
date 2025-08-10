package com.josiqq.safe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario; // username del bombero que realiza la acción
    private String accion; // "CREAR", "MODIFICAR", "ELIMINAR", "LOGIN"
    private String entidadAfectada; // "Bombero", "Incidente", etc.
    private Long idEntidadAfectada;
    private LocalDateTime fechaHora;

    @Lob
    private String detalle; // Para guardar datos viejos/nuevos en formato JSON
}