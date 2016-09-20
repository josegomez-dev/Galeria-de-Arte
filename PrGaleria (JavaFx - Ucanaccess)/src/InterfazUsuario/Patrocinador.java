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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Patrocinador implements Initializable, ControlVentanas {

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PINTURAS
	@FXML
    private TableView<TablaDosDatos> tablaPatrocinador;
    @FXML
    private TableColumn<TablaDosDatos, String> cCedula;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombre;


    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
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


    //DEFINIR FORMULARIO DEL PINTURA
    @FXML
    private TextField iCedula;
    @FXML
    private TextField iNombre;
    @FXML
    private TextField iPrimerApellido;
    @FXML
    private TextField iSegundoApellido;
    @FXML
    private TextField iPaisOrigen;
    @FXML
    private TextField iCiudadOrigen;
    @FXML
    private TextField iFechaFallecimiento;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnFinanciarPintores;
    @FXML
    private Button btnConsultar;


    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaDosDatos> datosPatrocinador = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoPatrocinador;
    private boolean patrocinadorSeleccionado = true;// true : REGISTRO
 											// false: MODIFICACIÓN
    private String idPatrocinadorSeleccionado;// ID PATROCINADOR
    private boolean listenerPatrocinadorCreado = false;

    //DEFINIR CONSTRUCTOR
    public Patrocinador(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cCedula.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
	    cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
	    tablaPatrocinador.setItems(datosPatrocinador);
    	seleccionDatoPatrocinador = tablaPatrocinador.getSelectionModel().getSelectedItems();
//		btnEliminar.setDisable(true);
	}


	//DEFINIR LISTENER DE LA TABLA PATROCINADOR
    private final ListChangeListener<TablaDosDatos> selectorTablaPatrocinador = new ListChangeListener<TablaDosDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaDosDatos > c) {
            try {
				extraerIDPatrocinadorSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };


    // CARGAR INFORMACIÓN DEL PATROCINADOR SELECCIONADO DE LA TABLA
    private void cargarDatosPatrocinadorSeleccionada(String[] pinfo){
    	limpiarLabels();
        iCedula.setText(pinfo[0]);
        iNombre.setText(pinfo[1]);
        iPrimerApellido.setText(pinfo[2]);
        iSegundoApellido.setText(pinfo[3]);
        iPaisOrigen.setText(pinfo[4]);
        iCiudadOrigen.setText(pinfo[5]);
        iFechaFallecimiento.setText(pinfo[6]);

    }

    //EXTRAER ID DE LA PATROCINADOR
    private void extraerIDPatrocinadorSeleccionado() throws SQLException, Exception {
    	patrocinadorSeleccionado = false;
    	final TablaDosDatos tabla = getTablaPatrocinadorSeleccionada();
        idPatrocinadorSeleccionado = tabla.getRCedula();
        cargarDatosPatrocinadorSeleccionada(gestor.patrocinadorConsultarXCedula(idPatrocinadorSeleccionado));

        desbloquearBotones();
        btnEliminar.setDisable(false);
        lMensaje.setText("");
    }

    //SELECCIONAR UNA PATROCINADOR DE LA TABLA
    public TablaDosDatos getTablaPatrocinadorSeleccionada() {
        if (tablaPatrocinador != null) {
			List<TablaDosDatos> tabla = tablaPatrocinador.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA PATROCINADOR
    private void agregarListenersTablaPatrocinador(){
    	seleccionDatoPatrocinador = tablaPatrocinador.getSelectionModel().getSelectedItems();
    	seleccionDatoPatrocinador.addListener(selectorTablaPatrocinador);
    	listenerPatrocinadorCreado = true;
    }


  //DEFINIR ACCIÓN DE LOS BOTONES
    public void atras(ActionEvent event){
    	limpiarTablaPatrocinador();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
    	ventana.mostrarVentana("Principal");
    }

    public void agregar(ActionEvent event){
    	reiniciarValoresFormPatrocinador();
		limpiarTablaPatrocinador();
		limpiarInputs();
		bloquearBotones();
    }

    public void consultar(ActionEvent event) throws SQLException, Exception{
    	//String[][] infoPatrocinios = gestor.patrocinadorConsultarPatrocinios(idPatrocinadorSeleccionado);
    	String[][] infoPintores = gestor.pintorConsultarLista();
    	//ventana.mostrarVentanaConsultaPatrocinio("FinanciarPintores", infoPatrocinios, infoPintores);
	}

    public void guardar(ActionEvent event) throws SQLException, Exception{
    	TextField[]inputs = {iCedula, iNombre, iPrimerApellido, iSegundoApellido,
    			iPaisOrigen, iCiudadOrigen};
    	Label[]labels = {lCedula, lNombre, lPrimerApellido, lSegundoApellido,
    			lPaisOrigen, lCiudadOrigen};
    	Boolean[] results = new Boolean[labels.length];

    	for(int i = 0; i < inputs.length; i++){
    		results[i] = ValidacionForm.campoDeTextoNoVacio(inputs[i], labels[i], "Dato requerido!");
    	}

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(results[0] && results[1] && results[2] && results[3] && results[4] && results[5]){
    		if(patrocinadorSeleccionado){
        		lMensaje.setText(gestor.patrocinadorRegistrar(iCedula.getText(), iNombre.getText(), iPrimerApellido.getText(), iSegundoApellido.getText(),
						iPaisOrigen.getText(), iCiudadOrigen.getText(), iFechaFallecimiento.getText()));
        	}else{
    			lMensaje.setText(gestor.patrocinadorModificar(iCedula.getText(), iNombre.getText(), iPrimerApellido.getText(), iSegundoApellido.getText(),
						iPaisOrigen.getText(), iCiudadOrigen.getText(), iFechaFallecimiento.getText()));
    		}

    		listarInformacionPatrocinador();
    		reiniciarValoresFormPatrocinador();
    		limpiarInputs();
    	}
    }

    public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionPatrocinador();
	}

    public void eliminar(ActionEvent event) throws SQLException, Exception{
    	lMensaje.setText(gestor.patrocinadorEliminar(idPatrocinadorSeleccionado));
		listarInformacionPatrocinador();
		reiniciarValoresFormPatrocinador();
		limpiarTablaPatrocinador();
		limpiarInputs();
		bloquearBotones();
		listarInformacionPatrocinador();
	}

    public void financiarPintores(ActionEvent event){
    		
    }

	public void limpiarTablaPatrocinador(){
		seleccionDatoPatrocinador.removeListener(selectorTablaPatrocinador);
		tablaPatrocinador.getItems().clear();
	}


    //LISTAR INFORMACIÓN DE PATROCINADOR
    public void listarInformacionPatrocinador() throws SQLException, Exception{
    	if(listenerPatrocinadorCreado){
    		seleccionDatoPatrocinador.removeListener(selectorTablaPatrocinador);
        	tablaPatrocinador.getItems().clear();
        	agregarListenersTablaPatrocinador();
    	}else{
    		agregarListenersTablaPatrocinador();
    	}

    	String[][] listaPatrocinador = gestor.patrocinadorConsultarLista();

		for(int i = 0; i < listaPatrocinador.length; i++){
			TablaDosDatos fila = new TablaDosDatos (listaPatrocinador[i][0], listaPatrocinador[i][1]);
	    	datosPatrocinador.add(fila);
        }

    }

	 public void reiniciarValoresFormPatrocinador(){
		 lMensaje.setText("");

		 lCedula.setText("");
		 lNombre.setText("");
		 lPrimerApellido.setText("");
		 lSegundoApellido.setText("");
		 lPaisOrigen.setText("");
		 lCiudadOrigen.setText("");
		 lFechaFallecimiento.setText("");

		 patrocinadorSeleccionado = true;

		 btnEliminar.setDisable(true);
	     btnConsultar.setDisable(true);
	 }

    public void limpiarLabels(){
    	lCedula.setText("");
    	lNombre.setText("");
    	lPrimerApellido.setText("");
    	lSegundoApellido.setText("");
    	lPaisOrigen.setText("");
    	lCiudadOrigen.setText("");
    	lFechaFallecimiento.setText("");

	 }

    public void limpiarInputs(){
    	iCedula.clear();
    	iNombre.clear();
    	iPrimerApellido.clear();
    	iSegundoApellido.clear();
    	iPaisOrigen.clear();
    	iCiudadOrigen.clear();
    	iFechaFallecimiento.clear();

	 }

	 public void bloquearInputs(){
		 iCedula.setDisable(true);
		 iNombre.setDisable(true);
		 iPrimerApellido.setDisable(true);
		 iSegundoApellido.setDisable(true);
		 iPaisOrigen.setDisable(true);
		 iCiudadOrigen.setDisable(true);
		 iFechaFallecimiento.setDisable(true);

	 }

	 public void desbloquearInputs(){
		 iCedula.setDisable(false);
		 iNombre.setDisable(false);
		 iPrimerApellido.setDisable(false);
		 iSegundoApellido.setDisable(false);
		 iPaisOrigen.setDisable(false);
		 iCiudadOrigen.setDisable(false);
		 iFechaFallecimiento.setDisable(false);

	 }

	 public void bloquearBotones(){
		 btnConsultar.setDisable(true);
		 btnEliminar.setDisable(true);
		 btnFinanciarPintores.setDisable(true);
	 }

	 public void desbloquearBotones(){
		 //btnConsultar.setDisable(false);
		 btnEliminar.setDisable(false);
		 //btnFinanciarPintores.setDisable(false);
	 }
}
