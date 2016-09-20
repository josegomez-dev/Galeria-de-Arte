package CapaLogica;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Multi.MultiPintura;
import Multi.MultiRegistroLlegada;

public class Galeria {

	private String cedulaJuridica;
	private String nombre;
	private String direccion;
	private String telefono;
	private String fechaInauguracion;
	private String nombreEncargado;
	private double metrosCuadrados;

	private ArrayList<RegistroLlegada> adquisiciones;

	public Galeria(String pcedulaJuridica, String pnombre, String pdireccion, String ptelefono, String pfechaInauguracion,
					String pnombreEncargado, double pmetrosCuadrados) {
		setCedulaJuridica(pcedulaJuridica);
		setNombre(pnombre);
		setDireccion(pdireccion);
		setTelefono(ptelefono);
		setFechaInauguracion(pfechaInauguracion);
		setNombreEncargado(pnombreEncargado);
		setMetrosCuadrados(pmetrosCuadrados);
		adquisiciones = null;
	}

	public String getCedulaJuridica() {
		return cedulaJuridica;
	}

	private void setCedulaJuridica(String pcedulaJuridica) {
		this.cedulaJuridica = pcedulaJuridica;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String pnombre) {
		this.nombre = pnombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String pdireccion) {
		this.direccion = pdireccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String ptelefono) {
		this.telefono = ptelefono;
	}

	public String getFechaInauguracion() {
		return fechaInauguracion;
	}

	public void setFechaInauguracion(String pfechaInauguracion) {
		this.fechaInauguracion = pfechaInauguracion;
	}

	public String getNombreEncargado() {
		return nombreEncargado;
	}

	public void setNombreEncargado(String pnombreEncargado) {
		this.nombreEncargado = pnombreEncargado;
	}

	public double getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setMetrosCuadrados(double pmetrosCuadrados) {
		this.metrosCuadrados = pmetrosCuadrados;
	}

	public String registrarLlegadaPintura(int pnumPintura, String pexposicion, String pfecha, String pcondLlegada,
											String pcondActual, double pcostoAdquirida) throws SQLException, Exception{
		RegistroLlegada llegada = new MultiRegistroLlegada().crear(pnumPintura, pexposicion, pfecha, pcondLlegada,
																	pcondActual, pcostoAdquirida, this.getCedulaJuridica());
		if(llegada != null){
			return "¡Llegada registrada!";
		}else{
			return "¡El registro falló!";
		}
	}

	public RegistroLlegada consultarLlegadaPintura(int pnumPintura) throws SQLException, Exception{
		RegistroLlegada llegada = (new MultiRegistroLlegada()).consultarXPintura(pnumPintura);
		return llegada;
	}

	public ArrayList<RegistroLlegada> consultarLlegadasPinturas() throws SQLException, Exception{
		adquisiciones = (new MultiRegistroLlegada()).consultarXGaleria(this.getCedulaJuridica());
		return adquisiciones;
	}

	public ArrayList<Pintura> consultarPinturasAdquiridas() throws SQLException, Exception{
		ArrayList<Pintura> listaPinturas = new ArrayList<>();
		adquisiciones = (new MultiRegistroLlegada()).consultarXGaleria(this.getCedulaJuridica());
		for(RegistroLlegada a : adquisiciones){
			Pintura pintura = new MultiPintura().consultarXNumero(a.getNumPintura());
			listaPinturas.add(pintura);
		}
		return listaPinturas;
	}

	public ArrayList<Pintura> consultarPinturasAdquiridasXRangoFechas(Date pfecha1, Date pfecha2) throws SQLException, Exception{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		adquisiciones = (new MultiRegistroLlegada()).consultarXGaleria(this.getCedulaJuridica());

		ArrayList<Pintura> listaPinturas = new ArrayList<>();

		int i=0;
		for(RegistroLlegada a : adquisiciones){
	    	Date fechaAdquirida = formato.parse(adquisiciones.get(i).getFecha());
	    	if(fechaAdquirida.after(pfecha1) && fechaAdquirida.before(pfecha2)){
	    		Pintura pintura = new MultiPintura().consultarXNumero(a.getNumPintura());
				listaPinturas.add(pintura);
	    	}
			i++;
		}
		return listaPinturas;
	}

	public ArrayList<Pintura> consultarPinturasAdquiridasXFecha(Date pfecha) throws SQLException, Exception{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		ArrayList<Pintura> listaPinturas = new ArrayList<>();
		adquisiciones = (new MultiRegistroLlegada()).consultarXGaleria(this.getCedulaJuridica());

		int i=0;
		for(RegistroLlegada a : adquisiciones){
	    	Date fechaAdquirida = formato.parse(adquisiciones.get(i).getFecha());
	    	if(fechaAdquirida.equals(pfecha)){
	    		Pintura pintura = new MultiPintura().consultarXNumero(a.getNumPintura());
				listaPinturas.add(pintura);
	    	}
			i++;
		}
		return listaPinturas;
	}

	public String eliminarLlegadaPintura(int pnumPintura) throws SQLException, Exception{
		(new MultiRegistroLlegada()).borrar(pnumPintura);
		return "¡Llegada de pintura eliminada!";
	}

	@Override
	public String toString(){
		String 	estado  = "\nCédula jurídica:        " + this.getCedulaJuridica();
				estado += "\nNombre:                 " + this.getNombre();
				estado += "\nDirección:              " + this.getDireccion();
				estado += "\nFecha de inauguración:  " + this.getFechaInauguracion();
				estado += "\nNombre encargado:       " + this.getNombreEncargado();
				estado += "\nMetros cuadrados:       " + this.getMetrosCuadrados();
		return estado;
	}
}