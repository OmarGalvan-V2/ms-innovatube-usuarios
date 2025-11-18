package com.capysoft.innovatube.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private String mensaje;
    private List<String> errores;
    private LocalDateTime timestamp;
    private int status;
    
    public ErrorResponse(String mensaje, List<String> errores, int status) {
        this.mensaje = mensaje;
        this.errores = errores;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}