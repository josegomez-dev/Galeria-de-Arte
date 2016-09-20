package CapaLogica;

import Multi.MultiPintor;

public class Pintura {

	private int numero;
	private String nombre;
	private double[] dimensiones;
	private String fechaCreada;
	private int[] duracionCreada;
	private String tecnica;
	private String famosa;
	private String historia;
	private String cedulaGaleria;
	private String cedulaPintor;

	private Pintor pintor;

	public Pintura(int pnumero, String pnombre, double[] pdimensiones, String pfechaCreada, int[] pduracionCreada,
					String ptecnica, String pfamosa, String phistoria, String pcedulaPintor, String pcedulaGaleria) {
		setNumero(pnumero);
		setNombre(pnombre);
		setDimensiones(pdimensiones);
		setFechaCreada(pfechaCreada);
		setDuracionCreada(pduracionCreada);
		setTecnica(ptecnica);
		setFamosa(pfamosa);
		setHistoria(phistoria);
		setCedulaPintor(pcedulaPintor);
		setCedulaGaleria(pcedulaGaleria);
		pintor = null;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int pnumero) {
		this.numero = pnumero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String pnombre) {
		this.nombre = pnombre;
	}

	public double[] getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(double[] pdimensiones) {
		this.dimensiones = pdimensiones;
	}

	public String getFechaCreada() {
		return fechaCreada;
	}

	public void setFechaCreada(String pfechaCreada) {
		this.fechaCreada = pfechaCreada;
	}

	public int[] getDuracionCreada() {
		return duracionCreada;
	}

	public void setDuracionCreada(int[] pduracionCreada) {
		this.duracionCreada = pduracionCreada;
	}

	public String getTecnica() {
		return tecnica;
	}

	public void setTecnica(String ptecnica) {
		this.tecnica = ptecnica;
	}

	public String getFamosa() {
		return famosa;
	}

	public void setFamosa(String pfamosa) {
		this.famosa = pfamosa;
	}

	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String phistoria) {
		this.historia = phistoria;
	}

	public String getCedulaPintor() {
		return cedulaPintor;
	}

	public void setCedulaPintor(String pcedulaPintor) {
		this.cedulaPintor = pcedulaPintor;
	}

	public String getCedulaGaleria() {
		return cedulaGaleria;
	}

	public void setCedulaGaleria(String pcedulaGaleria) {
		this.cedulaGaleria = pcedulaGaleria;
	}

	public Pintor getPintor() throws Exception{
		if (pintor == null) {
			setPintor((new MultiPintor()).consultarXCedula(this.getCedulaPintor()));
		}
		return pintor;
	}

	public void setPintor(Pintor ppintor){
		pintor = ppintor;
	}

	@Override
	public String toString(){
		String 	estado  = "\nNúmero:                   " + this.getNumero();
				estado += "\nNombre:                   " + this.getNombre();
				estado += "\nDimensiones:";
				estado += "\n   - Alto:  " + this.getDimensiones()[0];
				estado += "\n   - Ancho: " + this.getDimensiones()[1];
				estado += "\nFecha de creación:        " + this.getFechaCreada();
				estado += "\nDuración de la creación:  " + this.getDuracionCreada();
				estado += "\nTécnica:                  " + this.getTecnica();
				estado += "\nFamosa:                   " + this.getFamosa();
				estado += "\nHistoria:                 " + this.getHistoria();
		return estado;
	}
}
