package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Partida;

public class UsuarioDTO {

	private Integer usuarioID;
	private String nombreUsuario;
	private LocalDate fechaRegistro;
	private List<Partida>partidas;
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
		this.partidas = partidas;
		this.porcentageExito = porcentageExito;
	}
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Integer usuarioID, String nombreUsuario) {
		this.usuarioID = usuarioID;
		this.nombreUsuario = nombreUsuario;
	}

	
	//getters y setters
	public Integer getUsuarioID() {
		return usuarioID;
	}


	//se puede omitir porqque es un dato automatico 
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


	public void setFechaRegistro() {
		LocalDate c1 = LocalDate.now();
		//Calendar c1 = Calendar.getInstance();
		//String dia = Integer.toString(c.get(Calendar.DATE));
		//String mes = Integer.toString(c.get(Calendar.MONTH));
		//String annio = Integer.toString(c.get(Calendar.YEAR));
		this.fechaRegistro = c1;
	}

	
	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}


	//retornar las partidas de ese usuario en concreto
	public List<Partida> getPartidas(UsuarioDTO usuario) {
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