package InterfazUsuario;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Principal implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

    //DEFINIR BOTONES
    @FXML
    private Button btnGalerias;
    @FXML
    private Button btnPintores;
    @FXML
    private Button btnEscuelas;
    @FXML
    private Button btnPatrocinadores;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

	public void admiGalerias(ActionEvent event){
		ventana.mostrarVentana("Galeria");
	}

	public void admiPintores(ActionEvent event){
		ventana.mostrarVentana("Pintor");
	}

	public void admiEscuelas(ActionEvent event){
		ventana.mostrarVentana("Escuela");
	}

	public void admiPatrocinadores(ActionEvent event){
		ventana.mostrarVentana("Patrocinador");
	}

}
