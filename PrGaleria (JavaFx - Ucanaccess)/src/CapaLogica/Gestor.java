package CapaLogica;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import InterfazUsuario.ValidacionForm;
import Multi.MultiEscuela;
import Multi.MultiGaleria;
import Multi.MultiPatrocinador;
import Multi.MultiPatrocinio;
import Multi.MultiPintor;
import Multi.MultiPintura;
import Multi.MultiRegistroLlegada;
import javafx.scene.control.TextField;

public class Gestor {

	/* ***********************************
	 * 		    *** GALERÍAS ***
	 * ***********************************
	 */

	//REGISTRAR GALERÍA
		public String galeriaRegistrar(String pcedula, String pnombre, String pdireccion, String ptelefono, String pfecha,
										String pencargado, double pmetros){
			try{
				new MultiGaleria().crear(pcedula, pnombre, pdireccion, ptelefono, pfecha, pencargado, pmetros);
				return "¡Galería registrada!";
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
		}

	//MODIFICAR GALERÍA
		public String galeriaModificar(String pcedula, String pnombre, String pdireccion, String ptelefono, String pfecha,
										String pencargado, double pmetros){
			try{
				Galeria galeria = new MultiGaleria().consultarXCedula(pcedula);
				if(galeria != null){
					galeria.setNombre(pnombre);
					galeria.setDireccion(pdireccion);
					galeria.setTelefono(ptelefono);
					galeria.setFechaInauguracion(pfecha);
					galeria.setNombreEncargado(pencargado);
					galeria.setMetrosCuadrados(pmetros);

					(new MultiGaleria()).actualizar(galeria);
					return "¡Galería modificada!";
				}else{
					return "¡Galería no registrada!";
				}
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}

	//LISTAR GALERÍAS
		public String[][] galeriaConsultarLista(){
			try{
				ArrayList<Galeria> listaGalerias = (new MultiGaleria()).listar();
				String[][] info = new String[listaGalerias.size()][2];
				int i = 0;
				for(Galeria galeria : listaGalerias){
					info[i][0]= galeria.getCedulaJuridica();
					info[i][1]= galeria.getNombre();
					i++;
				}
				return info;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}

	//CONSULTAR GALERÍA POR CÉDULA
		public String[] galeriaConsultarXCedula(String pcedula){
			try{
				Galeria galeria = new MultiGaleria().consultarXCedula(pcedula);
				if(galeria != null){
					String[] info = new String[]{galeria.getCedulaJuridica(), galeria.getNombre(), galeria.getDireccion(),
													galeria.getTelefono(), galeria.getFechaInauguracion(),
													galeria.getNombreEncargado(), String.valueOf(galeria.getMetrosCuadrados())};
					return info;
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//ELIMINAR GALERÍA
		public String galeriaEliminar(String pcedula){
			try{
				new MultiGaleria().borrar(pcedula);
				return "¡Galería eliminada!";
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}

	//CONSULTAR PINTURAS ADQUIRIDAS POR GALERÍA Y FECHA O RANGO DE FECHAS
		public String[][] galeriaConsultarPinturasAdquiridas(String pcedula, TextField pfecha1, TextField pfecha2){
			try{
				Galeria galeria = (new MultiGaleria()).consultarXCedula(pcedula);
				ArrayList<RegistroLlegada> llegadas = galeria.consultarLlegadasPinturas();
				ArrayList<Pintura> listaPinturas = null;

				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

				Boolean fecha1 = false;
		    	Boolean fecha2 = false;

		    	Date date1 = null;
		    	Date date2 = null;

		    	if(!pfecha1.getText().isEmpty()){
		    		fecha1 = ValidacionForm.campoTipoFecha(pfecha1);
		    		date1 = formato.parse(pfecha1.getText());
		    	}
		    	if(!pfecha2.getText().isEmpty()){
		    		fecha2 = ValidacionForm.campoTipoFecha(pfecha2);
		    		date2 = formato.parse(pfecha2.getText());
		    	}

		    	if(fecha1 || fecha2){
		    		if(fecha1 && fecha2){
		    			listaPinturas = galeria.consultarPinturasAdquiridasXRangoFechas(date1, date2);
		    		}else if(fecha1){
		    			listaPinturas = galeria.consultarPinturasAdquiridasXFecha(date1);
		    		}else if(fecha2){
		    			listaPinturas = galeria.consultarPinturasAdquiridasXFecha(date2);
		    		}
		    	}else{
		    		listaPinturas = galeria.consultarPinturasAdquiridas();
		    	}

				String[][] infoPinturas = new String[listaPinturas.size()][3];
				int i = 0;
				for(Pintura a : listaPinturas){
					infoPinturas[i][0]= String.valueOf(a.getNumero());
					infoPinturas[i][1]= a.getNombre();
					infoPinturas[i][2]= llegadas.get(i).getExposicion();
					i++;
				}
				return infoPinturas;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}


	//REGISTRAR LA LLEGADA DE UNA PINTURA A UNA GALERÍA
		public String galeriaRegistrarLlegadaPintura(String pcedGaleria, int pnumPintura, String pexposicion, String pfecha,
														String pcondicion, String pcondicionActual, double pcostoAdquisicion){
			String resul = "";
			try{
				Galeria galeria = (new MultiGaleria()).consultarXCedula(pcedGaleria);
				if(galeria != null){
					resul = galeria.registrarLlegadaPintura(pnumPintura, pexposicion, pfecha, pcondicion, pcondicionActual,
															pcostoAdquisicion);
				}
			}catch (SQLException e) {
				e.printStackTrace();
				resul = "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				resul = "Excep-error: " + e.getMessage();
			}
			return resul;
		}

	//ELIMINAR EL REGISTRO DE LA LLEGADA DE UNA PINTURA A LA GALERÍA
		public String galeriaEliminarLlegadaPintura(String pcedulaGaleria, int pnumeroPintura){
			try{
				Galeria galeria = (new MultiGaleria()).consultarXCedula(pcedulaGaleria);
				if(galeria != null){
					return galeria.eliminarLlegadaPintura(pnumeroPintura);
				}
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
			return null;
		}

	//QUITAR PINTURAS ADQUIRIDAS DE UNA GALERÍA
		public void galeriaQuitarPintura(String pcedulaGaleria, int pnumeroPintura){
			try {
				Pintura pintura = new MultiPintura().consultarXNumero(pnumeroPintura);
				if(pintura != null){
					(new MultiPintura()).quitarPinturaGaleria(pintura);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	//MODIFICAR LA EXPOSICIÓN DE UNA PINTURA EN UNA GALERÍA
		public void galeriaCambiarExposicionPintura(String pcedulaGaleria, int pnumPintura, String pexposicion){
			try{
				Galeria galeria = (new MultiGaleria()).consultarXCedula(pcedulaGaleria);
				RegistroLlegada llegada = galeria.consultarLlegadaPintura(pnumPintura);

				llegada.setExposicion(pexposicion);
				(new MultiRegistroLlegada()).cambiarExposicion(llegada);
			}catch(SQLException e){
				e.getMessage();
			}catch(Exception e){
				e.getMessage();
			}
		}

	//CONSULTAR LA EXPOSICIÓN DE UNA PINTURA EN UNA GALERÍA
		public String galeriaConsultarExposicionPintura(String pcedulaGaleria, int pnumPintura){
			try{
				Galeria galeria = (new MultiGaleria()).consultarXCedula(pcedulaGaleria);
				RegistroLlegada llegada = galeria.consultarLlegadaPintura(pnumPintura);

				return llegada.getExposicion();
			}catch(SQLException e){
				e.getMessage();
			}catch(Exception e){
				e.getMessage();
			}
			return null;
		}



	/* ***********************************
	 * 		    *** PINTURAS ***
	 * ***********************************
	 */

	//REGISTRAR PINTURA
		public String pinturaRegistrar(String pnombre, double palto, double pancho, String pfecha, int panios, int pmeses,
										int pdias, String ptecnica, String pfamoso, String phistoria, String pnombrePintor) throws SQLException, Exception{
			try{
				String cedPintor = "";
				if(pnombrePintor != null){
					String[][] listaPintores = pintorConsultarLista();
			    	for(int i=0; i<listaPintores.length; i++){
			    		if(pnombrePintor.equals(listaPintores[i][1])){
			    			cedPintor = listaPintores[i][0];
			    		}
					}
				}
				new MultiPintura().crear(pnombre, palto, pancho, pfecha, panios, pmeses, pdias, ptecnica,
											pfamoso, phistoria, cedPintor);
				return "¡Pintura registrada!";
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
		}

	//MODIFICAR PINTURA
		public String pinturaModificar(int pnumero, String pnombre, double palto, double pancho, String pfecha, int panios,
										int pmeses, int pdias, String ptecnica, String pfamoso, String phistoria,
										String pnombrePintor){
			String resul = "";
			try{
				String cedPintor = "";
				String[][] listaPintores = pintorConsultarLista();
		    	for(int i=0; i<listaPintores.length; i++){
		    		if(pnombrePintor.equals(listaPintores[i][1])){
		    			cedPintor = listaPintores[i][0];
		    		}
				}

				Pintura pintura = (new MultiPintura()).consultarXNumero(pnumero);
				if(pintura != null){
					double[] dimensiones = new double[2];
					dimensiones[0] = palto;
					dimensiones[1] = pancho;

					int[] duracion = new int[3];
					duracion[0] = panios;
					duracion[1] = pmeses;
					duracion[2] = pdias;

					if(pintura != null){
					pintura.setNombre(pnombre);
					pintura.setDimensiones(dimensiones);
					pintura.setFechaCreada(pfecha);
					pintura.setDuracionCreada(duracion);
					pintura.setTecnica(ptecnica);
					pintura.setFamosa(pfamoso);
					pintura.setHistoria(phistoria);
					pintura.setCedulaPintor(cedPintor);

					(new MultiPintura()).actualizar(pintura);
					resul = "¡Pintura modificada!";
					}
				}
			}catch(SQLException e){
				resul = "SQL-error: "+e.getMessage();
			}catch(Exception e){
				resul = "Excep-error: "+e.getMessage();
			}
			return resul;
		}

	//LISTAR PINTURAS POR PINTOR
		public String[][] pinturaConsultarListaXPintor(String pcedulaPintor){
			try{
				ArrayList<Pintura> listaPinturas = (new MultiPintor()).consultarXCedula(pcedulaPintor).getObras();
				String[][] info = new String[listaPinturas.size()][3];

				int i = 0;
				for(Pintura pintura : listaPinturas){
					info[i][0]= String.valueOf(pintura.getNumero());
					info[i][1]= pintura.getNombre();
					info[i][2]= pintura.getTecnica();
					i++;
				}
				return info;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}

	//CONSULTAR PINTURA POR NÚMERO
		public String[] pinturaConsultarXNumero(int pnumero){
			try{
				Pintura pintura = new MultiPintura().consultarXNumero(pnumero);
				Galeria galeria = pintura.getGaleria();
				String nombreGaleria = "";

				if(galeria != null){
					nombreGaleria = galeria.getNombre();
				}

				String[] info;

				if(pintura != null){
					Pintor pintor = pintura.getPintor();
					if(pintor != null){
						info = new String[]{pintura.getNombre(), String.valueOf(pintura.getDimensiones()[0]),
								String.valueOf(pintura.getDimensiones()[1]), pintura.getFechaCreada(),
								String.valueOf(pintura.getDuracionCreada()[0]),
								String.valueOf(pintura.getDuracionCreada()[1]),
								String.valueOf(pintura.getDuracionCreada()[2]), pintura.getTecnica(),
								pintura.getFamosa(), pintura.getHistoria(),
								pintor.getNombre() + " " + pintor.getApellido1() + " " + pintor.getApellido2(),
								pintor.getTipo(), nombreGaleria};
					}else{
						info = new String[]{pintura.getNombre(), String.valueOf(pintura.getDimensiones()[0]),
								String.valueOf(pintura.getDimensiones()[1]), pintura.getFechaCreada(),
								String.valueOf(pintura.getDuracionCreada()[0]),
								String.valueOf(pintura.getDuracionCreada()[1]),
								String.valueOf(pintura.getDuracionCreada()[2]), pintura.getTecnica(),
								pintura.getFamosa(), pintura.getHistoria(), "", "", nombreGaleria};
					}

					return info;
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//ELIMINAR PINTURA
		public String pinturaEliminar(int pnumero){
			try{
				new MultiPintura().borrar(pnumero);
				return "¡Pintura eliminada!";
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}

	//LISTAR PINTURAS
		public String[][] pinturaConsultarLista(){
			try{
				ArrayList<Pintura> listaPinturas = (new MultiPintura()).listar();
				String[][] infoPinturas = new String[listaPinturas.size()][2];

				int i = 0;
				for(Pintura a : listaPinturas){
					infoPinturas[i][0]= String.valueOf(a.getNumero());
					infoPinturas[i][1]= a.getNombre();
					i++;
				}
				return infoPinturas;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}

	//CONSULTAR PINTURA POR NOMBRE
		public String[] pinturaConsultarXNombre(String pnombre){
			try{
				Pintura pintura = new MultiPintura().consultarXNombre(pnombre);
				if(pintura != null){
					String[] info = new String[]{String.valueOf(pintura.getNumero()), pintura.getNombre(), pintura.getTecnica()};
					return info;
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//ASIGNAR GALERÍA A PINTURA
		public void pinturaAsignarGaleria(String pcedulaGaleria, int pnumeroPintura){
			try {
				Pintura pintura = new MultiPintura().consultarXNumero(pnumeroPintura);
				if(pintura != null){
					pintura.setCedulaGaleria(pcedulaGaleria);
					(new MultiPintura()).asignarGaleria(pintura);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	//CONSULTAR PINTURAS NO ADQUIRIDAS POR NOMBRE
		public String[] pinturaNoAdquiridaConsultarXNombre(String pnombre){
			try{
				ArrayList<Pintura> listaPinturas = new MultiPintura().listarPinturasNoAdquiridas();
				Pintura pintura = new MultiPintura().consultarXNombre(pnombre);

				if(pintura != null){
					for(int i=0; i<listaPinturas.size(); i++){
						if(listaPinturas.get(i).getNombre().equals(pintura.getNombre())){
							String[] info = new String[]{String.valueOf(pintura.getNumero()), pintura.getNombre(), pintura.getTecnica()};
							return info;
						}
					}
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//LISTAR PINTURAS NO ADQUIRIDAS
		public String[][] pinturaConsultarListaNoAdquiridas(){
			try{
				ArrayList<Pintura> listaPinturas = (new MultiPintura()).listarPinturasNoAdquiridas();
				String[][] info = new String[listaPinturas.size()][3];
				int i = 0;
				for(Pintura pintura : listaPinturas){
					info[i][0]= String.valueOf(pintura.getNumero());
					info[i][1]= pintura.getNombre();
					info[i][2]= pintura.getTecnica();
					i++;
				}
				return info;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}



	/* ***********************************
	 * 		    *** PINTORES ***
	 * ***********************************
	 */

	//REGISTRAR PINTOR
		public String pintorRegistrar(String pcedula, String pnombre, String papellido1, String papellido2, String pseudonimo,
										String pnacionalidad, String ppaisOrigen, String pciudad, String pfechaNacimiento,
										String pfechaFallecimiento, String ptipo, String pnombreEscuela){
			try{
				String cedEscuela = "";
				if(pnombreEscuela != null){
					String[][] listaEscuelas = escuelaConsultarLista();
			    	for(int i=0; i<listaEscuelas.length; i++){
			    		if(pnombreEscuela.equals(listaEscuelas[i][1])){
			    			cedEscuela = listaEscuelas[i][0];
			    		}
					}
				}

				if(!(new MultiPintor()).buscar(pcedula)){
					new MultiPintor().crear(pcedula, pnombre, papellido1, papellido2, pseudonimo, pnacionalidad, ppaisOrigen,
											pciudad, pfechaNacimiento, pfechaFallecimiento, ptipo, cedEscuela);
					return "¡Pintor registrado!";
				}else{
					return "¡Pintor ya registrado!";
				}
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
		}

	//MODIFICAR PINTOR
		public String pintorModificar(String pcedula, String pnombre, String papellido1, String papellido2, String pseudónimo,
										String pnacionalidad, String ppaisOrigen, String pciudad, String pfechaNacimiento,
										String pfechaFallecimiento, String ptipo, String pnombreEscuela){
			String resul = "";
			try{
				String cedEscuela = "";
				String[][] listaEscuelas = escuelaConsultarLista();
		    	for(int i=0; i<listaEscuelas.length; i++){
		    		if(pnombreEscuela.equals(listaEscuelas[i][1])){
		    			cedEscuela = listaEscuelas[i][0];
		    		}
				}

				Pintor pintor = new MultiPintor().consultarXCedula(pcedula);
				if(pintor != null){
					pintor.setNombre(pnombre);
					pintor.setApellido1(papellido1);
					pintor.setApellido2(papellido2);
					pintor.setSeudónimo(pseudónimo);
					pintor.setNacionalidad(pnacionalidad);
					pintor.setPaisOrigen(ppaisOrigen);
					pintor.setCiudadOrigen(pciudad);
					pintor.setFechaNacimiento(pfechaNacimiento);
					pintor.setFechaFallecimiento(pfechaFallecimiento);
					pintor.setTipo(ptipo);
					pintor.setCedulaEscuela(cedEscuela);
					(new MultiPintor()).actualizar(pintor);
					resul = "¡Pintor modificado!";
				}else{
					resul = "¡Pintor no registrado!";
				}
			}catch(SQLException e){
				resul = "SQL-error: "+e.getMessage();
			}catch(Exception e){
				resul = "Excep-error: "+e.getMessage();
			}
			return resul;
		}

	//ELIMINAR PINTOR
		public String pintorEliminar(String pcedula){
			try{
				new MultiPintor().borrar(pcedula);
				return "¡Cliente eliminado!";
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}

	//LISTAR PINTORES
		public String[][] pintorConsultarLista(){
			try{
				ArrayList<Pintor> listaPintores = (new MultiPintor()).listar();
				String[][] infoPintores = new String[listaPintores.size()][3];

				int i = 0;
				for(Pintor a : listaPintores){
					infoPintores[i][0]= a.getCedula();
					infoPintores[i][1]= a.getNombre() + " " + a.getApellido1() + " " + a.getApellido2();
					infoPintores[i][2]= a.getTipo();
					i++;
				}
				return infoPintores;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}

	//CONSULTAR PINTOR POR NOMBRE
		public String[] pintorConsultarXNombre(String pnombre){
			try{
				Pintor pintor = new MultiPintor().consultarXNombre(pnombre);
				if(pintor != null){
					String[] info = new String[]{pintor.getCedula(), pintor.getNombre()+" "+pintor.getApellido1()+" "+pintor.getApellido2()};
					return info;
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//CONSULTAR PINTOR POR CEDULA
		public String[] pintorConsultarXCedula(String pcedula){
			String[] info = null;
			try{
				Pintor pintor = new MultiPintor().consultarXCedula(pcedula);
				Escuela escuela = pintor.getEscuela();
				if(pintor != null){
					if(escuela != null){
						info = new String[]{pintor.getCedula(), pintor.getNombre(), pintor.getApellido1(),
								pintor.getApellido2(), pintor.getSeudónimo(), pintor.getNacionalidad(),
								pintor.getPaisOrigen(), pintor.getCiudadOrigen(), pintor.getFechaNacimiento(),
								pintor.getFechaFallecimiento(), pintor.getTipo(), escuela.getNombre()};
					}else{
						info = new String[]{pintor.getCedula(), pintor.getNombre(), pintor.getApellido1(),
								pintor.getApellido2(), pintor.getSeudónimo(), pintor.getNacionalidad(),
								pintor.getPaisOrigen(), pintor.getCiudadOrigen(), pintor.getFechaNacimiento(),
								pintor.getFechaFallecimiento(), pintor.getTipo(), ""};
					}
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return info;
		}

	//CONSULTAR LA LLEGADA DE UNA PINTURA A UNA GALERÍA
		public String[] pinturaConsultarLlegadaAGaleria(int pnumero){
			try{
				Pintura pintura = new MultiPintura().consultarXNumero(pnumero);
				Galeria galeria = (new MultiGaleria()).consultarXCedula(pintura.getCedulaGaleria());
				RegistroLlegada llegada = galeria.consultarLlegadaPintura(pnumero);

				if(llegada != null){
					String[] info = new String[]{llegada.getFecha(), llegada.getCondicionLlegada(),
												llegada.getCondicionActual(), String.valueOf(llegada.getCostoAdquirida())};
					return info;
				}
			}catch(SQLException e){
				e.getMessage();
			}catch(Exception e){
				e.getMessage();
			}
			return null;
		}

	//ELEGIR MAESTRO
		public String pintorElegirMaestro(String pcedPintor, String pcedMaestro){
			try{
				(new MultiPintor()).asignarMaestro(pcedPintor, pcedMaestro);
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
			return null;
		}

	//CONSULTAR PINTORES NO MAESTROS
		public String[][] pintorConsultarListaNoMaestros(String pcedula){
			try{
				ArrayList<Pintor> listaPintores = (new MultiPintor()).listarPintoresNoMaestros(pcedula);
				String[][] infoPintores = new String[listaPintores.size()][2];

				int i = 0;
				for(Pintor a : listaPintores){
					infoPintores[i][0]= a.getCedula();
					infoPintores[i][1]= a.getNombre() + " " + a.getApellido1() + " " + a.getApellido2();
					i++;
				}
				return infoPintores;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}



	/* ***********************************
	 * 		    *** PATROCINADORES ***
	 * ***********************************
	 */

	//REGISTRAR PATROCINADOR
		public String patrocinadorRegistrar(String pcedula, String pnombre, String pprimerApellido, String psegundoApellido,
											String ppaisOrigen, String pciudadOrigen, String pfechaFallecimiento){
			try{
				new MultiPatrocinador().crear(pcedula, pnombre, pprimerApellido, psegundoApellido, ppaisOrigen, pciudadOrigen, pfechaFallecimiento);
				return "¡Patrocinador registrado!";
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
		}

	//MODIFICAR PATROCINADOR
		public String patrocinadorModificar(String pcedula, String pnombre, String pprimerApellido,
											String psegundoApellido, String ppaisOrigen, String pciudadOrigen,
											String pfechaFallecimiento){
			try{
				Patrocinador patrocinador = new MultiPatrocinador().consultarXCedula(pcedula);
				if(patrocinador != null){
					patrocinador.setNombre(pnombre);
					patrocinador.setApellido1(pprimerApellido);
					patrocinador.setApellido2(psegundoApellido);
					patrocinador.setPaisOrigen(ppaisOrigen);
					patrocinador.setCiudadOrigen(pciudadOrigen);
					patrocinador.setFechaFallecimiento(pfechaFallecimiento);

					(new MultiPatrocinador()).actualizar(patrocinador);
					return "¡Patrocinador modificado!";
				}else{
					return "¡Patrocinador no registrado!";
				}
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}

	//LISTAR PATROCINADORES
		public String[][] patrocinadorConsultarLista(){
			try{
				ArrayList<Patrocinador> listaPatrocinadores = (new MultiPatrocinador()).listar();
				String[][] info = new String[listaPatrocinadores.size()][2];
				int i = 0;
				for(Patrocinador patrocinador : listaPatrocinadores){
					info[i][0]= patrocinador.getCedula();
					info[i][1]= patrocinador.getNombre() + " " + patrocinador.getApellido1() + " " +
								patrocinador.getApellido2();
					i++;
				}
				return info;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}
		
		//CONSULTAR PATROCINIOS DE PATROCINADORES
				public String[][] patrocinadorConsultarPatrocinios(String pcedulaPatrocinador){
					try{
						ArrayList<Pintor> listaPintores = (new MultiPatrocinio()).buscarPatrocinios(pcedulaPatrocinador);
						String[][] infoPintores = new String[listaPintores.size()][3];

						int i = 0;
						for(Pintor a : listaPintores){
							infoPintores[i][0]= a.getCedula();
							infoPintores[i][1]= a.getNombre() + " " + a.getApellido1() + " " + a.getApellido2();
							infoPintores[i][2] = a.getTipo();
							i++;
						}
						return infoPintores;
					}catch(SQLException e){
						System.out.print("SQL-error: "+e.getMessage());
					}catch(Exception e){
						System.out.print("Excep-error: "+e.getMessage());
					}
					return null;
				}

	//CONSULTAR PATROCINADOR POR CÉDULA
		public String[] patrocinadorConsultarXCedula(String pcedula){
			try{
				Patrocinador patrocinador = new MultiPatrocinador().consultarXCedula(pcedula);
				if(patrocinador != null){
					String[] info = new String[]{patrocinador.getCedula(), patrocinador.getNombre(),
													patrocinador.getApellido1(), patrocinador.getApellido2(),
													patrocinador.getPaisOrigen(), patrocinador.getCiudadOrigen(),
													patrocinador.getFechaFallecimiento()};
					return info;
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//ELIMINAR PATROCINADOR
		public String patrocinadorEliminar(String pcedula){
			try{
				new MultiPatrocinador().borrar(pcedula);
				return "¡Patrocinador eliminado!";
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}



	/* ***********************************
	 * 		    *** ESCUELAS ***
	 * ***********************************
	 */

	//REGISTRAR ESCUELA
		public String escuelaRegistrar(String pcedulaJuridica, String pnombre, String ppaisFundacion, String pfechaFundacion,
										String pdescripcion){
			try{
				new MultiEscuela().crear(pcedulaJuridica, pnombre, ppaisFundacion, pfechaFundacion, pdescripcion);
				return "¡Escuela registrada!";
			}catch (SQLException e) {
				e.printStackTrace();
				return "SQL-error: " + e.getMessage();
			}catch (Exception e) {
				e.printStackTrace();
				return "Excep-error: " + e.getMessage();
			}
		}

	//MODIFICAR ESCUELA
		public String escuelaModificar(String pcedulaJuridica, String pnombre, String ppaisFundacion, String pfechaFundacion,
										String pdescripcion){
			try{
				Escuela escuela = new MultiEscuela().consultarXCedulaJuridica(pcedulaJuridica);
				if(escuela != null){
					escuela.setNombre(pnombre);
					escuela.setPaisFundada(ppaisFundacion);
					escuela.setFechaFundada(pfechaFundacion);
					escuela.setDescCaracteristicas(pdescripcion);

					(new MultiEscuela()).actualizar(escuela);
					return "¡Escuela modificada!";
				}else{
					return "¡Escuela no registrada!";
				}
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}

	//LISTAR ESCUELAS
		public String[][] escuelaConsultarLista(){
			try{
				ArrayList<Escuela> listaEscuelas = (new MultiEscuela()).listar();
				String[][] info = new String[listaEscuelas.size()][2];
				int i = 0;
				for(Escuela escuela : listaEscuelas){
					info[i][0]= escuela.getCedulaJuridica();
					info[i][1]= escuela.getNombre();
					i++;
				}
				return info;
			}catch(SQLException e){
				System.out.print("SQL-error: "+e.getMessage());
			}catch(Exception e){
				System.out.print("Excep-error: "+e.getMessage());
			}
			return null;
		}

	//CONSULTAR ESCUELA POR CÉDULA JURÍDICA
		public String[] escuelaConsultarXCedulaJuridica(String pcedula){
			try{
				Escuela escuela = new MultiEscuela().consultarXCedulaJuridica(pcedula);
				if(escuela != null){
					String[] info = new String[]{escuela.getCedulaJuridica(), escuela.getNombre(),
							escuela.getPaisFundada(), escuela.getFechaFundada(), escuela.getDescCaracteristicas()};
					return info;
				}
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return null;
		}

	//ELIMINAR ESCUELA
		public String escuelaEliminar(String pcedula){
			try{
				new MultiEscuela().borrar(pcedula);
				return "¡Escuela eliminada!";
			}catch(SQLException e){
				return "SQL-error: "+e.getMessage();
			}catch(Exception e){
				return "Excep-error: "+e.getMessage();
			}
		}
}
