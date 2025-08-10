package com.josiqq.safe.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.josiqq.safe.model.MensajeInterno;

@Data
public class MensajeDTO {

    private String cuerpo;
    private String remitenteUsername;
    private String destinatarioUsername;
    private LocalDateTime fechaEnvio;

    // Un constructor para convertir fácilmente de la entidad al DTO
    public MensajeDTO(MensajeInterno mensaje) {
        this.cuerpo = mensaje.getCuerpo();
        this.remitenteUsername = mensaje.getRemitente().getUsername();
        this.destinatarioUsername = mensaje.getDestinatario().getUsername();
        this.fechaEnvio = mensaje.getFechaEnvio();
    }
}