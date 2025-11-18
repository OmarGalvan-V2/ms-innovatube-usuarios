package com.capysoft.innovatube.services;

import java.util.List;
import java.util.Optional;

import com.capysoft.innovatube.entities.Usuario;

public interface UsuarioService {
	List<Usuario> findAll();
	
	Optional<Usuario> findById(Long idUsuario);
	
	Usuario insertarUsuario(Usuario objetoUsuario);
}
