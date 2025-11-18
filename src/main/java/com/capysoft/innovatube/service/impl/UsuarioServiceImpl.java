package com.capysoft.innovatube.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capysoft.innovatube.dto.UsuarioDto;
import com.capysoft.innovatube.entities.Usuario;
import com.capysoft.innovatube.repository.UsuarioRepository;
import com.capysoft.innovatube.services.UsuarioService;

@Service
public class UsuarioServiceImpl  implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	   
	@Override
	@Transactional
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

	@Override
	@Transactional
	public Optional<Usuario> findById(Long idUsuario) {
		return usuarioRepository.findById(idUsuario);
	}

	@Override
	@Transactional
	public Usuario insertarUsuario(Usuario objetoUsuario) {
		if (usuarioRepository.existsByUsuario(objetoUsuario.getUsuario())) {
			throw new RuntimeException("El nombre de usuario ya está en uso");
		}
		
		if (usuarioRepository.existsByCorreo(objetoUsuario.getCorreo())) {
			throw new RuntimeException("El correo electrónico ya está registrado");
		}
		
		return usuarioRepository.save(objetoUsuario);
	}
	
	public Usuario convertirDtoAEntidad(UsuarioDto dto) {
	    Usuario usuario = new Usuario();
	    usuario.setIdUsuario(dto.getIdUsuario());
	    usuario.setUsuario(dto.getUsuario());
	    usuario.setNombre(dto.getNombre());
	    usuario.setCorreo(dto.getCorreo());
	    usuario.setApellidoMaterno(dto.getApellidoMaterno());
	    usuario.setApellidoPaterno(dto.getApellidoPaterno());
	    usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
	    usuario.setFechaNacimiento(dto.getFechaNacimiento());
	    return usuario;
	}
	
	public Usuario findByEmailOrUsername(String correoOUsuario) {
		Usuario usuario = usuarioRepository.findByCorreo(correoOUsuario);
		if (usuario == null) {
			usuario = usuarioRepository.findByUsuario(correoOUsuario);
		}
		return usuario;
	}

	public Usuario findByUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}
	
	public boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
