package com.capysoft.innovatube.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correoOUsuario;
    private String password;
}