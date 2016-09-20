package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaTresDatos;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConsultaGaleria implements Initializable, ControlVentanas{

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PINTURAS
	@FXML
    private TableView<TablaTresDatos> tablaPinturasAdquiridas;
	@FXML
    private TableColumn<TablaTresDatos, String> cNumero;
    @FXML
    private TableColumn<TablaTresDatos, String> cNombre;
    @FXML
    private TableColumn<TablaTresDatos, String> cExposicion;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lCedulaJuridica;
    @FXML
    private Label lNombre;
    @FXML
    private Label lDireccion;
    @FXML
    private Label lTelefono;
    @FXML
    private Label lFechaInaugurada;
    @FXML
    private Label lEncargado;
    @FXML
    private Label lMetrosCuadrados;

    //BUTTON
    @FXML
    private Button btnConsultarPintura;

    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaTresDatos> datosPinturas = FXCollections.observableArrayList();
    private ObservableList<TablaTresDatos> seleccionDatoPintura;
    private String idPinturaSeleccionado;// ID PINTURA
    private boolean listenerPinturaCreado = false;

    //DEFINIR CONSTRUCTOR
    public ConsultaGaleria(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {

    }

    //INICIALIZAR CONTROLADOR
  	public void initialize(URL url, ResourceBundle rb) {
  		cNumero.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rCedula"));
	    cNombre.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rNombre"));
	    cExposicion.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rTipo"));
	    tablaPinturasAdquiridas.setItems(datosPinturas);
    	seleccionDatoPintura = tablaPinturasAdquiridas.getSelectionModel().getSelectedItems();
  	}

  	//DEFINIR LISTENER DE LA TABLA PINTURAS
    private final ListChangeListener<TablaTresDatos> selectorTablaPintura = new ListChangeListener<TablaTresDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaTresDatos > c) {
            try {
				extraerIDPinturaSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //EXTRAER ID DE LA PINTURA
    private void extraerIDPinturaSeleccionado() throws SQLException, Exception {
    	final TablaTresDatos tabla = getTablaPinturaSeleccionada();
        idPinturaSeleccionado = tabla.getRCedula();
        btnConsultarPintura.setDisable(false);
    }

    //SELECCIONAR UNA PINTURA DE LA TABLA
    public TablaTresDatos getTablaPinturaSeleccionada() {
        if (tablaPinturasAdquiridas != null) {
			List<TablaTresDatos> tabla = tablaPinturasAdquiridas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaTresDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA PINTURAS
    private void agregarListenersTablaPintura(){
    	seleccionDatoPintura = tablaPinturasAdquiridas.getSelectionModel().getSelectedItems();
    	seleccionDatoPintura.addListener(selectorTablaPintura);
    	listenerPinturaCreado = true;
    }

	//MOSTRAR INFORMACION
    public void mostrarInformacion(String[] pinfo, String[][] plistaPinturas) {
    	lCedulaJuridica.setText(pinfo[0]);
    	lNombre.setText(pinfo[1]);
    	lDireccion.setText(pinfo[2]);
    	lTelefono.setText(pinfo[3]);
    	lFechaInaugurada.setText(pinfo[4]);
    	lEncargado.setText(pinfo[5]);
    	lMetrosCuadrados.setText(pinfo[6]);

    	listarPinturasAdquiridas(plistaPinturas);
    }

    public void consultar(ActionEvent event) throws SQLException, Exception{
    	String[] infoPintura = gestor.pinturaConsultarXNumero(Integer.parseInt(idPinturaSeleccionado));
    	String[] infoPinturaLlegada = gestor.pinturaConsultarLlegadaAGaleria(Integer.parseInt(idPinturaSeleccionado));

    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("ConsultaPintura.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle("Registro de llegada");
		dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        ConsultaPintura controller = loader.getController();
        controller.mostrarInformacion(infoPintura, infoPinturaLlegada);

        dialogStage.showAndWait();
	}

    private void listarPinturasAdquiridas(String[][] pinfo) {
    	if(listenerPinturaCreado){
    		seleccionDatoPintura.removeListener(selectorTablaPintura);
    		tablaPinturasAdquiridas.getItems().clear();
        	agregarListenersTablaPintura();
    	}else{
    		agregarListenersTablaPintura();
    	}
		for(int i = 0; i < pinfo.length; i++){
			TablaTresDatos fila = new TablaTresDatos(pinfo[i][0], pinfo[i][1], pinfo[i][2]);
			datosPinturas.add(fila);
        }
    }
}
