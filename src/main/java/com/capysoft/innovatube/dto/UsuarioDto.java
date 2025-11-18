package com.capysoft.innovatube.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDto {
	private Long idUsuario;
	
	@NotBlank(message = "El nombre de usuario es requerido")
	@Size(min = 3, max = 30, message = "El usuario debe tener entre 3 y 30 caracteres")
	@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El usuario solo puede contener letras, números y guiones bajos")
	private String usuario;
	
	@NotBlank(message = "El nombre es requerido")
	@Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
	private String nombre;
	
	@NotBlank(message = "El correo es requerido")
	@Email(message = "El formato del correo no es válido")
	private String correo;
	
	@NotBlank(message = "El apellido materno es requerido")
	@Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
	private String apellidoMaterno;
	
	@NotBlank(message = "El apellido paterno es requerido")
	@Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
	private String apellidoPaterno;
	
	@NotBlank(message = "La contraseña es requerida")
	@Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
	private String password;
	
	@NotNull(message = "La fecha de nacimiento es requerida")
	@Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;
	
	@NotBlank(message = "El token reCAPTCHA es requerido")
	private String recaptchaToken;
}
