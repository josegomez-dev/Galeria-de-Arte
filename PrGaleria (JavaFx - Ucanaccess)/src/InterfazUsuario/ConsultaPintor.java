package InterfazUsuario;

import java.net.URL;
import java.util.ResourceBundle;

import InterfazUsuario.Tablas.TablaDosDatos;
import InterfazUsuario.Tablas.TablaUnDato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaPintor implements Initializable, ControlVentanas{

	//DEFINIR TABLA DE PINTURAS
	@FXML
    private TableView<TablaDosDatos> tablaPinturas;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombrePintura;
    @FXML
    private TableColumn<TablaDosDatos, String> cFechaCreacion;
    
    //DEFINIR TABLA DE PATROCINADORES
    @FXML
    private TableView<TablaUnDato> tablaPatrocinadores;
    @FXML
    private TableColumn<TablaUnDato, String> cNombrePatrocinador;
  
    //DEFINIR TABLA DE MAESTROS
  	@FXML
    private TableView<TablaUnDato> tablaMaestros;
    @FXML
    private TableColumn<TablaUnDato, String> cNombreMaestro;
 
      
	//DEFINIR ESPACIOS DE DATOS A MOSTRAR
    @FXML
    private Label lCedula;
    @FXML
    private Label lNombre;
    @FXML
    private Label lApellidos;
    @FXML
    private Label lSeudonimo;
    @FXML
    private Label lNacionalidad;
    @FXML
    private Label lPaisOrigen;
    @FXML
    private Label lCiudadOrigen;
    @FXML
    private Label lFechaNacimiento;
    @FXML
    private Label lFechaFallecimiento;
    @FXML
    private Label lTipo;
    @FXML
    private Label lEscuela;
    
    //DEFINIR OVSERVABLELIST DE ALQUILERES
    private ObservableList<TablaDosDatos> datosPinturas = FXCollections.observableArrayList();

    //DEFINIR OVSERVABLELIST DE PATROCINADORES
    private ObservableList<TablaUnDato> datosPatrocinadores = FXCollections.observableArrayList();

    //DEFINIR OVSERVABLELIST DE MAESTROS
    private ObservableList<TablaUnDato> datosMaestros = FXCollections.observableArrayList();

    public void setScreenPane(AdminVentanas pventana) {
	}

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//TABLA PINTURAS
		cNombrePintura.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
    	cFechaCreacion.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
    	tablaPinturas.setItems(datosPinturas);
    	
    	//TABLA PATROCINADORES
    	cNombrePatrocinador.setCellValueFactory(new PropertyValueFactory<TablaUnDato, String>("rNombre"));
    	tablaPatrocinadores.setItems(datosPatrocinadores);
    	
    	//TABLA MAESTROS
    	cNombreMaestro.setCellValueFactory(new PropertyValueFactory<TablaUnDato, String>("rNombre"));
    	tablaMaestros.setItems(datosMaestros);
    	
	}

    //también recibe la matriz con alquileres realizados
    public void mostrarInformacionPintor(String[] pinfoPintor, String[] pinfoPatrocinadores) {
    	
    	lCedula.setText(pinfoPintor[0]);
    	lNombre.setText(pinfoPintor[1]);
    	lApellidos.setText(pinfoPintor[2] + " " + pinfoPintor[3]);
    	lSeudonimo.setText(pinfoPintor[4]);
    	lNacionalidad.setText(pinfoPintor[5]);
    	lPaisOrigen.setText(pinfoPintor[6]);
    	lCiudadOrigen.setText(pinfoPintor[7]);
    	lFechaNacimiento.setText(pinfoPintor[8]);
    	lFechaFallecimiento.setText(pinfoPintor[9]);
    	lTipo.setText(pinfoPintor[10]);
    	lEscuela.setText(pinfoPintor[11]);
    	
    	//listarPinturas(pinfoPinturas);
    	while(pinfoPatrocinadores != null){
        	listarPatrocinadores(pinfoPatrocinadores);	
    	}
    	//listarMaestros(pinfoMaestros);
    }

    private void listarPinturas(String[][] pinfoPinturas) {
    	for(int i = 0;i < pinfoPinturas.length;i++){
    		TablaDosDatos lista = new TablaDosDatos(pinfoPinturas[i][0], pinfoPinturas[i][1]);
    		datosPinturas.add(lista);
        }
    }

    private void listarPatrocinadores(String[] pinfoPatrocinadores) {
    	for(int i = 0; i < pinfoPatrocinadores.length; i++){
    		TablaUnDato lista = new TablaUnDato(pinfoPatrocinadores[i]);
    		datosPatrocinadores.add(lista);
        }
    }
    
    private void listarMaestros(String[] pinfoMaestros) {
    	for(int i = 0; i < pinfoMaestros.length; i++){
    		TablaUnDato lista = new TablaUnDato(pinfoMaestros[i]);
    		datosMaestros.add(lista);
        }
    }


}
