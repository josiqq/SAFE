package com.josiqq.safe.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para reportes de asistencia
 */
public class ReporteAsistenciaDTO {
    private final String nombreCompleto;
    private final String username;
    private final LocalDate fecha;
    private final String estado;
    private final LocalTime horaEntrada;
    private final LocalTime horaSalida;
    private final String turno;
    
    public ReporteAsistenciaDTO(String nombreCompleto, String username, LocalDate fecha, 
                               String estado, LocalTime horaEntrada, LocalTime horaSalida, String turno) {
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.fecha = fecha;
        this.estado = estado;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.turno = turno;
    }
    
    // Getters
    public String getNombreCompleto() { return nombreCompleto; }
    public String getUsername() { return username; }
    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public LocalTime getHoraEntrada() { return horaEntrada; }
    public LocalTime getHoraSalida() { return horaSalida; }
    public String getTurno() { return turno; }
}