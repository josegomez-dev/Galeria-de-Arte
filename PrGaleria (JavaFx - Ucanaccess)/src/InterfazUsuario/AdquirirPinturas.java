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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdquirirPinturas implements Initializable{

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE ADQUISICION PINTURAS
	@FXML
    private TableView<TablaTresDatos> tablaAdquisicionPinturas;
    @FXML
    private TableColumn<TablaTresDatos, String> cCodigo;
    @FXML
    private TableColumn<TablaTresDatos, String> cNombre;
    @FXML
    private TableColumn<TablaTresDatos, String> cExposicion;


	//DEFINIR TABLA DE PINTURAS
	@FXML
    private TableView<TablaTresDatos> tablaPinturas;
    @FXML
    private TableColumn<TablaTresDatos, String> cCodigo2;
    @FXML
    private TableColumn<TablaTresDatos, String> cNombre2;
    @FXML
    private TableColumn<TablaTresDatos, String> cTecnica;


    //DEFINIR FORMULARIO DEL GALERIA
    @FXML
    private TextField iBuscarNombre;
    @FXML
    private TextField iFecha1;
    @FXML
    private TextField iFecha2;
    @FXML
    private CheckBox iExposicion;


    //DEFINIR BOTONES
    @FXML
    private Button btnQuitar;
    @FXML
    private Button btnAdquirir;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnGuardar;


    //DEFINIR OVSERVABLELIST ADQUISICION PINTURAS
    private ObservableList<TablaTresDatos> datosPinturasAdquiridas = FXCollections.observableArrayList();
    private ObservableList<TablaTresDatos> seleccionDatoPinturaAdquirida;
    private String idpinturaAdquiridaSeleccionado;// ID PINTURA ADQUIRIDA
    private boolean listenerPinturaAdquiridaCreado = false;

    //DEFINIR OVSERVABLELIST PINTURAS
    private ObservableList<TablaTresDatos> datosPinturas = FXCollections.observableArrayList();
    private ObservableList<TablaTresDatos> seleccionDatoPintura;
    private String idPinturaSeleccionado;// ID PINTURA
    private boolean listenerPinturaCreado = false;

    //CÉDULA DE LA GALERÍA A QUE SE LE ASIGNAN LAS PINTURAS
    private String cedulaGaleria;

    //DEFINIR CONSTRUCTOR
    public AdquirirPinturas(){
    	gestor = new Gestor();
    }

    //INICIALIZAR CONTROLADOR
	public void initialize(URL url, ResourceBundle rb) {
		cCodigo.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rCedula"));
	    cNombre.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rNombre"));
	    tablaAdquisicionPinturas.setItems(datosPinturasAdquiridas);
    	seleccionDatoPinturaAdquirida = tablaAdquisicionPinturas.getSelectionModel().getSelectedItems();

		cCodigo2.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rCedula"));
	    cNombre2.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rNombre"));
	    cTecnica.setCellValueFactory(new PropertyValueFactory<TablaTresDatos, String>("rTipo"));
	    tablaPinturas.setItems(datosPinturas);
    	seleccionDatoPintura = tablaPinturas.getSelectionModel().getSelectedItems();

    	try {
    		listarPinturasNoAdquiridas();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//DEFINIR LISTENER DE LA TABLA ADQUIRIR PINTURAS
    private final ListChangeListener<TablaTresDatos> selectorTablaPinturaAdquirida = new ListChangeListener<TablaTresDatos>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaTresDatos > c) {
            try {
				extraerNumPinturaAdquiridaSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

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

    //EXTRAER ID DE LA ADQUISICION PINTURA
    private void extraerNumPinturaAdquiridaSeleccionado() throws SQLException, Exception {
    	final TablaTresDatos tabla = getTablaPinturaAdquiridaSeleccionada();
        idpinturaAdquiridaSeleccionado = tabla.getRCedula();
        String expo = gestor.galeriaConsultarExposicionPintura(cedulaGaleria, Integer.parseInt(idpinturaAdquiridaSeleccionado));
        iExposicion.setText(expo);
        if(expo.equals("No")){
        	iExposicion.setSelected(false);
    	}else{
    		iExposicion.setSelected(true);
    	}
    }

    //EXTRAER ID DE LA PINTURA
    private void extraerIDPinturaSeleccionado() throws SQLException, Exception {
    	final TablaTresDatos tabla = getTablaPinturaSeleccionada();
        idPinturaSeleccionado = tabla.getRCedula();
    }

    //SELECCIONAR UNA PINTURA DE LA ADQUISICION PINTURAS
    public TablaTresDatos getTablaPinturaAdquiridaSeleccionada() {
        if (tablaAdquisicionPinturas != null) {
			List<TablaTresDatos> tabla = tablaAdquisicionPinturas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaTresDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //SELECCIONAR UNA PINTURA DE LA PINTUAS
    public TablaTresDatos getTablaPinturaSeleccionada() {
        if (tablaPinturas != null) {
			List<TablaTresDatos> tabla = tablaPinturas.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaTresDatos peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA ADQUISICION PINTURAS
    private void agregarListenersTablaPinturasAdquiridas(){
    	seleccionDatoPinturaAdquirida = tablaAdquisicionPinturas.getSelectionModel().getSelectedItems();
    	seleccionDatoPinturaAdquirida.addListener(selectorTablaPinturaAdquirida);
    	listenerPinturaAdquiridaCreado = true;
    }

    //AGREGAR LISTENER A TABLA PINTURAS
    private void agregarListenersTablaPinturas(){
    	seleccionDatoPintura = tablaPinturas.getSelectionModel().getSelectedItems();
    	seleccionDatoPintura.addListener(selectorTablaPintura);
    	listenerPinturaCreado = true;
    }

    //DEFINIR ACCIÓN DE LOS BOTONES
    public void quitar(ActionEvent event) throws SQLException, Exception{
    	gestor.galeriaQuitarPintura(cedulaGaleria, Integer.parseInt(idpinturaAdquiridaSeleccionado));
    	gestor.galeriaEliminarLlegadaPintura(cedulaGaleria, Integer.parseInt(idpinturaAdquiridaSeleccionado));
    	listarPinturasAdquiridas();
    	listarPinturasNoAdquiridas();
    }

    public void adquirir(ActionEvent event) throws SQLException, Exception{
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("RegistroLlegada.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.setTitle("Registro de llegada");
		dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        RegistroLlegada controller = loader.getController();
        controller.guardarDatos(cedulaGaleria, Integer.parseInt(idPinturaSeleccionado), iExposicion.getText());

        dialogStage.showAndWait();

        listarPinturasAdquiridas();
        listarPinturasNoAdquiridas();
    }

	//LISTAR INFORMACIÓN DE PINTURAS
    public void listarPinturasNoAdquiridas() throws SQLException, Exception{
    	if(listenerPinturaCreado){
    		seleccionDatoPintura.removeListener(selectorTablaPintura);
        	tablaPinturas.getItems().clear();
        	agregarListenersTablaPinturas();
    	}else{
    		agregarListenersTablaPinturas();
    	}
    	String[][] listaPinturas = gestor.pinturaConsultarListaNoAdquiridas();
		for(int i = 0; i < listaPinturas.length; i++){
			TablaTresDatos fila = new TablaTresDatos(listaPinturas[i][0], listaPinturas[i][1], listaPinturas[i][2]);
	    	datosPinturas.add(fila);
        }
    }

    public void listar(ActionEvent event) throws SQLException, Exception{
    	listarPinturasAdquiridas();
    }

    //LISTAR INFORMACIÓN DE PINTURAS ADQUIRIDAS
    public void listarPinturasAdquiridas() throws SQLException, Exception{
    	if(listenerPinturaAdquiridaCreado){
    		seleccionDatoPinturaAdquirida.removeListener(selectorTablaPinturaAdquirida);
    		tablaAdquisicionPinturas.getItems().clear();
        	agregarListenersTablaPinturasAdquiridas();
    	}else{
    		agregarListenersTablaPinturasAdquiridas();
    	}
    	String[][] listaPinturas = gestor.galeriaConsultarPinturasAdquiridas(cedulaGaleria, iFecha1, iFecha2);
		for(int i = 0; i < listaPinturas.length; i++){
			TablaTresDatos fila = new TablaTresDatos(listaPinturas[i][0], listaPinturas[i][1], listaPinturas[i][2]);
			datosPinturasAdquiridas.add(fila);
        }
    }

    public void guardar(ActionEvent event){
    	gestor.galeriaCambiarExposicionPintura(cedulaGaleria, Integer.parseInt(idpinturaAdquiridaSeleccionado), iExposicion.getText());
    	iExposicion.setSelected(false);
    	iExposicion.setText("No");
    }

    //BUSCAR PINTURA POR NOMBRE
    public void buscar(ActionEvent event) throws SQLException, Exception{
    	if(listenerPinturaCreado){
    		seleccionDatoPintura.removeListener(selectorTablaPintura);
        	tablaPinturas.getItems().clear();
        	agregarListenersTablaPinturas();
    	}else{
    		agregarListenersTablaPinturas();
    	}
    	if(!iBuscarNombre.getText().isEmpty()){
    		String[] infoPintura = gestor.pinturaNoAdquiridaConsultarXNombre(iBuscarNombre.getText());
        	if(infoPintura != null){
        		datosPinturas.add(new TablaTresDatos(infoPintura[0], infoPintura[1], infoPintura[2]));
        	}
    	}else{
    		listarPinturasNoAdquiridas();
    	}
    }

    //CAMBIAR EL TEXTO DEL CHECKBOX A ACTIVO E INACTIVO
    public void cambiarTextoCheckBox(ActionEvent event){
    	if(!iExposicion.isSelected()){
    		iExposicion.setText("No");
    	}else{
    		iExposicion.setText("Sí");
    	}
	}

    public void guardarCedulaGaleria(String pcedula){
    	cedulaGaleria = pcedula;
    }
}