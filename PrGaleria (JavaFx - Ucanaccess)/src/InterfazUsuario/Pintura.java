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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Pintura implements Initializable, ControlVentanas {

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PINTURAS
	@FXML
    private TableView<TablaDosDatos> tablaPintura;
    @FXML
    private TableColumn<TablaDosDatos, String> cCodigo;
    @FXML
    private TableColumn<TablaDosDatos, String> cNombre;


    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
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

    //DEFINIR FORMULARIO DEL PINTURA
    @FXML
    private TextField iNombre;
    @FXML
    private TextField iAlto;
    @FXML
    private TextField iAncho;
    @FXML
    private TextField iFechaCreacion;
    @FXML
    private TextField iAniosDuracion;
    @FXML
    private TextField iMesesDuracion;
    @FXML
    private TextField iDiasDuracion;
    @FXML
    private TextField iTecnica;
    @FXML
    private CheckBox iEstado;
    @FXML
    private TextArea iHistoria;
    @FXML
    private TextField iBuscarNombre;
    @FXML
    private ComboBox<String> iPintor;

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
    private Button btnBuscar;
    @FXML
    private Button btnConsultar;

    private ObservableList<String> pintores = FXCollections.observableArrayList();

    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaDosDatos> datosPinturas = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoPintura;
    private boolean pinturaSeleccionado = true;// true : REGISTRO
 											// false: MODIFICACIÓN
    private String idPinturaSeleccionado;// ID PINTURA
    private boolean listenerPinturaCreado = false;

    //DEFINIR CONSTRUCTOR
    public Pintura(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cCodigo.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
	    cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
	    tablaPintura.setItems(datosPinturas);
    	seleccionDatoPintura = tablaPintura.getSelectionModel().getSelectedItems();
    	bloquearBotones();
    	bloquearInputs();
	}

	//DEFINIR LISTENER DE LA TABLA PINTURAS
    private final ListChangeListener<TablaDosDatos> selectorTablaPintura = new ListChangeListener<TablaDosDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaDosDatos > c) {
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
    	pinturaSeleccionado = false;
    	final TablaDosDatos tabla = getTablaPinturaSeleccionada();
        idPinturaSeleccionado = tabla.getRCedula();
        cargarDatosPinturaSeleccionada(gestor.pinturaConsultarXNumero(Integer.parseInt(idPinturaSeleccionado)));
        desbloquearBotones();
        desbloquearInputs();
        obtenerNombresPintores();
        lMensaje.setText("");
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosPinturaSeleccionada(String[] pinfo){
    	limpiarLabels();
        iNombre.setText(pinfo[0]);
        iAlto.setText(pinfo[1]);
        iAncho.setText(pinfo[2]);
        iFechaCreacion.setText(pinfo[3]);
        iAniosDuracion.setText(pinfo[4]);
        iMesesDuracion.setText(pinfo[5]);
        iDiasDuracion.setText(pinfo[6]);
        iTecnica.setText(pinfo[7]);
        iEstado.setText(pinfo[8]);
        if(!pinfo[8].equals("Sí")){
        	iEstado.selectedProperty().set(false);
        }else{
        	iEstado.selectedProperty().set(true);
        }
        iHistoria.setText(pinfo[9]);
        iPintor.setValue(pinfo[10]);
    }


    //SELECCIONAR UNA PINTURA DE LA TABLA
    public TablaDosDatos getTablaPinturaSeleccionada() {
        if (tablaPintura != null) {
			List<TablaDosDatos> tabla = tablaPintura.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA PINTURAS
    private void agregarListenersTablaPintura(){
    	seleccionDatoPintura = tablaPintura.getSelectionModel().getSelectedItems();
    	seleccionDatoPintura.addListener(selectorTablaPintura);
    	listenerPinturaCreado = true;
    }


  //DEFINIR ACCIÓN DE LOS BOTONES
    public void atras(ActionEvent event){
    	lMensaje.setText("");
    	limpiarTablaPintura();
    	reiniciarValores();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
		bloquearInputs();
		ventana.mostrarVentana("Galeria");
    }

    public void agregar(ActionEvent event){
    	lMensaje.setText("");
    	reiniciarValores();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
		desbloquearInputs();
		obtenerNombresPintores();
    }

    public void obtenerNombresPintores(){
    	String[][] listaPintores = gestor.pintorConsultarLista();
    	String[] nombres = new String[listaPintores.length];
    	for(int i=0; i<listaPintores.length; i++){
    		nombres[i] = listaPintores[i][1];
		}
    	pintores.addAll(nombres);
    	iPintor.setItems(pintores);
    }

    public void consultar(ActionEvent event) throws SQLException, Exception{
    	String[] infoPintura = gestor.pinturaConsultarXNumero(Integer.parseInt(idPinturaSeleccionado));
    	String[] infoPinturaLlegada = gestor.pinturaConsultarLlegadaAGaleria(Integer.parseInt(idPinturaSeleccionado));
    	ventana.mostrarVentanaConsultaPintura("Consulta de pintura", infoPintura, infoPinturaLlegada);
	}

    public void guardar(ActionEvent event) throws SQLException, Exception{
    	String mensaje = "Dato requerido!";

    	Boolean nombre = ValidacionForm.campoDeTextoNoVacio(iNombre, lNombre, mensaje);
    	Boolean alto = ValidacionForm.campoTipoNumero(iAlto, lDimensiones, mensaje);
    	Boolean ancho = ValidacionForm.campoTipoNumero(iAncho, lDimensiones, mensaje);
    	Boolean fecha = ValidacionForm.campoTipoFecha(iFechaCreacion, lFechaCreacion, mensaje);
    	Boolean anios = ValidacionForm.campoTipoNumero(iAniosDuracion);
    	Boolean meses = ValidacionForm.campoTipoNumero(iMesesDuracion);
    	Boolean dias = ValidacionForm.campoTipoNumero(iDiasDuracion);
    	Boolean tecnica = ValidacionForm.campoDeTextoNoVacio(iTecnica, lTecnica, mensaje);
    	Boolean duracion = false;

    	if(anios || meses || dias){
    		duracion = true;
    	}else{
    		lDuracionCreacion.setText(mensaje);
    	}

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(nombre && alto && ancho && fecha && duracion && tecnica){
    		if(pinturaSeleccionado){
				lMensaje.setText(gestor.pinturaRegistrar(iNombre.getText(), Double.parseDouble(iAlto.getText()),
															Double.parseDouble(iAncho.getText()), iFechaCreacion.getText(),
															Integer.parseInt(iAniosDuracion.getText()),
															Integer.parseInt(iMesesDuracion.getText()),
															Integer.parseInt(iDiasDuracion.getText()),
															iTecnica.getText(), iEstado.getText(), iHistoria.getText(),
															iPintor.getSelectionModel().getSelectedItem()));
    		}else{
    			lMensaje.setText(gestor.pinturaModificar(Integer.parseInt(idPinturaSeleccionado), iNombre.getText(),
    														Double.parseDouble(iAlto.getText()),
									    					Double.parseDouble(iAncho.getText()), iFechaCreacion.getText(),
									    					Integer.parseInt(iAniosDuracion.getText()),
															Integer.parseInt(iMesesDuracion.getText()),
															Integer.parseInt(iDiasDuracion.getText()),
															iTecnica.getText(), iEstado.getText(), iHistoria.getText(),
															iPintor.getSelectionModel().getSelectedItem()));
    		}
    		listarInformacionPinturas();
    		limpiarInputs();
    		limpiarLabels();
    		bloquearBotones();
    		bloquearInputs();
		}
    }

    public void listar(ActionEvent event) throws SQLException, Exception{
    	reiniciarValores();
		listarInformacionPinturas();
		bloquearBotones();
		lMensaje.setText("");
	}

    public void eliminar(ActionEvent event) throws SQLException, Exception{
    	lMensaje.setText(gestor.pinturaEliminar(Integer.parseInt(idPinturaSeleccionado)));
    	reiniciarValores();
    	listarInformacionPinturas();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
	}

    public void buscar(ActionEvent event)throws SQLException, Exception{
    	if(listenerPinturaCreado){
    		seleccionDatoPintura.removeListener(selectorTablaPintura);
        	tablaPintura.getItems().clear();
        	agregarListenersTablaPintura();
    	}else{
    		agregarListenersTablaPintura();
    	}
    	if(!iBuscarNombre.getText().isEmpty()){
    		String[] infoPintura = gestor.pinturaConsultarXNombre(iBuscarNombre.getText());
        	if(infoPintura != null){
        		datosPinturas.add(new TablaDosDatos(infoPintura[0], infoPintura[1]));
        	}
    	}else{
			listarInformacionPinturas();
    	}
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
    }

    //LISTAR INFORMACIÓN DE PINTURAS
    public void listarInformacionPinturas() throws SQLException, Exception{
    	if(listenerPinturaCreado){
    		seleccionDatoPintura.removeListener(selectorTablaPintura);
        	tablaPintura.getItems().clear();
        	agregarListenersTablaPintura();
    	}else{
    		agregarListenersTablaPintura();
    	}
    	String[][] listaPinturas = gestor.pinturaConsultarLista();
		for(int i = 0; i < listaPinturas.length; i++){
			TablaDosDatos fila = new TablaDosDatos(listaPinturas[i][0], listaPinturas[i][1]);
	    	datosPinturas.add(fila);
        }
    }

    //CAMBIAR EL TEXTO DEL CHECKBOX A ACTIVO E INACTIVO
    public void cambiarTextoCheckBox(ActionEvent event){
    	if(!iEstado.isSelected()){
    		iEstado.setText("No");
    	}else{
    		iEstado.setText("Sí");
    	}
	}

    //LIMPIEZA EN LA PANTALLA
    public void limpiarTablaPintura(){
		seleccionDatoPintura.removeListener(selectorTablaPintura);
		tablaPintura.getItems().clear();
	}

    public void limpiarLabels(){
    	lNombre.setText("");
    	lDimensiones.setText("");
    	lFechaCreacion.setText("");
    	lDuracionCreacion.setText("");
    	lTecnica.setText("");
	 }

    public void limpiarInputs(){
    	iNombre.clear();
    	iAlto.clear();
    	iAncho.clear();
    	iFechaCreacion.clear();
    	iAniosDuracion.setText("0");
    	iMesesDuracion.setText("0");
    	iDiasDuracion.setText("0");
    	iTecnica.clear();
    	iHistoria.clear();
    	iEstado.setSelected(true);
    	iEstado.setText("Sí");
    	iPintor.getSelectionModel().clearSelection();
	 }

    public void bloquearInputs(){
    	iNombre.setDisable(true);
    	iAlto.setDisable(true);
    	iAncho.setDisable(true);
    	iFechaCreacion.setDisable(true);
    	iAniosDuracion.setDisable(true);
    	iMesesDuracion.setDisable(true);
    	iDiasDuracion.setDisable(true);
    	iTecnica.setDisable(true);
    	iHistoria.setDisable(true);
    	iEstado.setDisable(true);
    	iPintor.setDisable(true);
    }

    public void desbloquearInputs(){
    	iNombre.setDisable(false);
    	iAlto.setDisable(false);
    	iAncho.setDisable(false);
    	iFechaCreacion.setDisable(false);
    	iAniosDuracion.setDisable(false);
    	iMesesDuracion.setDisable(false);
    	iDiasDuracion.setDisable(false);
    	iTecnica.setDisable(false);
    	iHistoria.setDisable(false);
    	iEstado.setDisable(false);
    	iPintor.setDisable(false);
    }

	 public void bloquearBotones(){
		 btnConsultar.setDisable(true);
		 btnEliminar.setDisable(true);
	 }

	 public void desbloquearBotones(){
		 btnConsultar.setDisable(false);
		 btnEliminar.setDisable(false);
	 }

	 public void reiniciarValores(){
		 pinturaSeleccionado = true;
		 idPinturaSeleccionado = null;
	 }

}
