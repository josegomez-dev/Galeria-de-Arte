package InterfazUsuario;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistroLlegada implements Initializable, ControlVentanas{

	AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR MENSAJES DE VALIDACIÓN
	@FXML
    private Label lMensaje;
    @FXML
    private Label lFecha;
    @FXML
    private Label lCondicionLlegada;
    @FXML
    private Label lCondicionActual;
    @FXML
    private Label lCostoAdquisicion;

    //DEFINIR FORMULARIO DEL REGISTRO DE LLEGADA
    @FXML
    private TextField iFecha;
    @FXML
    private ComboBox<String> iCondicionLlegada;
    @FXML
    private ComboBox<String> iCondicionActual;
    @FXML
    private TextField iCostoAdquisicion;

    //DEFINIR BOTONES
    @FXML
    private Button btnGuardar;

    //DATOS IMPORTANTES PARA EL REGISTRO DE LLEGADA DE LA PINTURA A LA GALERÍA
    private String cedulaGaleria;
    private int numPintura;
    private String exposicion;

    //DEFINIR CONSTRUCTOR
    public RegistroLlegada(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //DEFINIR COMBOBOX DE TIPOS
    private ObservableList<String> tiposCondicion = FXCollections.observableArrayList(
    		"Excelente", "Buena", "Regular", "Mala"
    );

    //INICIALIZAR CONTROLADOR
   	@Override
   	public void initialize(URL arg0, ResourceBundle arg1) {
   		iCondicionLlegada.setItems(tiposCondicion);
   		iCondicionActual.setItems(tiposCondicion);

   		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        iFecha.setText(formato.format(new Date()));
   	}

	//DEFINIR ACCIÓN DE LOS BOTONES
    public void guardar(ActionEvent event){
    	Boolean fecha = ValidacionForm.campoTipoFecha(iFecha, lFecha, "Formato no válido!");
    	Boolean condicionLlegada = ValidacionForm.campoComboBox(iCondicionLlegada, lCondicionLlegada, "Dato requerido!");
    	Boolean condicionActual = ValidacionForm.campoComboBox(iCondicionActual, lCondicionActual, "Dato requerido!");
    	Boolean costo = ValidacionForm.campoTipoNumero(iCostoAdquisicion, lCostoAdquisicion, "Formato no válido!");

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(fecha && condicionLlegada && condicionActual && costo){
			lMensaje.setText(gestor.galeriaRegistrarLlegadaPintura(cedulaGaleria, numPintura, exposicion, iFecha.getText(),
													iCondicionLlegada.getValue(), iCondicionActual.getValue(),
													Double.parseDouble(iCostoAdquisicion.getText())));
    		limpiarInputs();
    		limpiarLabels();
    		gestor.pinturaAsignarGaleria(cedulaGaleria, numPintura);
		}

    }

    public void limpiarLabels(){
    	lFecha.setText("");
    	lCondicionLlegada.setText("");
    	lCondicionActual.setText("");
    	lCostoAdquisicion.setText("");
    }

	 public void limpiarInputs(){
    	iFecha.clear();
    	iCondicionLlegada.getSelectionModel().clearSelection();
    	iCondicionActual.getSelectionModel().clearSelection();
    	iCostoAdquisicion.clear();
	 }

	 public void guardarDatos(String pcedGaleria, int pnumPintura, String pexposicion){
		 cedulaGaleria = pcedGaleria;
		 numPintura = pnumPintura;
		 exposicion = pexposicion;
	 }
}
