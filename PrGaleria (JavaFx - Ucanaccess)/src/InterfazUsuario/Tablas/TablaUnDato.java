package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaUnDato {
	
	private final SimpleStringProperty rNombre;

	public TablaUnDato(String snombre){
		this.rNombre = new SimpleStringProperty(snombre);
	}

	public String getRNombre() {
		return rNombre.get();
	}

	public void setRNombre(String pnombre) {
		rNombre.set(pnombre);
	}

}
