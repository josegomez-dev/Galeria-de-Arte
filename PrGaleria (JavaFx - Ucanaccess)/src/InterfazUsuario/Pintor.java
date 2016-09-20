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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Pintor implements Initializable, ControlVentanas {

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

    //DEFINIR TABLA DE CLIENTES
	@FXML
    private TableView<TablaDosDatos> tablaPintor;
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

  //DEFINIR FORMULARIO DEL PINTOR
    @FXML
    private TextField iCedula;
    @FXML
    private TextField iNombre;
    @FXML
    private TextField iPrimerApellido;
    @FXML
    private TextField iSegundoApellido;
    @FXML
    private TextField iSeudonimo;
    @FXML
    private TextField iNacionalidad;
    @FXML
    private TextField iPaisOrigen;
    @FXML
    private TextField iCiudadOrigen;
    @FXML
    private TextField iFechaNacimiento;
    @FXML
    private TextField iFechaFallecimiento;
    @FXML
    private ComboBox<String> iTipo;
    @FXML
    private ComboBox<String> iEscuela;
    @FXML
    private TextField iBuscar;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnConsultar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnElegirMaestros;
    @FXML
    private Button btnBuscar;

    //DEFINIR COMBOBOX DE TIPOS
    private ObservableList<String> tiposCondicion = FXCollections.observableArrayList(
    		"Famoso", "Muy Conocido", "Poco Conocido"
    );

    //DEFINIR COMBOBOX DE ESCUELAS
    private ObservableList<String> escuelas = FXCollections.observableArrayList();

    //DEFINIR OVSERVABLELIST DE CLIENTE
    private ObservableList<TablaDosDatos> datosPintores = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoPintor;
    private boolean pintorSeleccionado = true;	// true : REGISTRO
										 		// false: MODIFICACIÓN
    private String idPintorSeleccionado;// ID
    private boolean listenerPintorCreado = true;


    //DEFINIR CONSTRUCTOR
    public Pintor(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
   	@Override
   	public void initialize(URL arg0, ResourceBundle arg1) {
   		cCedula.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
       	cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
       	tablaPintor.setItems(datosPintores);
       	seleccionDatoPintor = tablaPintor.getSelectionModel().getSelectedItems();
       	iTipo.setItems(tiposCondicion);
       	bloquearInputs();
   	}

  //DEFINIR LISTENER DE LA TABLA CLIENTE
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

    //EXTRAER ID DEL CLIENTE Y CARGAR SUS DATOS EN LOS CAMPOS DE TEXTO
    private void extraerIDPintorSeleccionado() throws SQLException, Exception {
    	pintorSeleccionado = false;
    	final TablaDosDatos tabla = getTablaPintorSeleccionada();
    	idPintorSeleccionado = tabla.getRCedula();
        cargarDatosPintorSeleccionado(gestor.pintorConsultarXCedula(idPintorSeleccionado));
        obtenerNombresEscuelas();
        desbloquearBotones();
        desbloquearInputs();
        lMensaje.setText("");
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosPintorSeleccionado(String[] pinfo){
    	limpiarLabels();
        iCedula.setText(pinfo[0]);
        iNombre.setText(pinfo[1]);
        iPrimerApellido.setText(pinfo[2]);
        iSegundoApellido.setText(pinfo[3]);
        iSeudonimo.setText(pinfo[4]);
        iNacionalidad.setText(pinfo[5]);
        iPaisOrigen.setText(pinfo[6]);
        iCiudadOrigen.setText(pinfo[7]);
        iFechaNacimiento.setText(pinfo[8]);
        iFechaFallecimiento.setText(pinfo[9]);
        iTipo.setValue(pinfo[10]);
        iEscuela.setValue(pinfo[11]);
    }

    //SELECCIONAR UN CLIENTE DE LA TABLA
    public TablaDosDatos getTablaPintorSeleccionada() {
        if (tablaPintor != null) {
			List<TablaDosDatos> tabla = tablaPintor.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA PINTORES
    private void agregarListenersTablaPintor(){
    	seleccionDatoPintor = tablaPintor.getSelectionModel().getSelectedItems();
    	seleccionDatoPintor.addListener(selectorTablaPintor);
    	listenerPintorCreado = true;
    }

	//DEFINIR ACCIÓN DE LOS BOTONES
    public void atras(ActionEvent event){
    	limpiarTablaPintor();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
		bloquearInputs();
		iBuscar.setDisable(true);
    	ventana.mostrarVentana("Principal");
    }

    public void agregar(ActionEvent event){
    	reiniciarValoresFormPintor();
		limpiarTablaPintor();
		limpiarInputs();
		bloquearBotones();
		desbloquearInputs();
		iBuscar.setDisable(true);
		obtenerNombresEscuelas();
    }

    public void obtenerNombresEscuelas(){
    	String[][] listaEscuelas= gestor.escuelaConsultarLista();
    	String[] nombres = new String[listaEscuelas.length];
    	for(int i=0; i<listaEscuelas.length; i++){
    		nombres[i] = listaEscuelas[i][1];
		}
    	escuelas.addAll(nombres);
    	iEscuela.setItems(escuelas);
    }

    public void consultar(ActionEvent event) throws SQLException, Exception{
    	String[] infoPintor = gestor.pintorConsultarXCedula(idPintorSeleccionado);
//		String[][] infoPinturasCreadas;
//		String[] infoPatrocinadores = gestor.pintorConsultarXCedulaPatrocinador(idPintorSeleccionado);
//		String[] infoMaestros;
//    	ventana.mostrarVentanasConsultaPintor("Consulta", infoPintor, infoPatrocinadores);

	}

    public void guardar(ActionEvent event) throws SQLException, Exception{

    	TextField[]inputs = {iCedula, iNombre, iPrimerApellido, iSegundoApellido, iSeudonimo, iNacionalidad,
    			iPaisOrigen, iCiudadOrigen, iFechaNacimiento};
    	Label[]labels = {lCedula, lNombre, lPrimerApellido, lSegundoApellido, lSeudonimo, lNacionalidad,
    			lPaisOrigen, lCiudadOrigen, lFechaNacimiento};
    	Boolean[] results = new Boolean[labels.length]; // cant 10

    	int i = 0;
    	for(TextField a : inputs){
    		results[i] = ValidacionForm.campoDeTextoNoVacio(inputs[i], labels[i], "Dato requerido!");
        	i++;
    	}

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(results[0] && results[1] && results[2] && results[3] && results[4] && results[5] && results[6] && results[7] && results[8]){
    		if(pintorSeleccionado){
        		lMensaje.setText(gestor.pintorRegistrar(iCedula.getText(), iNombre.getText(), iPrimerApellido.getText(), iSegundoApellido.getText(),
						iSeudonimo.getText(), iNacionalidad.getText(), iPaisOrigen.getText(), iCiudadOrigen.getText(), iFechaNacimiento.getText(),
						iFechaFallecimiento.getText(), iTipo.getValue(), iEscuela.getSelectionModel().getSelectedItem()));
        	}else{
    			lMensaje.setText(gestor.pintorModificar(iCedula.getText(), iNombre.getText(), iPrimerApellido.getText(), iSegundoApellido.getText(),
						iSeudonimo.getText(), iNacionalidad.getText(), iPaisOrigen.getText(), iCiudadOrigen.getText(), iFechaNacimiento.getText(),
						iFechaFallecimiento.getText(), iTipo.getValue(), iEscuela.getSelectionModel().getSelectedItem()));
        	}

    		listarInformacionPintores();
    		reiniciarValoresFormPintor();
    		limpiarInputs();
    		bloquearInputs();
		}
    }

    public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionPintores();
	}

    public void eliminar(ActionEvent event) throws SQLException, Exception{
    	lMensaje.setText(gestor.pintorEliminar(idPintorSeleccionado));
		listarInformacionPintores();
		reiniciarValoresFormPintor();
		limpiarTablaPintor();
		limpiarInputs();
		bloquearBotones();
		bloquearInputs();
		listarInformacionPintores();
	}

    public void elegirMaestros(ActionEvent event) throws SQLException, Exception{
    	ventana.mostrarVentanaElegirMaestros("Elección de maestros", idPintorSeleccionado);
	}

    public void inscribirPinturas(ActionEvent event) throws SQLException, Exception{
    	seleccionDatoPintor.removeListener(selectorTablaPintor);
    	tablaPintor.getItems().clear();
    	reiniciarValoresFormPintor();
    	limpiarTablaPintor();
    	limpiarInputs();
    	bloquearInputs();
    	limpiarLabels();
    	ventana.mostrarVentana("Pintura");
	}

    public void buscar(ActionEvent event) throws SQLException, Exception{
    	if(listenerPintorCreado){
    		seleccionDatoPintor.removeListener(selectorTablaPintor);
        	tablaPintor.getItems().clear();
        	agregarListenersTablaPintor();
    	}else{
    		agregarListenersTablaPintor();
    	}
    	if(!iBuscar.getText().isEmpty()){
    		String[] infoPintura = gestor.pintorConsultarXNombre(iBuscar.getText());
        	if(infoPintura != null){
        		datosPintores.add(new TablaDosDatos(infoPintura[0], infoPintura[1]));
        	}
    	}else{
    		listarInformacionPintores();
    	}
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
		bloquearInputs();
    }

	//LISTAR INFORMACIÓN DE PINTORES
    public void listarInformacionPintores() throws SQLException, Exception{
    	if(listenerPintorCreado){
    		seleccionDatoPintor.removeListener(selectorTablaPintor);
        	tablaPintor.getItems().clear();
        	agregarListenersTablaPintor();
    	}else{
    		agregarListenersTablaPintor();
    	}
    	String[][] listaPintores = gestor.pintorConsultarLista();
    	if(listaPintores != null){
    		for(int i = 0; i < listaPintores.length; i++){
    			TablaDosDatos fila = new TablaDosDatos(listaPintores[i][0], listaPintores[i][1]);
    	    	datosPintores.add(fila);
            }	
    	}
    }

	public void limpiarTablaPintor(){
		seleccionDatoPintor.removeListener(selectorTablaPintor);
		tablaPintor.getItems().clear();
	}

	 public void reiniciarValoresFormPintor(){
		 pintorSeleccionado = true;
		 idPintorSeleccionado = null;
		 btnEliminar.setDisable(true);
	     btnConsultar.setDisable(true);
	     btnElegirMaestros.setDisable(true);
	 }

    public void limpiarLabels(){
    	lMensaje.setText("");
    	lCedula.setText("");
    	lNombre.setText("");
    	lPrimerApellido.setText("");
    	lSegundoApellido.setText("");
    	lSeudonimo.setText("");
    	lNacionalidad.setText("");
    	lPaisOrigen.setText("");
    	lCiudadOrigen.setText("");
    	lFechaNacimiento.setText("");
    	lFechaFallecimiento.setText("");
    }

	 public void limpiarInputs(){
    	iCedula.clear();
    	iNombre.clear();
    	iPrimerApellido.clear();
    	iSegundoApellido.clear();
    	iSeudonimo.clear();
    	iNacionalidad.clear();
    	iPaisOrigen.clear();
    	iCiudadOrigen.clear();
    	iFechaNacimiento.clear();
    	iFechaFallecimiento.clear();

    	iTipo.setValue("-- Seleccione");
    	iEscuela.setValue("-- Seleccione");
    	iBuscar.setText("");
	 }

	public void bloquearInputs(){
		iCedula.setDisable(true);
		iNombre.setDisable(true);
		iPrimerApellido.setDisable(true);
		iSegundoApellido.setDisable(true);
		iSeudonimo.setDisable(true);
		iNacionalidad.setDisable(true);
		iPaisOrigen.setDisable(true);
		iCiudadOrigen.setDisable(true);
		iFechaNacimiento.setDisable(true);
		iFechaFallecimiento.setDisable(true);
		iTipo.setDisable(true);
		iEscuela.setDisable(true);
	}

	public void desbloquearInputs(){
		iCedula.setDisable(false);
		iNombre.setDisable(false);
		iPrimerApellido.setDisable(false);
		iSegundoApellido.setDisable(false);
		iSeudonimo.setDisable(false);
		iNacionalidad.setDisable(false);
		iPaisOrigen.setDisable(false);
		iCiudadOrigen.setDisable(false);
		iFechaNacimiento.setDisable(false);
		iFechaFallecimiento.setDisable(false);
		iTipo.setDisable(false);
		iEscuela.setDisable(false);
	}

	 public void bloquearBotones(){
		 btnConsultar.setDisable(true);
		 btnEliminar.setDisable(true);
		 btnElegirMaestros.setDisable(true);
	 }

	 public void desbloquearBotones(){
		 btnConsultar.setDisable(false);
		 btnEliminar.setDisable(false);
		 btnElegirMaestros.setDisable(false);
	 }
}
