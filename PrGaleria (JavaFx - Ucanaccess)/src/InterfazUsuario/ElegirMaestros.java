package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaDosDatos;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ElegirMaestros implements Initializable {

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PINTORES
	@FXML
    private TableView<TablaDosDatos> tablaMaestros;
	@FXML
    private TableColumn<TablaDosDatos, String> cCedula;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombre;

    @FXML
    private TableView<TablaDosDatos> tablaPintores;
    @FXML
    private TableColumn<TablaDosDatos, String> cCedula2;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombre2;


    //DEFINIR FORMULARIO DEL FINANCIAR PITNORES
    @FXML
    private TextField iBuscarNombre;

    //DEFINIR BOTONES
    @FXML
    private Button btnListar;
    @FXML
    private Button btnElegir;
    @FXML
    private Button btnQuitar;
    @FXML
    private Button btnBuscar;


    //DEFINIR OVSERVABLELIST TABLA FINANCIAMIENTO DE PINTORES
    private ObservableList<TablaDosDatos> datosMaestro = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoMaestro;
    private String idMaestroSeleccionado;// ID MESTROS
    private boolean listenerMaestroCreado = false;

    //DEFINIR OVSERVABLELIST TABLA PINTORES
    private ObservableList<TablaDosDatos> datosPintores = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoPintor;
    private String idPintorSeleccionado;// ID PINTORES
    private boolean listenerPintorCreado = false;

    //CÉDULA DEL PINTOR A QUIÉN SE LE ASIGNAN LOS MAESTROS
    private String cedulaPintor;

    //DEFINIR CONSTRUCTOR
    public ElegirMaestros(){
    	gestor = new Gestor();
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cCedula.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
		cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
	    tablaMaestros.setItems(datosMaestro);
    	seleccionDatoMaestro = tablaMaestros.getSelectionModel().getSelectedItems();

    	cCedula2.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
	    cNombre2.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
	    tablaPintores.setItems(datosPintores);
    	seleccionDatoPintor = tablaPintores.getSelectionModel().getSelectedItems();

    	try {
    		listarInformacionPintoresNoMaestros();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//LISTAR INFORMACIÓN DE PINTORES
    public void listarInformacionPintoresNoMaestros() throws SQLException, Exception{
    	if(listenerPintorCreado){
    		seleccionDatoPintor.removeListener(selectorTablaPintor);
        	tablaPintores.getItems().clear();
        	agregarListenersTablaPintor();
    	}else{
    		agregarListenersTablaPintor();
    	}
    	String[][] listaPintores = gestor.pintorConsultarListaNoMaestros(cedulaPintor);
    	for(int i = 0; i < listaPintores.length; i++){
			TablaDosDatos fila = new TablaDosDatos(listaPintores[i][0], listaPintores[i][1]);
	    	datosPintores.add(fila);
        }
    }

	//DEFINIR LISTENER DE LA TABLA MAESTROS
    private final ListChangeListener<TablaDosDatos> selectorTablaMaestro = new ListChangeListener<TablaDosDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaDosDatos > c) {
            try {
				extraerIDFPintorSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //DEFINIR LISTENER DE LA TABLA PINTORES
    private final ListChangeListener<TablaDosDatos> selectorTablaPintor = new ListChangeListener<TablaDosDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaDosDatos > c) {
            try {
				extraerIDPintorSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };


    //EXTRAER ID DE LA MAESTROS
    private void extraerIDFPintorSeleccionado() throws SQLException, Exception {
    	final TablaDosDatos tabla = getTablaMaestroSeleccionada();
        idMaestroSeleccionado = tabla.getRCedula();
    }

    //EXTRAER ID DE LA PINTOR
    private void extraerIDPintorSeleccionado() throws SQLException, Exception {
    	final TablaDosDatos tabla = getTablaPintorSeleccionada();
        idPintorSeleccionado = tabla.getRCedula();
    }

    //SELECCIONAR UNA MAESTROS DE LA TABLA
    public TablaDosDatos getTablaMaestroSeleccionada() {
        if (tablaMaestros != null) {
			List<TablaDosDatos> tabla = tablaMaestros.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //SELECCIONAR UNA PINTOR DE LA TABLA
    public TablaDosDatos getTablaPintorSeleccionada() {
        if (tablaPintores != null) {
			List<TablaDosDatos> tabla = tablaPintores.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA PINTURAS
    private void agregarListenersTablaPintor(){
    	seleccionDatoPintor = tablaPintores.getSelectionModel().getSelectedItems();
    	seleccionDatoPintor.addListener(selectorTablaPintor);
    	listenerPintorCreado = true;
    }

    //DEFINIR ACCIÓN DE LOS BOTONES
    public void quitar(ActionEvent event){

    }

    public void elegir(ActionEvent event) throws SQLException, Exception{
    	gestor.pintorElegirMaestro(cedulaPintor, idPintorSeleccionado);
    	listarInformacionPintoresNoMaestros();
	}

	public void buscar(ActionEvent event) throws SQLException, Exception{
//		if(listenerPintorCreado){
//    		seleccionDatoPintor.removeListener(selectorTablaPintor);
//        	tablaPintores.getItems().clear();
//        	agregarListenersTablaPintor();
//    	}else{
//    		agregarListenersTablaPintor();
//    	}
//    	if(!iBuscarNombre.getText().isEmpty()){
//    		String[] infoPintura = gestor.pintorConsultarXNombre(iBuscarNombre.getText());
//        	if(infoPintura != null){
//        		datosPintores.add(new TablaDosDatos(infoPintura[0], infoPintura[1]));
//        	}
//    	}else{
//    		listarInformacionPintores();
//    	}
	}

	public void listar(ActionEvent event){

	}

    public void guardarCedulaPintor(String pcedula){
    	cedulaPintor = pcedula;
    }
}
