package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaTresDatos {

	private final SimpleStringProperty rCedula;
	private final SimpleStringProperty rNombre;
	private final SimpleStringProperty rTipo;

	
	public TablaTresDatos(String snombre, String sfechaCreacion, String stipo){
		this.rCedula = new SimpleStringProperty(snombre);
		this.rNombre = new SimpleStringProperty(sfechaCreacion);
		this.rTipo = new SimpleStringProperty(stipo);
	}
	
	public String getRCedula() {
		return rCedula.get();
	}

	public void setRCedula(String pcedula) {
		rCedula.set(pcedula);
	}

	public String getRNombre() {
		return rNombre.get();
	}

	public void setRNombre(String pnombre) {
		rNombre.set(pnombre);
	}
	
	public String getRTipo() {
		return rTipo.get();
	}

	public void setRTipo(String ptipo) {
		rTipo.set(ptipo);
	}
	
	
}
