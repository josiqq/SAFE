package com.josiqq.safe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomberoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
}
