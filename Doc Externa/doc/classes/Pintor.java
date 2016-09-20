package CapaLogica;

import java.sql.SQLException;
import java.util.ArrayList;

import Multi.MultiEscuela;
import Multi.MultiPatrocinador;
import Multi.MultiPintor;
import Multi.MultiPintura;

public class Pintor {

	private String cedula;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String seudónimo;
	private String nacionalidad;
	private String paisOrigen;
	private String ciudadOrigen;
	private String fechaNacimiento;
	private String fechaFallecimiento;
	private String tipo;
	private String cedulaEscuela;

	private ArrayList<Patrocinador> patrocinadores;
	private ArrayList<Pintura> obras;
	private ArrayList<Pintor> maestros;
	private Escuela escuela;

	public Pintor(String pcedula, String pnombre, String papellido1, String papellido2, String pseudónimo,
					String pnacionalidad, String ppaisOrigen, String pciudadOrigen, String pfechaNacimiento,
					String pfechaFallecimiento, String ptipo, String pcedulaEscuela) {
		setCedula(pcedula);
		setNombre(pnombre);
		setApellido1(papellido1);
		setApellido2(papellido2);
		setSeudónimo(pseudónimo);
		setNacionalidad(pnacionalidad);
		setPaisOrigen(ppaisOrigen);
		setCiudadOrigen(pciudadOrigen);
		setFechaNacimiento(pfechaNacimiento);
		setFechaFallecimiento(pfechaFallecimiento);
		setTipo(ptipo);
		setCedulaEscuela(pcedulaEscuela);
		patrocinadores = new ArrayList<Patrocinador>();
		obras = new ArrayList<Pintura>();
		maestros = new ArrayList<Pintor>();
		escuela = null;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String pcedula) {
		this.cedula = pcedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String pnombre) {
		this.nombre = pnombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String papellido1) {
		this.apellido1 = papellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String papellido2) {
		this.apellido2 = papellido2;
	}

	public String getSeudónimo() {
		return seudónimo;
	}

	public void setSeudónimo(String pseudónimo) {
		this.seudónimo = pseudónimo;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String pnacionalidad) {
		this.nacionalidad = pnacionalidad;
	}

	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String ppaisOrigen) {
		this.paisOrigen = ppaisOrigen;
	}

	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(String pciudadOrigen) {
		this.ciudadOrigen = pciudadOrigen;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String pfechaNacimiento) {
		this.fechaNacimiento = pfechaNacimiento;
	}

	public String getFechaFallecimiento() {
		return fechaFallecimiento;
	}

	public void setFechaFallecimiento(String pfechaFallecimiento) {
		this.fechaFallecimiento = pfechaFallecimiento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String ptipo) {
		this.tipo = ptipo;
	}

	public String getCedulaEscuela() {
		return cedulaEscuela;
	}

	public void setCedulaEscuela(String pcedulaEscuela) {
		this.cedulaEscuela = pcedulaEscuela;
	}

	public ArrayList<Patrocinador> getPatrocinadores() throws SQLException, Exception{
		if(patrocinadores.size() == 0){
			setPatrocinadores((new MultiPatrocinador()).listar(this.getCedula()));
		}
		return patrocinadores;
	}

	private void setPatrocinadores(ArrayList<Patrocinador> ppatrocinadores){
		patrocinadores = ppatrocinadores;
	}

	public ArrayList<Pintor> getMaestros() throws SQLException, Exception{
		if(maestros.size() == 0){
//			setMaestros((new MultiPintor()).listar(this.getCedula()));
		}
		return maestros;
	}

	private void setMaestros(ArrayList<Pintor> pmaestros){
		maestros = pmaestros;
	}

	public Escuela getEscuela() throws Exception{
		if (escuela == null) {
			setEscuela((new MultiEscuela()).consultarXCedulaJuridica(this.getCedulaEscuela()));
		}
		return escuela;
	}

	public void setEscuela(Escuela pescuela){
		escuela = pescuela;
	}

	public String registrarPintura(String pnombre, double palto, double pancho, String pfecha, int panios, int pmeses,
									int pdias, String ptecnica, String pfamoso, String phistoria) throws SQLException, Exception{
		Pintura pintura = new MultiPintura().crear(pnombre, palto, pancho, pfecha, panios, pmeses, pdias, ptecnica,
													pfamoso, phistoria, this.getCedula());
		if(pintura != null){
			return "¡Pintura registrada!";
		}else{
			return "¡El registro falló!";
		}
	}

	public ArrayList<Pintura> consultarPinturas() throws SQLException, Exception{
		obras = new MultiPintura().consultarXPintor(this.getCedula());
		return obras;
	}

	@Override
	public String toString(){
		String 	estado  = "\nCédula:                  " + this.getCedula();
				estado += "\nNombre completo:         " + this.getNombre() + " " + this.getApellido1() + " " + this.getApellido2();
				estado += "\nSeudónimo:               " + this.getSeudónimo();
				estado += "\nNacionalidad:            " + this.getNacionalidad();
				estado += "\nPaís de origen:          " + this.getPaisOrigen();
				estado += "\nCiudad de origen:        " + this.getCiudadOrigen();
				estado += "\nFecha de nacimiento:     " + this.getFechaNacimiento();
				estado += "\nFecha de fallecimiento:  " + this.getFechaFallecimiento();
				estado += "\nTipo:                    " + this.getTipo();
		return estado;
	}
}