package CapaLogica;

import java.sql.SQLException;
import java.util.ArrayList;

import Multi.MultiPintor;

public class Patrocinador {

	private String cedula;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String paisOrigen;
	private String ciudadOrigen;
	private String fechaFallecimiento;

	private ArrayList<Pintor> pintores;

	public Patrocinador(String pcedula, String pnombre, String papellido1, String papellido2, String ppaisOrigen,
						String pciudadOrigen, String pfechaFallecimiento) {
		setCedula(pcedula);
		setNombre(pnombre);
		setApellido1(papellido1);
		setApellido2(papellido2);
		setPaisOrigen(ppaisOrigen);
		setCiudadOrigen(pciudadOrigen);
		setFechaFallecimiento(pfechaFallecimiento);
		pintores = new ArrayList<Pintor>();
	}

	public String getCedula() {
		return cedula;
	}

	private void setCedula(String pcedula) {
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

	public String getFechaFallecimiento() {
		return fechaFallecimiento;
	}

	public void setFechaFallecimiento(String pfechaFallecimiento) {
		this.fechaFallecimiento = pfechaFallecimiento;
	}

	public ArrayList<Pintor> getPintores() throws SQLException, Exception{
		if(pintores.size() == 0){
			setPintores((new MultiPintor()).listarXPatrocinador(this.getCedula()));
		}
		return pintores;
	}

	private void setPintores(ArrayList<Pintor> ppintores){
		pintores = ppintores;
	}

	@Override
	public String toString(){
		String 	estado  = "\nCédula:                  " + this.getCedula() + "\n"; 
				estado += "\nNombre completo:         " + this.getNombre() + " " + this.getApellido1() + " " + this.getApellido2() + "\n";
				estado += "\nPaís de origen:          " + this.getPaisOrigen() + "\n";
				estado += "\nCiudad de origen:        " + this.getCiudadOrigen() + "\n";
				estado += "\nFecha de fallecimiento:  " + this.getFechaFallecimiento() + "\n";
		return estado;
	}
}
