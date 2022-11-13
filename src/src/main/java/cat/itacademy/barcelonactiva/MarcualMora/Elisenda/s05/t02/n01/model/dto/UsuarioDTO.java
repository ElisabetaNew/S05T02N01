package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Partida;

public class UsuarioDTO {

	private Integer usuarioID;
	private String nombreUsuario;
	private LocalDate fechaRegistro;
	private List<Partida>partidas = new ArrayList<Partida>();
	private float porcentageExito;


	// porcentageExito = (float)((partidas ganadas*100)/total partidas)
	// Syso(porcentageExito + "%")

	//constructores
	public UsuarioDTO(Integer usuarioID, String nombreUsuario, LocalDate fechaRegistro, List<Partida> partidas,
			float porcentageExito) {
		super();
		this.usuarioID = usuarioID;
		this.nombreUsuario = nombreUsuario;
		this.fechaRegistro = fechaRegistro;
		this.partidas = new ArrayList<Partida>();
		this.porcentageExito = porcentageExito;
	}
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Integer usuarioID, String nombreUsuario) {
		this.usuarioID = usuarioID;
		this.nombreUsuario = nombreUsuario;
	}
	
	public UsuarioDTO(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	
	//getters y setters
	public Integer getUsuarioID() {
		return usuarioID;
	}
 
	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}


	//retornar las partidas de ese usuario en concreto
	public List<Partida> getPartidas(Integer usuarioID) {
		List<Partida> lista = new ArrayList<Partida>();
		for (Partida partida : partidas) {
			if (partida.getUsuarioID().equals(this.usuarioID)) {
				lista.add(partida);
			}
		}
		return lista;
	}
	
	public float getPorcentageExito() {
		return porcentageExito;
	}

	
//	public void setPorcentageExito(float porcentageExito) {
//		this.porcentageExito = porcentageExito;
//	}

	//para obtener el percentatge contar partidas ganadas y dividirlas por total partidas
	public void setPorcentageExito() {
		if(this.partidas.size()==0) {
			this.porcentageExito = 0;
		} else {
		List<Partida> lista = new ArrayList<Partida>();
		for (Partida partida : partidas) {
			if (partida.isResultado()== true) {
				lista.add(partida);
			}
		}
		this.partidas.size();
		this.porcentageExito = (100*lista.size())/this.partidas.size();
	}
	}
	
	
	

}