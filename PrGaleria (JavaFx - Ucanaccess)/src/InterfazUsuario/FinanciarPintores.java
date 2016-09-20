package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaDosDatos;
import InterfazUsuario.Tablas.TablaTresDatos;
import InterfazUsuario.Tablas.TablaUnDato;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FinanciarPintores implements Initializable {

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PINTORES
	@FXML
    private TableView<TablaDosDatos> tablaPatrocinio;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombre;
    @FXML
    private TableColumn<TablaDosDatos, String> cTipo;

    @FXML
    private TableView<TablaTresDatos> tablaPintores;
    @FXML
    private TableColumn<TablaTresDatos, String> cCedula;
    @FXML
    private TableColumn<TablaTresDatos, String> cNombreCompleto;
    @FXML
    private TableColumn<TablaTresDatos, String> cTipo2;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;

    @FXML
    private Label lBuscar;

    @FXML
    private Label lBuscar2;

    //DEFINIR FORMULARIO DEL FINANCIAR PITNORES
    @FXML
    private TextField iNombre;
    @FXML
    private TextField iNombre2;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnAdquirir;
    @FXML
    private Button btnQuitar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnBuscar2;

    //DEFINIR OVSERVABLELIST TABLA FINANCIAMIENTO DE PINTORES
    private ObservableList<TablaDosDatos> datosPatrocinio = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoPatrocinio;
    private boolean patrocinioSeleccionado = true;// true : REGISTRO
 											// false: MODIFICACIÓN
    private String idPatrocinioSeleccionado;// ID Patrocinio
    private boolean listenerPatrocinioCreado = false;

  //DEFINIR OVSERVABLELIST TABLA PINTORES
    private ObservableList<TablaTresDatos> datosPintores = FXCollections.observableArrayList();
    private ObservableList<TablaTresDatos> seleccionDatoPintor;
    private boolean pintorSeleccionado = true;// true : REGISTRO
 											// false: MODIFICACIÓN
    private String idPintorSeleccionado;// ID PINTORES
    private boolean listenerPintorCreado = false;

    //DEFINIR CONSTRUCTOR
    public FinanciarPintores(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
	    cTipo.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
	    tablaPatrocinio.setItems(datosPatrocinio);
    	seleccionDatoPatrocinio = tablaPatrocinio.getSelectionModel().getSelectedItems();

    	cCedula.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rCedula"));
	    cNombreCompleto.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rNombre"));
	    cTipo2.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rTipo"));
	    tablaPintores.setItems(datosPintores);
    	seleccionDatoPintor = tablaPintores.getSelectionModel().getSelectedItems();

	}

	//DEFINIR LISTENER DE LA TABLA FPINTORES
    private final ListChangeListener<TablaDosDatos> selectorTablaFPintor = new ListChangeListener<TablaDosDatos>() {
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
    private final ListChangeListener<TablaTresDatos> selectorTablaPintor = new ListChangeListener<TablaTresDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaTresDatos > c) {
            try {
				extraerIDPintorSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };


    // CARGAR INFORMACIÓN DEL FPINTOR SELECCIONADO DE LA TABLA
    private void cargarDatosFPintorSeleccionada(String[] pinfo){
    	limpiarLabels();
        iNombre.setText(pinfo[0]);
    }

 // CARGAR INFORMACIÓN DEL PINTOR SELECCIONADO DE LA TABLA
    private void cargarDatosPintorSeleccionada(String[] pinfo){
    	limpiarLabels();
        iNombre2.setText(pinfo[0]);
    }

    //EXTRAER ID DE LA FPINTOR
    private void extraerIDFPintorSeleccionado() throws SQLException, Exception {
    	patrocinioSeleccionado = false;
    	final TablaDosDatos tabla = getTablaFPintorSeleccionada();
        idPatrocinioSeleccionado = tabla.getRCedula();
        //cargarDatosPintoresSeleccionada(gestor.pintorConsultarXId(idPinturaSeleccionado));

        lMensaje.setText("");
    }

    //EXTRAER ID DE LA PINTOR
    private void extraerIDPintorSeleccionado() throws SQLException, Exception {
    	pintorSeleccionado = false;
    	final TablaTresDatos tabla = getTablaPintorSeleccionada();
        idPintorSeleccionado = tabla.getRCedula();
        //cargarDatosPeliculaSeleccionada(gestor.pinturaConsultarXId(idPinturaSeleccionado));

        lMensaje.setText("");
    }

    //SELECCIONAR UNA FPINTOR DE LA TABLA
    public TablaDosDatos getTablaFPintorSeleccionada() {
        if (tablaPatrocinio != null) {
			List<TablaDosDatos> tabla = tablaPatrocinio.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //SELECCIONAR UNA PINTOR DE LA TABLA
    public TablaTresDatos getTablaPintorSeleccionada() {
        if (tablaPintores != null) {
			List<TablaTresDatos> tabla = tablaPintores.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaTresDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA PINTURAS
    private void agregarListenersTablaFPintor(){
    	seleccionDatoPatrocinio = tablaPatrocinio.getSelectionModel().getSelectedItems();
    	seleccionDatoPatrocinio.addListener(selectorTablaFPintor);
    	listenerPatrocinioCreado = true;
    }

    //AGREGAR LISTENER A TABLA PINTURAS
    private void agregarListenersTablaPintor(){
    	seleccionDatoPintor = tablaPintores.getSelectionModel().getSelectedItems();
    	seleccionDatoPintor.addListener(selectorTablaPintor);
    	listenerPintorCreado = true;
    }

    //MOSTRAR INFORMACION
    public void mostrarInformacion(String[][] pinfoPatrocinios, String[][] pinfoPintores) {
    	listarInformacion(pinfoPatrocinios, pinfoPintores);
    }
    
    private void listarInformacion(String[][] pinfoPatrocinios, String[][] pinfoPintores) {
    	if(pinfoPatrocinios.length > 0){
    		for(int i=0;i<pinfoPatrocinios.length;i++){
        		TablaDosDatos lista = new TablaDosDatos(pinfoPatrocinios[i][0], pinfoPatrocinios[i][1]);
        		datosPatrocinio.add(lista);
            }	
    	}
    
    	if(pinfoPintores.length > 0){
    		for(int i=0;i<pinfoPintores.length;i++){
        		TablaTresDatos lista = new TablaTresDatos(pinfoPintores[i][0], pinfoPintores[i][1], pinfoPintores[i][2]);
        		datosPintores.add(lista);
            }	
    	}
    }
    
    //DEFINIR ACCIÓN DE LOS BOTONES
    public void atras(ActionEvent event){
    	ventana.mostrarVentana("Principal");
    }

    public void quitar(ActionEvent event){

    }

    public void adquirir(ActionEvent event) throws SQLException, Exception{

	}

	public void buscar(ActionEvent event){

	}

	public void buscar2(ActionEvent event){

	}

    public void limpiarLabels(){
    	lMensaje.setText("");

	 }

    public void limpiarInputs(){
    	iNombre.clear();
    	iNombre2.clear();

	 }

    public void bloquearInputs(){
    	iNombre.setDisable(true);
    	iNombre2.setDisable(true);

	 }

	 public void desbloquearInputs(){
		 iNombre.setDisable(false);
		 iNombre2.setDisable(false);
	 }

	 public void bloquearBotones(){
		 btnAtras.setDisable(true);
		 btnBuscar.setDisable(true);
		 btnQuitar.setDisable(true);
		 btnAdquirir.setDisable(true);
	 }

	 public void desbloquearBotones(){
		 btnAtras.setDisable(false);
		 btnBuscar.setDisable(false);
		 btnQuitar.setDisable(false);
		 btnAdquirir.setDisable(false);
	 }


}
