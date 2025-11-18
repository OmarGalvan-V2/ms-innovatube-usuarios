package com.capysoft.innovatube.entities;


import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(unique = true)
	private String usuario;
	
	private String nombre;
	
	@Column(unique = true)
	private String correo;
	
	@Column(name="apellido_materno")
	private String apellidoMaterno;
	
	@Column(name="apellido_paterno")
	private String apellidoPaterno;
	
	private String password;
	
	@Column(name="fecha_nacimiento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;

}
