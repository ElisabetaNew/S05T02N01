package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "usuarios")
public class Usuario{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer usuarioID;

	@Column(name = "nombre_usuario", length = 150, nullable = false)
	private String nombreUsuario;

	@Column(name = "fecha_registro", updatable = true, nullable = true)
	// @Temporal(TemporalType.DATE)
	private LocalDate fechaRegistro;

	@Column(name = "porcetage_Exito", length = 10, nullable = true)
	private float porcentageExito;

	@OneToMany(mappedBy = "usuarioID", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Partida> partidas;

	// Constructores
	public Usuario() {

	}

	public Usuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	

	public Usuario(Integer usuarioID, String nombreUsuario, LocalDate fechaRegistro) {
		super();
		this.usuarioID = usuarioID;
		this.nombreUsuario = nombreUsuario;
		this.fechaRegistro = fechaRegistro;
	}


	public Usuario(Integer usuarioID, String nombreUsuario, LocalDate fechaRegistro, float porcentageExito) {
		super();
		this.usuarioID = usuarioID;
		this.nombreUsuario = nombreUsuario;
		this.fechaRegistro = fechaRegistro;
		this.porcentageExito = porcentageExito;
		this.partidas = new ArrayList<Partida>();
	}

	// getters y setters

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

	// retornar las partidas de ese usuario en concreto
	public List<Partida> getPartidas() {
		List<Partida> lista = new ArrayList<Partida>();
		for (Partida partida : partidas) {
			if (partida.getUsuarioID().equals(this.usuarioID)) {
				lista.add(partida);
			}
		}
		return lista;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	public Usuario addPartida(Partida partida) {
		this.partidas.add(partida);
		partida.setUsuarioID(this.usuarioID);
		return this;
	}

	public float getPorcentageExito() {
		return porcentageExito;
	}

//	public void setPorcentageExito(float porcentageExito) {
//		this.porcentageExito = porcentageExito;
//	}

	@Override
	public String toString() {
		return "Usuario [usuarioID=" + usuarioID + ", nombreUsuario=" + nombreUsuario + ", fechaRegistro="
				+ fechaRegistro + ", porcentageExito=" + porcentageExito + ", partidas=" + partidas + "]";
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
