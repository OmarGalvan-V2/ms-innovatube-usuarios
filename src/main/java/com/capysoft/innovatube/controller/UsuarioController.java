package com.capysoft.innovatube.controller;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capysoft.innovatube.dto.UsuarioDto;
import com.capysoft.innovatube.dto.LoginRequest;
import com.capysoft.innovatube.entities.Usuario;
import com.capysoft.innovatube.service.impl.UsuarioServiceImpl;
import com.capysoft.innovatube.service.RecaptchaService;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Map;
import java.util.HashMap;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/Usuarios")
public class UsuarioController {

	Logger log = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private RecaptchaService recaptchaService;
	
	@Value("${configuracion.texto}")
	private String texto;

	@Autowired
	private Environment env; 


	@GetMapping("/texto-config")
	public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String puerto) {
		Map<String, String> response = new HashMap<>();
		response.put("texto", texto);
		response.put("puerto", puerto);
		log.info("Texto: " + texto + " Puerto: " + puerto);
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
			response.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			response.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		return ResponseEntity.ok().body(response);
	}


	@GetMapping("/getAll")
	public List<Usuario> obtenerUsuarios(){
		return usuarioService.findAll();
	}
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long idUsuario) {
		Optional<Usuario> obtenerUsuario = usuarioService.findById(idUsuario); 
		if(obtenerUsuario.isPresent()) {
			return ResponseEntity.ok(obtenerUsuario.orElseThrow());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/crearUsuario")
	public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDto objetoUsuario){
		if (!recaptchaService.validateToken(objetoUsuario.getRecaptchaToken())) {
			throw new RuntimeException("Token reCAPTCHA inválido");
		}
	    Usuario usuario = usuarioService.convertirDtoAEntidad(objetoUsuario);
	    Usuario guardado = usuarioService.insertarUsuario(usuario);
		return ResponseEntity.ok(guardado);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Usuario usuario = usuarioService.findByEmailOrUsername(loginRequest.getCorreoOUsuario());
		if (usuario != null && usuarioService.checkPassword(loginRequest.getPassword(), usuario.getPassword())) {

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			Map<String, Object> jwtRequest = new HashMap<>();
			jwtRequest.put("usuario", usuario.getUsuario());
			jwtRequest.put("idUsuario", usuario.getIdUsuario());
			jwtRequest.put("correo", usuario.getCorreo());
			
			HttpEntity<Map<String, Object>> entity = new HttpEntity<>(jwtRequest, headers);
			ResponseEntity<Map> response = restTemplate.postForEntity(
				"http://localhost:8092/jwt/generar", entity, Map.class);
			
			return ResponseEntity.ok(response.getBody());
		}
		return ResponseEntity.badRequest().body("Credenciales inválidas");
	}	
	
}
