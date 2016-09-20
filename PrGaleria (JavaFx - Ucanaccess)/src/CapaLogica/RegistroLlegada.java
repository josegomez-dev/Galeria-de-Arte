package CapaLogica;

import Multi.MultiGaleria;
import Multi.MultiPintura;

public class RegistroLlegada {

	private int id;
	private String fecha;
	private String condicionLlegada;
	private String condicionActual;
	private double costoAdquirida;
	private int numPintura;
	private String cedulaGaleria;
	private String exposicion;

	private Pintura pintura;
	private Galeria galeria;

	public RegistroLlegada(int pid, String pfecha, String pcondicionLlegada, String pcondicionActual,
							String pexposicion, double pcostoAdquirida, int pnumPintura, String pcedGaleria) {
		setId(pid);
		setFecha(pfecha);
		setCondicionLlegada(pcondicionLlegada);
		setCondicionActual(pcondicionActual);
		setExposicion(pexposicion);
		setCostoAdquirida(pcostoAdquirida);
		setNumPintura(pnumPintura);
		setCedulaGaleria(pcedGaleria);
	}

	public int getId() {
		return id;
	}

	private void setId(int pid) {
		this.id = pid;
	}

	public String getFecha() {
		return fecha;
	}

	private void setFecha(String pfecha) {
		this.fecha = pfecha;
	}

	public String getCondicionLlegada() {
		return condicionLlegada;
	}

	private void setCondicionLlegada(String pcondicionLlegada) {
		this.condicionLlegada = pcondicionLlegada;
	}

	public String getCondicionActual() {
		return condicionActual;
	}

	private void setCondicionActual(String pcondicionActual) {
		this.condicionActual = pcondicionActual;
	}

	public double getCostoAdquirida() {
		return costoAdquirida;
	}

	private void setCostoAdquirida(double pcostoAdquirida) {
		this.costoAdquirida = pcostoAdquirida;
	}

	public int getNumPintura() {
		return numPintura;
	}

	private void setNumPintura(int pnumPintura) {
		this.numPintura = pnumPintura;
	}

	public String getCedulaGaleria() {
		return cedulaGaleria;
	}

	private void setCedulaGaleria(String pcedulaGaleria) {
		this.cedulaGaleria = pcedulaGaleria;
	}

	public String getExposicion() {
		return exposicion;
	}

	public void setExposicion(String exposicion) {
		this.exposicion = exposicion;
	}

	public Pintura getPintura() throws Exception{
		if (pintura == null) {
			setPintura((new MultiPintura()).consultarXNumero(this.getNumPintura()));
		}
		return pintura;
	}

	private void setPintura(Pintura ppintura){
		pintura = ppintura;
	}

	public Galeria getGaleria() throws Exception{
		if (galeria == null) {
			setGaleria((new MultiGaleria()).consultarXCedula(this.getCedulaGaleria()));
		}
		return galeria;
	}

	public void setGaleria(Galeria pgaleria){
		galeria = pgaleria;
	}

	@Override
	public String toString(){
		String 	estado  = "\nId:         " + this.getId();
				estado += "\nFecha:      " + this.getFecha();
				estado += "\nCondición:  " + this.getCondicionLlegada();
				estado += "\nCosto:      " + this.getCostoAdquirida();
		return estado;
	}
}