package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.dto;

public class PartidaDTO {

	private Integer partidaID;
	private int dado1;
	private int dado2;
	private boolean resultado;
	private Integer usuarioID;

	// constructores
	public PartidaDTO(Integer partidaID, int dado1, int dado2, boolean resultado, Integer usuarioID) {
		super();
		this.partidaID = partidaID;
		this.dado1 = dado1;
		this.dado2 = dado2;
		this.resultado = resultado;
		this.usuarioID = usuarioID;
	}

	public PartidaDTO(int dado1, int dado2, Integer usuarioID) {
		this.dado1 = dado1;
		this.dado2 = dado2;
		this.usuarioID = usuarioID;
	}

	public PartidaDTO() {

	}

	// getters y setters
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

	@Override
	public String toString() {
		return "PartidaDTO [partidaID=" + partidaID + ", dado1=" + dado1 + ", dado2=" + dado2 + ", resultado="
				+ resultado + ", usuarioID=" + usuarioID + "]";
	}

}
