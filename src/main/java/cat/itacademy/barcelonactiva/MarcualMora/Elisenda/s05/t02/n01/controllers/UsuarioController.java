package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.PartidaDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.UsuarioDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services.UsuarioServiceImpl;

@Controller
@RequestMapping("/players")
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

	// http://localhost:9000/players/add ---- crear jugador
	@GetMapping("/add")
	public ModelAndView addUsuario() {
		UsuarioDTO usuario = new UsuarioDTO();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Usuario", usuario);
		modelAndView.setViewName("aplicacion/add");
		return modelAndView;
	}

	@PostMapping("/guardar")
	public ModelAndView saveUsuario(@ModelAttribute("Usuario") UsuarioDTO usuarioDTO) {
		usuarioService.addUsuario(usuarioDTO);
		return new ModelAndView("redirect:/players/ranking");
	}

	// http://localhost:9000/players/add/{id}/games ---- jugada
	@GetMapping("/add/{id}/games")
	public ModelAndView addPartida() {
		PartidaDTO partida = new PartidaDTO();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("Partida", partida);
		modelAndView.setViewName("aplicacion/jugada");
		return modelAndView;
	}	

	@PostMapping("/guardarPartida")
	public ModelAndView savePatida(@ModelAttribute("Partida") PartidaDTO partidaDTO) {
		usuarioService.addPartida(partidaDTO);
		return new ModelAndView("redirect:/players/getAllPartidas");
	}

	// http://localhost:9000/players/getAll ---- recuperar todos los usuarios con el porcentage de aciertos
	@GetMapping("/ranking")
	public ModelAndView listaAllUsuarios(ModelAndView modelAndView) {
		List<UsuarioDTO> usuarios = usuarioService.getAllUsuario();
		modelAndView.addObject("Lista_Usuarios", usuarios);
		modelAndView.setViewName("aplicacion/getAll");
		return modelAndView;
	}

	// http://localhost:9000/players/getAllPartidasUsuario ---- recuperar todas las
	// partidas de un usuario
	@GetMapping("/getAllPatidas/{id}")
	public ModelAndView listaAllPartidas(ModelAndView modelAndView, @PathVariable("id") Integer usuarioID) {
		List<PartidaDTO> partidas = usuarioService.getAllPartidasUsuario(usuarioID);
		modelAndView.addObject("Lista_Partidas", partidas);
		modelAndView.setViewName("aplicacion/getAllPartidas");
		return modelAndView;
	}

	// http://localhost:9000/players/getOne/{id} ----- recuperar usuario por id
	@GetMapping("/getOne/{id}")
	public ModelAndView getOneUsuario(ModelAndView modelAndView, @PathVariable("id") Integer id) {
		UsuarioDTO usuario = usuarioService.getOneUsuario(id);
		modelAndView.addObject("Usuario", usuario);
		modelAndView.setViewName("aplicacion/getOne");
		return modelAndView;
	}

	// http://localhost:9000/players/ranking/loser ----- recuperar usuario con peor ranking de partidas ganadas
	@GetMapping("/ranking/loser")
	public ModelAndView getloser(ModelAndView modelAndView) {
		List<UsuarioDTO> usuarios = usuarioService.getAllUsuario();
		float porcentage = Integer.MAX_VALUE;
		UsuarioDTO usuariodto = new UsuarioDTO();
		for (int i = 0; i < usuarios.size(); i++) {
			if(porcentage > usuarios.get(i).getPorcentageExito()) {
				usuariodto = usuarios.get(i);
				porcentage = usuarios.get(i).getPorcentageExito(); 
			}
		}
		modelAndView.addObject("UsuarioLosar", usuariodto);
		modelAndView.setViewName("aplicacion/getOne");
		return modelAndView;
	}

	// http://localhost:9000/players/ranking/winner ----- recuperar usuario con mejor ranking de partidas ganadas
	@GetMapping("/ranking/winner")
	public ModelAndView getwinner(ModelAndView modelAndView) {
		List<UsuarioDTO> usuarios = usuarioService.getAllUsuario();
		float porcentage = Integer.MIN_VALUE;
		UsuarioDTO usuariodto = new UsuarioDTO();
		for (int i = 0; i < usuarios.size(); i++) {
			if(porcentage < usuarios.get(i).getPorcentageExito()) {
				usuariodto = usuarios.get(i);
				porcentage = usuarios.get(i).getPorcentageExito(); 
			}
		}
		modelAndView.addObject("UsuarioLosar", usuariodto);
		modelAndView.setViewName("aplicacion/getOne");
		return modelAndView;
	}

	// http://localhost:9000/players/update ---- actualizar o modificar usuario
	@GetMapping("/update/{id}")
	public ModelAndView updateUsuario(ModelAndView modelAndView, @PathVariable("id") Integer id) {
		UsuarioDTO usuarioDTO = usuarioService.getOneUsuario(id);
		usuarioService.updateUsuario(id, usuarioDTO);
		modelAndView.addObject("Usuario", usuarioDTO);
		modelAndView.setViewName("aplicacion/update");
		return modelAndView;
	}

	// http://localhost:9000/usuario/delete/{id} ---- borrar usuario por id
	@GetMapping("/delete/{id}")
	public ModelAndView deleteUsuario(@PathVariable("id") Integer id) {
		usuarioService.deleteUsuario(id);
		return new ModelAndView("redirect:/players/ranking");
	}

	// http://localhost:9000/usuario/deletepartidas/{id} ---- borrar todas las
	// partidas de un usuario
	@GetMapping("/deletePartidas/{id}")
	public ModelAndView deletePartidasUsuario(@PathVariable("id") Integer id) {
		usuarioService.deletePartidasUsuario(id);
		return new ModelAndView("redirect:/players/ranking");
	}

}
