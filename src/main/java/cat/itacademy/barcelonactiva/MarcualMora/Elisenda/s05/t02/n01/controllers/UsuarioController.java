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

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.UsuarioDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services.UsuarioServiceImpl;

@Controller
@RequestMapping("/players")
public class UsuarioController {

	
	@GetMapping({"","/"})
	public String mostrarprueba(Model model) {
		model.addAttribute("nombre","Elisenda Marcual");
		return"homePrueba";
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
    public ModelAndView saveUsuario(@ModelAttribute("Usuario") UsuarioDTO usuarioDTO){
	usuarioService.addUsuario(usuarioDTO);
	return new ModelAndView ("redirect:/players/getAll");
    }

	
	// http://localhost:9000/players/getAll ---- recuperar todos los usuarios
		@GetMapping("/getAll")
		public ModelAndView listaAllUsuarios(ModelAndView modelAndView) {
			List<UsuarioDTO> usuarios = usuarioService.getAllUsuario();
			modelAndView.addObject("Lista_Usurios", usuarios);
			modelAndView.setViewName("aplicacion/getAll");
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

		// http://localhost:9000/players/update ---- actualizar o modificar usuario
		@GetMapping("/update/{id}")
		public ModelAndView updateUsuario(ModelAndView modelAndView, @PathVariable("id") Integer id) {
			UsuarioDTO usuarioDTO = usuarioService.getOneUsuario(id);
			modelAndView.addObject("Usuario", usuarioDTO);
			modelAndView.setViewName("aplicacion/update");
			return modelAndView;
		}

		// http://localhost:9000/usuario/delete/{id} ---- borrar usuario por id
		@GetMapping("/delete/{id}")
		public ModelAndView deleteUsuario(@PathVariable("id") Integer id) {
			usuarioService.deleteUsuario(id);
			return new ModelAndView ("redirect:/players/getAll");
		}
		
		
	}

