package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaDosDatos;
import InterfazUsuario.Tablas.TablaUnDato;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaPatrocinador {



	//DEFINIR TABLA DE PINTURAS
	@FXML
    private TableView<TablaUnDato> tablaPintoresFinanciados;
    @FXML
    private TableColumn<TablaUnDato, String> cNombre;


    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lCedula;
    @FXML
    private Label lNombre;
    @FXML
    private Label lPrimerApellido;
    @FXML
    private Label lSegundoApellido;
    @FXML
    private Label lPaisOrigen;
    @FXML
    private Label lCiudadOrigen;
    @FXML
    private Label lFechaFallecimiento;
    @FXML
    private Label lFechaInicio;
    @FXML
    private Label lFechaFin;


    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaUnDato> datosPintores = FXCollections.observableArrayList();
    private ObservableList<TablaUnDato> seleccionDatoPintores;

    public void setScreenPane(AdminVentanas pventana) {

    }


    //INICIALIZAR CONTROLADOR
	public void initialize(URL url, ResourceBundle rb) {
		cNombre.setCellValueFactory(new PropertyValueFactory<TablaUnDato, String>("rNombre"));
	    tablaPintoresFinanciados.setItems(datosPintores);
    	seleccionDatoPintores = tablaPintoresFinanciados.getSelectionModel().getSelectedItems();
	}

	//MOSTRAR INFORMACION
    public void mostrarInformacion(String[] pinfo) {
    	listarInformacion(pinfo);
    }


    private void listarInformacion(String[] pinfo) {
    	for(int i=0;i<pinfo.length;i++){
    		TablaUnDato lista = new TablaUnDato(pinfo[i]);
    		datosPintores.add(lista);
        }
    }
}
