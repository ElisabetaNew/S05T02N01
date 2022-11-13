package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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
	public Usuario addUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = this.mapDTOtoEntityUsuario(usuarioDTO);
		return usuarioRepository.save(usuario);
	}

	@Override
	public Partida addPartida(Integer usuarioID, PartidaDTO partidaDTO) {
		partidaDTO.setUsuarioID(usuarioID);
		int dado1 = tirada();
		partidaDTO.setDado1(dado1);
		int dado2 = tirada();
		partidaDTO.setDado2(dado2);
		partidaDTO.setResultado(resultado(dado1 + dado2));
		Partida partida = this.mapDTOtoEntityPartida(partidaDTO);
		return partidaRepository.save(partida);
	}

	@Override
	public List<UsuarioDTO> getAllUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return this.getDTOByUsuarios(usuarios);
	}

	@Override
	public List<Partida> getAllPartidas() {
		List<Partida> partidas = partidaRepository.findAll();
		return partidas;
	}

	@Override
	public List<PartidaDTO> getAllPartidasUsuario(Integer usuarioID) {
		List<Partida> partidas = partidaRepository.findAll();
		List<Partida> partidasjugador = new ArrayList<Partida>();
		for (Partida partida : partidas) {
			if (partida.getUsuarioID() == usuarioID) {
				partidasjugador.add(partida);
			}
		}
		return this.getDTOByPartidas(partidasjugador);
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

	@Override
	public Integer getJugador(String nombreUsuario) {
		Integer id = null;
		List<Usuario> usuarios = usuarioRepository.findAll();
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
				id = usuarios.get(i).getUsuarioID();
			}
		}
		return id;
	}

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
		usuarioBuscado.setNombreUsuario(usuarioDTO.getNombreUsuario());
		Usuario usuario = this.mapDTOtoEntityUsuario(usuarioBuscado);
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
			List<Partida> partidas = this.getAllPartidas();
			
//			for (Partida partida : partidas) {
//				if (partida.getUsuarioID() == usuarioID) {
//					partidaRepository.delete(partida);					
//				}
//			}
			
			
			Iterator<Partida> it = partidas.iterator();
			int i = 0;
			while (it.hasNext() && i < partidas.size()) {
				if (partidas.get(i).getUsuarioID() == usuarioID) {
					partidaRepository.delete(partidas.get(i));					
				}
				i++;
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
		dto.setPartidas(usuario.getPartidas());
		dto.setPorcentageExito();
		

		return dto;
	}

	private PartidaDTO mapEntitytoDTOPartida(Partida partida) {
		PartidaDTO dto = new PartidaDTO();
		dto.setDado1(partida.getDado1());
		dto.setDado2(partida.getDado2());
		dto.setPartidaID(partida.getPartidaID());
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
		usuario.setPorcentageExito();


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
		int dado = (int) (Math.random() * 6);
		return dado;
	}

	// da valor de fecha de alta del usuario
	public LocalDate fechaRegistro() {
		LocalDate fecha = LocalDate.now();
		return fecha;
	}

	// valora si partida es ganada o perdida
	public boolean resultado(int total) {
		if (total == 7) {
			return true;
		}
		return false;
	}
	
	//calcular porcentage de acierto de un jugador
	public float porcentageExitoJugador(List<Partida>partidas) {
		int ganadas = 0;
		for (Partida partida : partidas) {
			if (partida.isResultado() == true) {
				++ganadas;
			}
		}
		return ((float) (ganadas * 100) / partidas.size());
	}

		
}
