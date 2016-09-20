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

public class Escuela implements Initializable,  ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

    //DEFINIR TABLA DE CLIENTES
	@FXML
    private TableView<TablaDosDatos> tablaEscuelas;
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
    private Label lPaisFundacion;
    @FXML
    private Label lFechaFundacion;
    @FXML
    private Label lDescripcion;


    //DEFINIR FORMULARIO DEL PINTOR
    @FXML
    private TextField iCedulaJuridica;
    @FXML
    private TextArea iNombre;
    @FXML
    private TextField iPaisFundacion;
    @FXML
    private TextField iFechaFundacion;
    @FXML
    private TextArea iDescripcion;

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


    //DEFINIR OVSERVABLELIST DE ESCUELA
    private ObservableList<TablaDosDatos> datosEscuelas = FXCollections.observableArrayList();
    private ObservableList<TablaDosDatos> seleccionDatoEscuela;
    private boolean escuelaSeleccionado = true;	// true : REGISTRO
										 		// false: MODIFICACIÓN
    private String idEscuelaSeleccionado;// ID
    private boolean listenerEscuelaCreado = true;


    //DEFINIR CONSTRUCTOR
    public Escuela(){
    	gestor = new Gestor();
    }


    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
   	@Override
   	public void initialize(URL arg0, ResourceBundle arg1) {
   		cCedulaJuridica.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rCedula"));
       	cNombre.setCellValueFactory(new PropertyValueFactory<TablaDosDatos, String>("rNombre"));
       	tablaEscuelas.setItems(datosEscuelas);
       	seleccionDatoEscuela = tablaEscuelas.getSelectionModel().getSelectedItems();
   	}

    //DEFINIR LISTENER DE LA TABLA CLIENTE
    private final ListChangeListener<TablaDosDatos> selectorTablaEscuela = new ListChangeListener<TablaDosDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaDosDatos > c) {
            try {
				extraerIDEscuelaSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

  //EXTRAER ID DEL CLIENTE Y CARGAR SUS DATOS EN LOS CAMPOS DE TEXTO
    private void extraerIDEscuelaSeleccionado() throws SQLException, Exception {
    	escuelaSeleccionado = false;
    	final TablaDosDatos tabla = getTablaEscuelaSeleccionada();
    	idEscuelaSeleccionado = tabla.getRCedula();
        cargarDatosEscuelaSeleccionado(gestor.escuelaConsultarXCedulaJuridica(idEscuelaSeleccionado));
        
        desbloquearBotones();
        lMensaje.setText("");
    }

 // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosEscuelaSeleccionado(String[] pinfo){
        iCedulaJuridica.setText(pinfo[0]);
        iNombre.setText(pinfo[1]);
        iPaisFundacion.setText(pinfo[2]);
        iFechaFundacion.setText(pinfo[3]);
        iDescripcion.setText(pinfo[4]);
    }

    //SELECCIONAR UN CLIENTE DE LA TABLA
    public TablaDosDatos getTablaEscuelaSeleccionada() {
        if (tablaEscuelas != null) {
			List<TablaDosDatos> tabla = tablaEscuelas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaDosDatos competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }


    //AGREGAR LISTENER A TABLA PINTORES
    private void agregarListenersTablaEscuela(){
    	seleccionDatoEscuela = tablaEscuelas.getSelectionModel().getSelectedItems();
    	seleccionDatoEscuela.addListener(selectorTablaEscuela);
    	listenerEscuelaCreado = true;
    }

  //DEFINIR ACCIÓN DE LOS BOTONES
    public void atras(ActionEvent event){
    	limpiarTablaEscuela();
    	limpiarInputs();
		limpiarLabels();
		bloquearBotones();
    	ventana.mostrarVentana("Principal");
    }

    public void agregar(ActionEvent event){
    	reiniciarValoresFormEscuela();
		limpiarTablaEscuela();
		limpiarInputs();
		bloquearBotones();
    }

    public void consultar(ActionEvent event) throws SQLException, Exception{
    	String[] infoEscuela = gestor.escuelaConsultarXCedulaJuridica(idEscuelaSeleccionado);
		ventana.mostrarVentanaConsultaEscuela("Consulta", infoEscuela);
	}

    public void guardar(ActionEvent event) throws SQLException, Exception{
    	
		boolean cedulaJuridica = ValidacionForm.campoDeTextoNoVacio(iCedulaJuridica, lCedulaJuridica, "Dato requerido!");
		boolean nombre = ValidacionForm.areaDeTextoNoVacio(iNombre, lNombre, "Dato requerido!");
		boolean paisFundada = ValidacionForm.campoDeTextoNoVacio(iPaisFundacion, lPaisFundacion, "Dato requerido!");
		boolean fechaFundacion = ValidacionForm.campoDeTextoNoVacio(iFechaFundacion, lFechaFundacion, "Dato requerido!");
		boolean descripcion = ValidacionForm.areaDeTextoNoVacio(iDescripcion, lDescripcion, "Dato requerido!");
		
    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(cedulaJuridica && nombre && paisFundada && fechaFundacion && descripcion){
    		if(escuelaSeleccionado){
        		lMensaje.setText(gestor.escuelaRegistrar(iCedulaJuridica.getText(), iNombre.getText(), iPaisFundacion.getText(), iFechaFundacion.getText(), iDescripcion.getText()));
        	}else{
    			lMensaje.setText(gestor.escuelaModificar(iCedulaJuridica.getText(), iNombre.getText(), iPaisFundacion.getText(), iFechaFundacion.getText(), iDescripcion.getText()));
    		}
        	
    		listarInformacionEscuelas();
    		reiniciarValoresFormEscuela();
    		limpiarInputs();
    	}
    }

    public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionEscuelas();
	}

    public void eliminar(ActionEvent event) throws SQLException, Exception{
    	lMensaje.setText(gestor.escuelaEliminar(idEscuelaSeleccionado));
		listarInformacionEscuelas();
		reiniciarValoresFormEscuela();
		limpiarTablaEscuela();
		limpiarInputs();
		bloquearBotones();
		listarInformacionEscuelas();
	}

  //LISTAR INFORMACIÓN DE CLIENTES
    public void listarInformacionEscuelas() throws SQLException, Exception{
    	if(listenerEscuelaCreado){
    		seleccionDatoEscuela.removeListener(selectorTablaEscuela);
        	tablaEscuelas.getItems().clear();
        	agregarListenersTablaEscuela();
    	}else{
    		agregarListenersTablaEscuela();
    	}
    	
    	String[][] listaEscuelas = gestor.escuelaConsultarLista();

    	for(int i = 0; i < listaEscuelas.length; i++){
			TablaDosDatos fila = new TablaDosDatos(listaEscuelas[i][0], listaEscuelas[i][1]);
	    	datosEscuelas.add(fila);
        }
    	
    }

    public void reiniciarValoresFormEscuela(){
		 lMensaje.setText("");
		 lCedulaJuridica.setText("");
		 lNombre.setText("");
		 lPaisFundacion.setText("");
		 lFechaFundacion.setText("");
		 lDescripcion.setText("");
		 
		 escuelaSeleccionado = true;
		 
		 btnEliminar.setDisable(true);
	     btnConsultar.setDisable(true);
	 }
    
    public void limpiarTablaEscuela(){
		seleccionDatoEscuela.removeListener(selectorTablaEscuela);
		tablaEscuelas.getItems().clear();
	}

    public void limpiarLabels(){
    	lCedulaJuridica.setText("");
    	lNombre.setText("");
    	lPaisFundacion.setText("");
    	lFechaFundacion.setText("");
    	lDescripcion.setText("");
	 }
	 public void limpiarInputs(){
	    	iCedulaJuridica.clear();
	    	iNombre.setText("");
	    	iPaisFundacion.clear();
	    	iFechaFundacion.clear();
	    	iDescripcion.setText("");
	 }

	 public void bloquearInputs(){
	    	iCedulaJuridica.setDisable(true);
	    	iNombre.setDisable(true);
	    	iPaisFundacion.setDisable(true);
	    	iFechaFundacion.setDisable(true);
	    	iDescripcion.setDisable(true);
	 }

	 public void desbloquearInputs(){
		 	iCedulaJuridica.setDisable(false);
	    	iNombre.setDisable(false);
	    	iPaisFundacion.setDisable(false);
	    	iFechaFundacion.setDisable(false);
	    	iDescripcion.setDisable(false);
	 }

	 public void bloquearBotones(){
		 	btnConsultar.setDisable(true);
		 	btnEliminar.setDisable(true);
	 }

	 public void desbloquearBotones(){
		 	btnConsultar.setDisable(false);
		 	btnEliminar.setDisable(false);
	 }
}
