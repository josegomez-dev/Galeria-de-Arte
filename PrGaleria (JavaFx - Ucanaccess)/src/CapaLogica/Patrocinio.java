package CapaLogica;

public class Patrocinio {

	private String codPatrocinio;
	private static long cantPatrocinios = 0;
	private String fechaInicio;
	private String fechaFin;
	private String cedulaPintor;
	private String cedulaPatrocinador;
	
	private Pintor artista;
	private Patrocinador patrocinador;
	
	public Patrocinio(String pfechaInicio, String pfechaFin, String pcedulaPintor, String pcedulaPatrocinador) {
		setCodPatrocinio();
		setFechaInicio(pfechaInicio);
		setFechaFin(pfechaFin);
		setCedulaPintor(pcedulaPintor);
		setCedulaPatrocinador(pcedulaPatrocinador);
	}
	
	public String getCodPatrocinio() {
		return codPatrocinio;
	}
	private void setCodPatrocinio() {
		this.codPatrocinio = "" + ++cantPatrocinios;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getCedulaPintor() {
		return cedulaPintor;
	}
	private void setCedulaPintor(String cedulaPintor) {
		this.cedulaPintor = cedulaPintor;
	}
	public String getCedulaPatrocinador() {
		return cedulaPatrocinador;
	}
	private void setCedulaPatrocinador(String cedulaPatrocinador) {
		this.cedulaPatrocinador = cedulaPatrocinador;
	}
	
	
	public Pintor getArtista() {
		return artista;
	}
	public void setArtista(Pintor artista) {
		this.artista = artista;
	}
	public Patrocinador getPatrocinador() {
		return patrocinador;
	}
	public void setPatrocinador(Patrocinador patrocinador) {
		this.patrocinador = patrocinador;
	}
	
	public String toString() {
		String estado = "Fecha de Inicio:      " + this.getFechaInicio() + "\n";
			   estado += "Fecha Final:         " + this.getFechaFin() + "\n";
			   estado += "Cedula Artista:      " + this.getCedulaPintor() + "\n";
			   estado += "Cedula Patrocinador: " + this.getCedulaPatrocinador() + "\n";
		return estado;
	}
	
}
