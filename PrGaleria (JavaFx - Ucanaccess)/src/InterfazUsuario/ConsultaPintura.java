package InterfazUsuario;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConsultaPintura {

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lNombre;
    @FXML
    private Label lDimensiones;
    @FXML
    private Label lFechaCreacion;
    @FXML
    private Label lDuracionCreacion;
    @FXML
    private Label lTecnica;
    @FXML
    private Label lFamosa;
    @FXML
    private Label lHistoria;
    @FXML
    private Label lNombreGaleria;
    @FXML
    private Label lNombrePintor;
    @FXML
    private Label lFechaLlegada;
    @FXML
    private Label lCondicionLlegada;
    @FXML
    private Label lCondicionActual;
    @FXML
    private Label lCostoAdquisicion;
    @FXML
    private Label lTipoPintor;


  //MOSTRAR INFORMACION
    public void mostrarInformacion(String[] pinfo, String[] pinfoLlegada) {
    	lNombre.setText(pinfo[0]);
    	lDimensiones.setText(pinfo[1]+" cm   X   "+pinfo[2]+" cm");
    	lFechaCreacion.setText(pinfo[3]);
    	lDuracionCreacion.setText(pinfo[4]+" año(s)    "+pinfo[5]+" mes(es)    "+pinfo[6]+" día(s)");
    	lTecnica.setText(pinfo[7]);
    	lFamosa.setText(pinfo[8]);
    	lHistoria.setText(pinfo[9]);
    	lNombrePintor.setText(pinfo[10]);
    	lTipoPintor.setText(pinfo[11]);
    	lNombreGaleria.setText(pinfo[12]);

    	if(pinfoLlegada != null){
    		lFechaLlegada.setText(pinfoLlegada[0]);
        	lCondicionLlegada.setText(pinfoLlegada[1]);
        	lCondicionActual.setText(pinfoLlegada[2]);
        	lCostoAdquisicion.setText(pinfoLlegada[3]+" dólares");
    	}
    }

}
