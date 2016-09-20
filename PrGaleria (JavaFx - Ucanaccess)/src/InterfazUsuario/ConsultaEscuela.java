package InterfazUsuario;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ConsultaEscuela {

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lCedulaJuridica;
    @FXML
    private Label lNombre;
    @FXML
    private Label lPaisFundacion;
    @FXML
    private Label lFechaFundacion;
    @FXML
    private Label lDescripcion;
    

    public void setScreenPane(AdminVentanas pventana) {
    }
    
    //INICIALIZAR CONTROLADOR
   	public void initialize(URL url, ResourceBundle rb) {
   		
   	}
   	
    //MOSTRAR INFORMACION
    public void mostrarInformacion(String[] pinfo) {
    	lCedulaJuridica.setText(pinfo[0]);
    	lNombre.setText(pinfo[1]);
    	lPaisFundacion.setText(pinfo[2]);
    	lFechaFundacion.setText(pinfo[3]);
    	lDescripcion.setText(pinfo[4]);

    }
    
}
