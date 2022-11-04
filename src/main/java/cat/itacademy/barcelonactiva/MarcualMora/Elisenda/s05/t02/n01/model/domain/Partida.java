package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "partidas")
public class Partida{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_partida")
	private Integer partidaID;
	
	@Column(name = "dado1", length = 3, nullable = false)
	private int dado1;
	// int dado1 = (int) Math.ceil((Math.random()*6));
	
	@Column(name = "dado2", length = 3, nullable = false)
	private int dado2;
	
	@Column(name = "partida_ganada", length = 3, nullable = false)
	private boolean resultado;
	
	//relacion bidireccional por ser la más optima en recursos
	//@ManyToOne
	@JoinColumn(name = "usuarioID", nullable = false, updatable = false)
	private Integer usuarioID;

	
	//constructor 
	public Partida(Integer partidaID, int dado1, int dado2, boolean resultado, Integer usuarioID) {
		super();
		this.partidaID = partidaID;
		this.dado1 = dado1;
		this.dado2 = dado2;
		this.resultado = resultado;
		this.usuarioID = usuarioID;
	}
	
	public Partida(Integer partidaID, int dado1, int dado2) {
		this.partidaID = partidaID;
		this.dado1 = dado1;
		this.dado2 = dado2;
	}
	
	public Partida() {
		
	}
	
	//getters y setters

	public Integer getPartidaID() {
		return partidaID;
	}

	public void setPartidaID(Integer partidaID) {
		this.partidaID = partidaID;
	}

	public int getDado1() {
		return dado1;
	}

	public void setDado1(int dado1) {
		this.dado1 = dado1;
	}

	public int getDado2() {
		return dado2;
	}

	public void setDado2(int dado2) {
		this.dado2 = dado2;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public Integer getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(Integer usuarioID) {
		this.usuarioID = usuarioID;
	}

	


	
	
	
	
}
