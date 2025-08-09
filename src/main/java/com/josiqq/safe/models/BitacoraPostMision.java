package com.josiqq.safe.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BitacoraPostMision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String resultados;

    @Lob
    private String heridos;

    @Lob
    private String danosMateriales;

    @Lob
    private String leccionesAprendidas;

    @OneToOne(mappedBy = "bitacora")
    private Incidente incidente;
}