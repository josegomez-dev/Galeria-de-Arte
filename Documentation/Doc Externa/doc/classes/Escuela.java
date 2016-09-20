package CapaLogica;

public class Escuela {

	private String cedulaJuridica;
	private String nombre;
	private String paisFundada;
	private String fechaFundada;
	private String descCaracteristicas;

	public Escuela(String pcedulaJuridica, String pnombre, String ppaisFundada, String pfechaFundada,
					String pdescCaracteristicas) {
		setCedulaJuridica(pcedulaJuridica);
		setNombre(pnombre);
		setPaisFundada(ppaisFundada);
		setFechaFundada(pfechaFundada);
		setDescCaracteristicas(pdescCaracteristicas);
	}

	public String getCedulaJuridica() {
		return cedulaJuridica;
	}

	public void setCedulaJuridica(String pcedulaJuridica) {
		this.cedulaJuridica = pcedulaJuridica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String pnombre) {
		this.nombre = pnombre;
	}

	public String getPaisFundada() {
		return paisFundada;
	}

	public void setPaisFundada(String ppaisFundada) {
		this.paisFundada = ppaisFundada;
	}

	public String getFechaFundada() {
		return fechaFundada;
	}

	public void setFechaFundada(String pfechaFundada) {
		this.fechaFundada = pfechaFundada;
	}

	public String getDescCaracteristicas() {
		return descCaracteristicas;
	}

	public void setDescCaracteristicas(String pdescCaracteristicas) {
		this.descCaracteristicas = pdescCaracteristicas;
	}

	@Override
	public String toString(){
		String 	estado  = "\nCédula jurídica:  " + this.getCedulaJuridica();
				estado += "\nNombre:           " + this.getNombre();
				estado += "\nPaís fundada:     " + this.getPaisFundada();
				estado += "\nFecha fundada:    " + this.getFechaFundada();
				estado += "\nDescripción:      " + this.getDescCaracteristicas();
		return estado;
	}
}