package com.capysoft.innovatube.repository;

import org.springframework.data.repository.CrudRepository;

import com.capysoft.innovatube.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	boolean existsByUsuario(String usuario);
	
	boolean existsByCorreo(String correo);
	
	boolean existsByIdUsuario(Long idUsuario);
	
	Usuario findByCorreo(String correo);
	
	Usuario findByUsuario(String usuario);

}
