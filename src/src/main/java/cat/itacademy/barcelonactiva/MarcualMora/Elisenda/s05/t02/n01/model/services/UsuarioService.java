package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Partida;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Usuario;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.PartidaDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.UsuarioDTO;

public interface UsuarioService {

		public Usuario addUsuario(UsuarioDTO usuarioDTO);
		
		public Partida addPartida(Integer usuarioID, PartidaDTO partidaDTO);
		
		public List<UsuarioDTO> getAllUsuario();
		
		public List<PartidaDTO> getAllPartidasUsuario(Integer usuarioID);
		
		public List<Partida> getAllPartidas();
		
		public UsuarioDTO getOneUsuario(Integer usuarioID);
			
		public boolean getOneUsuario(String nombreUsuario);
		
		public UsuarioDTO updateUsuario (Integer usuarioID, UsuarioDTO usuarioDTO);
		
		public int deleteUsuario (Integer usuarioID);
		
		public int deletePartidasUsuario (Integer usuarioID);

		public Integer getJugador(String nombreUsuario);
		
}
