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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Galeria implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE GALERIA
	@FXML
    private TableView<TablaDosDatos> tablaGalerias;
    @FXML
    private TableColumn<TablaDosDatos, String> cCedulaJuridica;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombre;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
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

    //DEFINIR FORMULARIO DEL GALERIA
    @FXML
    private TextField iCedulaJuridica;
    @FXML
    private TextArea iNombre;
    @FXML
    private TextArea iDireccion;
    @FXML
    private TextField iTelefono;
    @FXML
    private TextField iFechaInaugurada;
    @FXML
    private TextArea iEncargado;
    @FXML
    private TextField iMetrosCuadrados;
    @FXML
    private TextField iFechaInicio;
    @FXML
    private TextField iFechaFinal;

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
    private Button btnConsultar;
    @FXML
    private Button btnAdquirirPinturas;
    @FXML
    private Button btnConsultaPinturas;

    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaDosDatos> datosGaleria = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoGaleria;
    private boolean galeriaSeleccionado = true;	// true : REGISTRO
 												// false: MODIFICACIÓN
    private String idGaleriaSeleccionado;// ID GALERIA
    private boolean listenerGaleriaCreado = false;

    //DEFINIR CONSTRUCTOR
    public Galeria(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cCedulaJuridica.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
	    cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
	    tablaGalerias.setItems(datosGaleria);
    	seleccionDatoGaleria = tablaGalerias.getSelectionModel().getSelectedItems();
	}

	//DEFINIR LISTENER DE LA TABLA GALERIAS
    private final ListChangeListener<TablaDosDatos> selectorTablaGaleria = new ListChangeListener<TablaDosDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaDosDatos > c) {
            try {
				extraerIDGaleriaSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };


    //EXTRAER ID DE LA GALERÍA
    private void extraerIDGaleriaSeleccionado() throws SQLException, Exception {
    	galeriaSeleccionado = false;
    	final TablaDosDatos tabla = getTablaGaleriaSeleccionada();
        idGaleriaSeleccionado = tabla.getRCedula();
        cargarDatosGaleriaSeleccionada(gestor.galeriaConsultarXCedula(idGaleriaSeleccionado));
        desbloquearBotones();
        lMensaje.setText("");
    }


    // CARGAR INFORMACIÓN DE LA GALERIA SELECCIONADO DE LA TABLA
    private void cargarDatosGaleriaSeleccionada(String[] pinfo){
    	limpiarLabels();
        iCedulaJuridica.setText(pinfo[0]);
        iNombre.setText(pinfo[1]);
        iDireccion.setText(pinfo[2]);
        iTelefono.setText(pinfo[3]);
        iFechaInaugurada.setText(pinfo[4]);
        iEncargado.setText(pinfo[5]);
        iMetrosCuadrados.setText(pinfo[6]);
    }


    //SELECCIONAR UNA PINTURA DE LA GALERIA
    public TablaDosDatos getTablaGaleriaSeleccionada() {
        if (tablaGalerias != null) {
			List<TablaDosDatos> tabla = tablaGalerias.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }


    //AGREGAR LISTENER A TABLA GALERIAS
    private void agregarListenersTablaGaleria(){
    	seleccionDatoGaleria = tablaGalerias.getSelectionModel().getSelectedItems();
    	seleccionDatoGaleria.addListener(selectorTablaGaleria);
    	listenerGaleriaCreado = true;
    }

    //DEFINIR ACCIÓN DE LOS BOTONES
    public void atras(ActionEvent event){
    	lMensaje.setText("");
    	limpiarTablaGaleria();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
		ventana.mostrarVentana("Principal");
    }

    public void agregar(ActionEvent event){
    	lMensaje.setText("");
    	limpiarTablaGaleria();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
    }

    public void consultar(ActionEvent event) throws SQLException, Exception{
    	String[] infoGaleria = gestor.galeriaConsultarXCedula(idGaleriaSeleccionado);
    	String[][] listaPinturas = gestor.galeriaConsultarPinturasAdquiridas(idGaleriaSeleccionado, iFechaInicio,
    																			iFechaFinal);
    	ventana.mostrarVentanaConsultaGaleria("Consulta de galería", infoGaleria, listaPinturas);
	}

    public void guardar(ActionEvent event) throws SQLException, Exception{
    	Boolean cedula = ValidacionForm.campoTipoCedulaJuridica(iCedulaJuridica, lCedulaJuridica, "Formato no válido!");
    	Boolean nombre = ValidacionForm.areaDeTextoNoVacio(iNombre, lNombre, "Dato requerido!");
    	Boolean direccion = ValidacionForm.areaDeTextoNoVacio(iDireccion, lDireccion, "Dato requerido!");
    	Boolean telefono = ValidacionForm.campoTipoTelefono(iTelefono, lTelefono, "Formato no válido!");
    	Boolean fecha = ValidacionForm.campoTipoFecha(iFechaInaugurada, lFechaInaugurada, "Formato no válido!");
    	Boolean encargado = ValidacionForm.areaDeTextoNoVacio(iEncargado, lEncargado, "Dato requerido!");
    	Boolean m2 = ValidacionForm.campoDeTextoNoVacio(iMetrosCuadrados, lMetrosCuadrados, "Dato requerido!");

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(cedula && nombre && direccion && telefono && fecha && encargado && m2){
    		if(galeriaSeleccionado){
				lMensaje.setText(gestor.galeriaRegistrar(iCedulaJuridica.getText(), iNombre.getText(), iDireccion.getText(),
															iTelefono.getText(), iFechaInaugurada.getText(),
															iEncargado.getText(),
															Double.parseDouble(iMetrosCuadrados.getText())));
    		}else{
    			lMensaje.setText(gestor.galeriaModificar(iCedulaJuridica.getText(), iNombre.getText(), iDireccion.getText(),
															iTelefono.getText(), iFechaInaugurada.getText(),
															iEncargado.getText(),
															Double.parseDouble(iMetrosCuadrados.getText())));
    		}
    		listarInformacionGalerias();
    		limpiarInputs();
    		limpiarLabels();
    		bloquearBotones();
		}
    }

    public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionGalerias();
		bloquearBotones();
		lMensaje.setText("");
    }
	
    public void admiPinturas(ActionEvent event){
    	ventana.mostrarVentana("Pintura");
    }

    public void eliminar(ActionEvent event) throws SQLException, Exception{
    	lMensaje.setText(gestor.galeriaEliminar(idGaleriaSeleccionado));
    	idGaleriaSeleccionado = null;
    	galeriaSeleccionado = true;
    	listarInformacionGalerias();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
	}

    public void adquirirPinturas(ActionEvent event) throws SQLException, Exception{
    	ventana.mostrarVentanaAdquirirPinturas("Adquisición de pinturas", idGaleriaSeleccionado);
	}

    //LISTAR INFORMACIÓN DE GALERIAS
    public void listarInformacionGalerias() throws SQLException, Exception{
    	if(listenerGaleriaCreado){
    		seleccionDatoGaleria.removeListener(selectorTablaGaleria);
        	tablaGalerias.getItems().clear();
        	agregarListenersTablaGaleria();
    	}else{
    		agregarListenersTablaGaleria();
    	}
    	String[][] listaGalerias = gestor.galeriaConsultarLista();
		for(int i = 0; i < listaGalerias.length; i++){
			TablaDosDatos fila = new TablaDosDatos(listaGalerias[i][0], listaGalerias[i][1]);
	    	datosGaleria.add(fila);
        }
    }

    //LIMPIEZA EN LA PANTALLA
    public void limpiarTablaGaleria(){
		seleccionDatoGaleria.removeListener(selectorTablaGaleria);
		tablaGalerias.getItems().clear();
	}

    public void limpiarLabels(){
    	lCedulaJuridica.setText("");
    	lNombre.setText("");
    	lDireccion.setText("");
    	lTelefono.setText("");
    	lFechaInaugurada.setText("");
    	lEncargado.setText("");
    	lMetrosCuadrados.setText("");
	}

    public void limpiarInputs(){
    	iCedulaJuridica.clear();
    	iNombre.clear();
    	iDireccion.clear();
    	iTelefono.clear();
    	iFechaInaugurada.clear();
    	iEncargado.clear();
    	iMetrosCuadrados.clear();
	}

	 public void bloquearBotones(){
		 btnAdquirirPinturas.setDisable(true);
		 btnConsultar.setDisable(true);
		 btnEliminar.setDisable(true);
	 }

	 public void desbloquearBotones(){
		 btnAdquirirPinturas.setDisable(false);
		 btnConsultar.setDisable(false);
		 btnEliminar.setDisable(false);
	 }
}
