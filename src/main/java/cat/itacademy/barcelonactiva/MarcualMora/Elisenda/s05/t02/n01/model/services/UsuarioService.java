package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.PartidaDTO;
import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto.UsuarioDTO;

public interface UsuarioService {

		public Integer addUsuario(UsuarioDTO usuarioDTO);
		
		public Integer addPartida(PartidaDTO partidaDTO);
		
		public List<UsuarioDTO> getAllUsuario();
		
		public List<PartidaDTO> getAllPartidasUsuario(Integer usuarioID);
		
		public UsuarioDTO getOneUsuario(Integer usuarioID);
		
		public UsuarioDTO updateUsuario (Integer usuarioID, UsuarioDTO usuarioDTO);
		
		public int deleteUsuario (Integer usuarioID);
		
		public int deletePartidasUsuario (Integer usuarioID);
		
}
