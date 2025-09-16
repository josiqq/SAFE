package com.josiqq.safe.dto;

import java.time.LocalDate;

/**
 * DTO para patrones de ausentismo
 */
public class PatronAusentismoDTO {
    private final String nombreBombero;
    private final int diasConsecutivos;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    
    public PatronAusentismoDTO(String nombreBombero, int diasConsecutivos, 
                              LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombreBombero = nombreBombero;
        this.diasConsecutivos = diasConsecutivos;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    // Getters
    public String getNombreBombero() { return nombreBombero; }
    public int getDiasConsecutivos() { return diasConsecutivos; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
}