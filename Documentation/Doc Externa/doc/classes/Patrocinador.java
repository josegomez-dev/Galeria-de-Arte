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
	private String fechaInicio;
	private String fechaFin;

	private ArrayList<Pintor> pintores;

	public Patrocinador(String pcedula, String pnombre, String papellido1, String papellido2, String ppaisOrigen,
						String pciudadOrigen, String pfechaFallecimiento, String pfechaInicio, String pfechaFin) {
		setCedula(pcedula);
		setNombre(pnombre);
		setApellido1(papellido1);
		setApellido2(papellido2);
		setPaisOrigen(ppaisOrigen);
		setCiudadOrigen(pciudadOrigen);
		setFechaFallecimiento(pfechaFallecimiento);
		setFechaInicio(pfechaInicio);
		setFechaFin(pfechaFin);
		pintores = new ArrayList<Pintor>();
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

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String pfechaInicio) {
		this.fechaInicio = pfechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String pfechaFin) {
		this.fechaFin = pfechaFin;
	}

	public ArrayList<Pintor> getPintores() throws SQLException, Exception{
		if(pintores.size() == 0){
//			setPintores((new MultiPintor()).listar(this.getCedula()));
		}
		return pintores;
	}

	private void setPintores(ArrayList<Pintor> ppintores){
		pintores = ppintores;
	}

	@Override
	public String toString(){
		String 	estado  = "\nCédula:                  " + this.getCedula();
				estado += "\nNombre completo:         " + this.getNombre() + " " + this.getApellido1() + " " + this.getApellido2();
				estado += "\nPaís de origen:          " + this.getPaisOrigen();
				estado += "\nCiudad de origen:        " + this.getCiudadOrigen();
				estado += "\nFecha de fallecimiento:  " + this.getFechaFallecimiento();
				estado += "\nFecha de inicio:         " + this.getFechaInicio();
				estado += "\nFecha de fin:            " + this.getFechaFin();
		return estado;
	}
}
