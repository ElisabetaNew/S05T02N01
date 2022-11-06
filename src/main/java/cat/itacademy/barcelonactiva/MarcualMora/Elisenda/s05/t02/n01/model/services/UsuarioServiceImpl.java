package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Partida;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Usuario;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.PartidaDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.UsuarioDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.repository.PartidaRepository;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	PartidaRepository partidaRepository;

	@Override
	public Integer addUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = this.mapDTOtoEntityUsuario(usuarioDTO);
		usuario = usuarioRepository.save(usuario);
		return usuario.getUsuarioID();
	}

	@Override
	public Integer addPartida(PartidaDTO partidaDTO) {
		Partida partida = this.mapDTOtoEntityPartida(partidaDTO);
		partida = partidaRepository.save(partida);
		return partida.getPartidaID();
	}

	@Override
	public List<UsuarioDTO> getAllUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return this.getDTOByUsuarios(usuarios);
	}

	@Override
	public List<PartidaDTO> getAllPartidasUsuario(Integer usuarioID) {
		List<Partida> partidas = partidaRepository.findAll();
		return this.getDTOByPartidas(partidas);
	}

	@Override
	public UsuarioDTO getOneUsuario(Integer usuarioID) {
		UsuarioDTO dto = null;
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioID); // Optional = puede existir o no
		if (usuario.isPresent()) {
			dto = this.mapEntitytoDTOUsuario(usuario.get());
		} else {
			dto = new UsuarioDTO(); // para no pasarselo vacia
		}
		return dto;
	}
	
//	@Override
//	public UsuarioDTO getUsuarioxNombre(String nombreUsuario) {
//		UsuarioDTO dto = null;
//		Optional<Usuario> usuario = usuarioRepository.findByName(nombreUsuario); // Optional = puede existir o no
//		if (usuario.isPresent()) {
//			dto = this.mapEntitytoDTOUsuario(usuario.get());
//		} else {
//			dto = new UsuarioDTO(); // para no pasarselo vacia
//		}
//		return dto;
//	}

	@Override
	public boolean getOneUsuario(String nombreUsuario) {
		List<UsuarioDTO> usuarios = this.getAllUsuario();
		for (UsuarioDTO usuarioDTO : usuarios) {
			if (usuarioDTO.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public UsuarioDTO updateUsuario(Integer usuarioID, UsuarioDTO usuarioDTO) {
		UsuarioDTO usuarioBuscado = this.getOneUsuario(usuarioID);
		Usuario usuario = this.mapDTOtoEntityUsuario(usuarioDTO);
		usuario = usuarioRepository.save(usuario);
		usuarioBuscado = this.mapEntitytoDTOUsuario(usuario);
		return usuarioBuscado;
	}

	@Override
	public int deleteUsuario(Integer usuarioID) {
		try {
			usuarioRepository.deleteById(usuarioID);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public int deletePartidasUsuario(Integer usuarioID) {
		try {
			Optional<Usuario> usuario = usuarioRepository.findById(usuarioID);
			if (usuario.isPresent()) {
				List<Partida> partidas = usuario.get().getPartidas(usuarioID);
				partidas.clear();
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	// metodos mapear datos: EntitytoDTO y de DTOtoEntity

	// convierte en UsuarioDTO / PartidaDTO los datos que llegan de la BBDD (Usuario
	// / Partida)
	private UsuarioDTO mapEntitytoDTOUsuario(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setUsuarioID(usuario.getUsuarioID());
		dto.setNombreUsuario(usuario.getNombreUsuario());
		dto.setFechaRegistro(usuario.getFechaRegistro());
		dto.setPorcentageExito(usuario.getPorcentageExito());
		dto.setPartidas(usuario.getPartidas(usuario.getUsuarioID()));

		return dto;
	}

	private PartidaDTO mapEntitytoDTOPartida(Partida partida) {
		PartidaDTO dto = new PartidaDTO();
		dto.setDado1(partida.getDado1());
		dto.setDado2(partida.getDado2());
		dto.setPartidaID(partida.getPartidaID());
		dto.setResultado();
		dto.setUsuarioID(dto.getUsuarioID());

		return dto;
	}

	// convierte en Usuario / Partida los datos que llegan de la vista (UsuarioDTO)
	// para poderlos volcar a las BBDD
	private Usuario mapDTOtoEntityUsuario(UsuarioDTO udto) {

		Usuario usuario = new Usuario();
		usuario.setUsuarioID(udto.getUsuarioID());
		if (udto.getNombreUsuario().equals("") || udto.getNombreUsuario() == null) {
			usuario.setNombreUsuario("Anonimo");
		} else {
			usuario.setNombreUsuario(udto.getNombreUsuario());
		}
		if (udto.getFechaRegistro() == null) {
			usuario.setFechaRegistro(fechaRegistro());
		} else {
			usuario.setFechaRegistro(udto.getFechaRegistro());
		}
		usuario.setPartidas(udto.getPartidas());
		usuario.setPorcentageExito(udto.getPorcentageExito());
		// usuario.addPartida(dto.)

		return usuario;
	}

	private Partida mapDTOtoEntityPartida(PartidaDTO pdto) {
		Partida partida = new Partida();
		partida.setDado1(pdto.getDado1());
		partida.setDado2(pdto.getDado2());
		partida.setPartidaID(pdto.getPartidaID());
		partida.setResultado(pdto.isResultado());
		partida.setUsuarioID(pdto.getUsuarioID());

		return partida;
	}

	// devuelve lista de UsuariosDTO/ PartidaDTO a partir de lista de
	// usuarios/partidas (entity)
	private List<UsuarioDTO> getDTOByUsuarios(List<Usuario> usuarios) {
		List<UsuarioDTO> usuariosdto = null;
		if (usuarios != null) {
			usuariosdto = new ArrayList<UsuarioDTO>();

			for (Usuario u : usuarios) {
				UsuarioDTO dto = this.mapEntitytoDTOUsuario(u);

				usuariosdto.add(dto);
			}
		}
		return usuariosdto;
	}

	private List<PartidaDTO> getDTOByPartidas(List<Partida> partidas) {
		List<PartidaDTO> partidasdto = null;
		if (partidas != null) {
			partidasdto = new ArrayList<PartidaDTO>();

			for (Partida p : partidas) {
				PartidaDTO dto = this.mapEntitytoDTOPartida(p);

				partidasdto.add(dto);
			}
		}
		return partidasdto;
	}

	// metodos extras de calculos

	// calcula porcentage de partidas ganadas sobre e ltotal de la lista de usuarios
	public float porcentageExito() {

		float porcentageTotal = 0;
		float suma = 0;
		List<UsuarioDTO> usuarios = this.getAllUsuario();
		if (usuarios.size() == 0) {
			porcentageTotal = 0;
		} else {
			for (int i = 0; i < usuarios.size(); i++) {
				suma = +usuarios.get(i).getPorcentageExito();
				++i;
			}
			porcentageTotal = (suma / usuarios.size());
		}
		return porcentageTotal;
	}

	// da valor aleatorio entre el 0-6 al dado
	public int tirada() {
		int dado = (int) (Math.random() * 7);
		return dado;
	}

	// da valor de fecha de alta del usuario
	public LocalDate fechaRegistro() {
		LocalDate fecha = LocalDate.now();
		return fecha;
	}

}
