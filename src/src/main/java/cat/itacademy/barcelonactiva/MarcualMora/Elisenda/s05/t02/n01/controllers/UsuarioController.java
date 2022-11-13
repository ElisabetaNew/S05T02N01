package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Partida;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Usuario;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.PartidaDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.UsuarioDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services.UsuarioServiceImpl;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "http://localhost:9000")
public class UsuarioController {

	@GetMapping({ "", "/" })
	public ModelAndView paginaInicio() {
		UsuarioDTO usuario = new UsuarioDTO();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Usuario", usuario);
		modelAndView.setViewName("inicio");
		return modelAndView;
	}

	@Autowired
	private UsuarioServiceImpl usuarioService;

	/// http://localhost:9000/players/add ---- crear jugador
	@PostMapping("/add")
	public ResponseEntity<Usuario> addUsuario(@RequestBody UsuarioDTO usuariodto) {
		if (usuarioService.getOneUsuario(usuariodto.getNombreUsuario()) == true) {
			return new ResponseEntity("Este nombre ya esta registrado.", HttpStatus.NOT_ACCEPTABLE);
		} else {
			try {
				return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.addUsuario(usuariodto));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	// http://localhost:9000/players/add/{id}/games ---- jugada
	@PostMapping("/add/{id}/games")
	public ResponseEntity<Partida> savePatida(@PathVariable("id") Integer usuarioID,
			@RequestBody PartidaDTO partidadto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.addPartida(usuarioID, partidadto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// http://localhost:9000/players/getAll ---- recuperar todos los usuarios con el
	// porcentage de aciertos
	@GetMapping("/ranking")
	public List<UsuarioDTO> listaAllUsuarios() {
		List<UsuarioDTO> usuarios = StreamSupport.stream(usuarioService.getAllUsuario().spliterator(), false)
				.collect(Collectors.toList());
		return usuarios;
	}

	// http://localhost:9000/players/getAllPartidasUsuario ---- recuperar todas las
	// partidas de un usuario
	@GetMapping("/getAllPartidas/{id}")
	public List<PartidaDTO> listaAllPartidas(@PathVariable("id") Integer usuarioID) {
		List<PartidaDTO> partidas = StreamSupport
				.stream(usuarioService.getAllPartidasUsuario(usuarioID).spliterator(), false)
				.collect(Collectors.toList());
		return partidas;
	}

	// http://localhost:9000/players/getOne/{id} ----- recuperar usuario por id
	@GetMapping("/getOne/{id}")
	public ResponseEntity<UsuarioDTO> getOneUsuario(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(usuarioService.getOneUsuario(id));
	}

	// http://localhost:9000/players/ranking/loser ----- recuperar usuario con peor
	// ranking de partidas ganadas
	@GetMapping("/ranking/loser")
	public ResponseEntity<UsuarioDTO> getloser() {
		List<UsuarioDTO> usuarios = usuarioService.getAllUsuario();
		float porcentage = Integer.MAX_VALUE;
		UsuarioDTO usuariodto = new UsuarioDTO();
		for (int i = 0; i < usuarios.size(); i++) {
			if (porcentage > usuarios.get(i).getPorcentageExito()) {
				usuariodto = usuarios.get(i);
				porcentage = usuarios.get(i).getPorcentageExito();
			}
		}
		return ResponseEntity.ok(usuariodto);
	}

	// http://localhost:9000/players/ranking/winner ----- recuperar usuario con
	// mejor ranking de partidas ganadas
	@GetMapping("/ranking/winner")
	public ResponseEntity<UsuarioDTO> getwinner() {
		List<UsuarioDTO> usuarios = usuarioService.getAllUsuario();
		float porcentage = Integer.MIN_VALUE;
		UsuarioDTO usuariodto = new UsuarioDTO();
		for (int i = 0; i < usuarios.size(); i++) {
			if (porcentage < usuarios.get(i).getPorcentageExito()) {
				usuariodto = usuarios.get(i);
				porcentage = usuarios.get(i).getPorcentageExito();
			}
		}
		return ResponseEntity.ok(usuariodto);
	}

	// http://localhost:9000/players/ranking/porcentajeTotal----- recuperar usuario
	// con
	// mejor ranking de partidas ganadas
	@GetMapping("/ranking/porcentajeTotal")
	public float getporcentajeTotal() {
		List<Partida> partidas = usuarioService.getAllPartidas();
		int ganadas = 0;
		float porcentage = 0;
		for (Partida partidaDTO : partidas) {
			if (partidaDTO.isResultado() == true) {
				++ganadas;
			}
		}
		porcentage = (float) ((ganadas * 100) / partidas.size());

		return porcentage;
	}

	// http://localhost:9000/players/update ---- actualizar o modificar usuario
	@PutMapping("/update/{id}")
	public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable("id") Integer id,
			@RequestBody UsuarioDTO usuariodto) {
		if (usuarioService.getOneUsuario(usuariodto.getNombreUsuario()) == true) {
			return new ResponseEntity("Este nombre ya esta registrado.", HttpStatus.NOT_ACCEPTABLE);
		} else {
			UsuarioDTO usuarioNuevoDTO = usuarioService.updateUsuario(id, usuariodto);
			return ResponseEntity.ok(usuarioNuevoDTO);
		}
		
//		UsuarioDTO usuarioNuevoDTO = usuarioService.updateUsuario(id, usuariodto);
//		return ResponseEntity.ok(usuarioNuevoDTO);
	}

	// http://localhost:9000/usuario/delete/{id} ---- borrar usuario por id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteUsuario(@PathVariable("id") Integer id) {
		usuarioService.deleteUsuario(id);
		return ResponseEntity.ok().build();
	}

	// http://localhost:9000/usuario/deletepartidas/{id} ---- borrar todas las
	// partidas de un usuario
	@DeleteMapping("/delete/{id}/games")
	public ResponseEntity<HttpStatus> deletePartidasUsuario(@PathVariable("id") Integer id) {
		usuarioService.deletePartidasUsuario(id);
		return ResponseEntity.ok().build();
	}

}
