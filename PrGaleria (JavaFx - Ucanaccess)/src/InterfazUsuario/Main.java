package InterfazUsuario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static String principal = "Principal.fxml";
	public static String pintor = "Pintor.fxml";
	public static String pintura = "Pintura.fxml";
	public static String registroLlegada = "RegistroLlegada.fxml";
	public static String patrocinador = "Patrocinador.fxml";
	public static String escuela = "Escuela.fxml";
	public static String galeria = "Galeria.fxml";
	public static String financiarPintores = "FinanciarPintores.fxml";
	public static String elegirMaestros = "ElegirMaestros.fxml";
	public static String adquirirPinturas = "AdquirirPinturas.fxml";

	@Override
	public void start(Stage stage) throws Exception{
		AdminVentanas contenedorPrincipal = new AdminVentanas();
		contenedorPrincipal.cargarVentana("Principal", Main.principal);
		contenedorPrincipal.cargarVentana("Pintor", Main.pintor);
		contenedorPrincipal.cargarVentana("Pintura", Main.pintura);
		contenedorPrincipal.cargarVentana("RegistroLlegada", Main.registroLlegada);
		contenedorPrincipal.cargarVentana("Patrocinador", Main.patrocinador);
		contenedorPrincipal.cargarVentana("Escuela", Main.escuela);
		contenedorPrincipal.cargarVentana("Galeria", Main.galeria);
		contenedorPrincipal.cargarVentana("FinanciarPintores", Main.financiarPintores);
		contenedorPrincipal.cargarVentana("ElegirMaestros", Main.elegirMaestros);
		contenedorPrincipal.cargarVentana("AdquirirPinturas", Main.adquirirPinturas);

		contenedorPrincipal.mostrarVentana("Principal");

		Scene scene = new Scene(contenedorPrincipal);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}