package CapaLogica;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import InterfazUsuario.ValidacionForm;
import Multi.MultiEscuela;
import Multi.MultiGaleria;
import Multi.MultiPatrocinador;
import Multi.MultiPintor;
import Multi.MultiPintura;
import Multi.MultiRegistroLlegada;
import javafx.scene.control.TextField;

public class Gestor {

	/* ***********************************
	 * 		    *** GALERÍAS ***
	 * ***********************************
	 */

	//REGISTRAR
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

	//MODIFICAR
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

	//LISTAR
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

	//CONSULTAR POR CÉDULA
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

	//ELIMINAR
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

	//LISTAR PINTURAS NO ADQUIRIDAS
	public String[][] galeriaConsultarListaPinturasNoAdquiridas(){
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


	//
	public String galeriaRegistrarLlegadaPintura(String pcedGaleria, int pnumPintura, String pexposicion, String pfecha,
													String pcondicion, String pcondicionActual, double pcostoAdquisicion){
		try{
			Galeria galeria = (new MultiGaleria()).consultarXCedula(pcedGaleria);
			if(galeria != null){
				return galeria.registrarLlegadaPintura(pnumPintura, pexposicion, pfecha, pcondicion, pcondicionActual,
														pcostoAdquisicion);
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

	//QUITAR PINTURAS
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

	//CONSULTAR POR CÉDULA
	public String[] galeriaConsultarLlegadaPintura(int pnumero){
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
	public String pintorRegistrarPintura(String pnombre, double palto, double pancho, String pfecha, int panios, int pmeses,
											int pdias, String ptecnica, String pfamoso, String phistoria, String pcedulaPintor){
		try{
			Pintor pintor = (new MultiPintor()).consultarXCedula(pcedulaPintor);
			if(pintor != null){
				return pintor.registrarPintura(pnombre, palto, pancho, pfecha, panios, pmeses, pdias, ptecnica, pfamoso,
												phistoria);
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

	//MODIFICAR
	public String pinturaModificar(int pnumero, String pnombre, double palto, double pancho, String pfecha, int panios,
									int pmeses, int pdias, String ptecnica, String pfamoso, String phistoria){
		try{
			Pintura pintura = new MultiPintura().consultarXNumero(pnumero);
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

				(new MultiPintura()).actualizar(pintura);
				return "¡Pintura modificada!";
			}else{
				return "¡Pintura no registrada!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//LISTAR
	public String[][] pinturaConsultarListaXPintor(String pcedulaPintor){
		try{
			Pintor pintor = (new MultiPintor()).consultarXCedula(pcedulaPintor);
			ArrayList<Pintura> listaPinturas = pintor.consultarPinturas();
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

	//CONSULTAR POR CÉDULA
	public String[] pinturaConsultarXNumero(int pnumero){
		try{
			Pintura pintura = new MultiPintura().consultarXNumero(pnumero);
			Pintor pintor = pintura.getPintor();

			if(pintura != null){
				String[] info = new String[]{pintura.getNombre(), String.valueOf(pintura.getDimensiones()[0]),
												String.valueOf(pintura.getDimensiones()[1]), pintura.getFechaCreada(),
												String.valueOf(pintura.getDuracionCreada()[0]),
												String.valueOf(pintura.getDuracionCreada()[1]),
												String.valueOf(pintura.getDuracionCreada()[2]), pintura.getTecnica(),
												pintura.getFamosa(), pintura.getHistoria(), pintor.getNombre(),
												pintor.getTipo(), pintura.getCedulaGaleria()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	//ELIMINAR
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

	//CONSULTAR POR NOMBRE
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

	//ADQUIRIR PINTURAS
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

	//CONSULTAR POR NOMBRE
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

	/* ***********************************
	 * 		    *** PINTORES ***
	 * ***********************************
	 */

	//REGISTRAR
	public String pintorRegistrar(String pcedula, String pnombre, String papellido1, String papellido2, String pseudonimo,
			String pnacionalidad, String ppaisOrigen, String pciudad, String pfechaNacimiento, String pfechaFallecimiento,
			String ptipo, String pcedulaEscuela){
		try{
			if(!pintorBuscar(pcedula)){
				new MultiPintor().crear(pcedula, pnombre, papellido1, papellido2, pseudonimo, pnacionalidad, ppaisOrigen,
						pciudad, pfechaNacimiento, pfechaFallecimiento, ptipo, pcedulaEscuela);

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

	//MODIFICAR
	public String pintorModificar(String pcedula, String pnombre, String papellido1, String papellido2, String pseudónimo,
			String pnacionalidad, String ppaisOrigen, String pciudad, String pfechaNacimiento, String pfechaFallecimiento,
			String ptipo, String pcedulaEscuela){
		try{
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
				pintor.setCedulaEscuela(pcedulaEscuela);
				(new MultiPintor()).actualizar(pintor);
				return "¡Pintor modificado!";
			}else{
				return "¡Pintor no registrado!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//ELIMINAR
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

	//BUSCAR
	public boolean pintorBuscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = (new MultiPintor()).buscar(pcedula);
		return encontrado;
	}

	//LISTAR
	public String[][] pintorConsultarLista(){
		try{
			ArrayList<Pintor> listaPintores = (new MultiPintor()).listar();
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

	//CONSULTAR POR NOMBRE
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

	//CONSULTAR POR CEDULA
	public String[] pintorConsultarXCedula(String pcedula){
		try{
			Pintor pintor = new MultiPintor().consultarXCedula(pcedula);
			if(pintor != null){
				String[] info = new String[]{pintor.getCedula(), pintor.getNombre(), pintor.getApellido1(), pintor.getApellido2(), pintor.getSeudónimo(), pintor.getNacionalidad(), pintor.getPaisOrigen(), pintor.getCiudadOrigen(), pintor.getFechaNacimiento(), pintor.getFechaFallecimiento(), pintor.getTipo(), pintor.getCedulaEscuela() };
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	/* ***********************************
	 * 		    *** PATROCINADORES ***
	 * ***********************************
	 */

	//REGISTRAR
	public String patrocinadorRegistrar(String pcedula, String pnombre, String pprimerApellido, String psegundoApellido, String ppaisOrigen,
									String pciudadOrigen, String pfechaFallecimiento, String pfechaInicio, String pfechaFin){
		try{
			new MultiPatrocinador().crear(pcedula, pnombre, pprimerApellido, psegundoApellido, ppaisOrigen, pciudadOrigen, pfechaFallecimiento, pfechaInicio, pfechaFin);
			return "¡Patrocinador registrado!";
		}catch (SQLException e) {
			e.printStackTrace();
			return "SQL-error: " + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String patrocinadorModificar(String pcedula, String pnombre, String pprimerApellido, String psegundoApellido, String ppaisOrigen,
			String pciudadOrigen, String pfechaFallecimiento, String pfechaInicio, String pfechaFin){
		try{
			Patrocinador patrocinador = new MultiPatrocinador().consultarXCedula(pcedula);
			if(patrocinador != null){
				patrocinador.setNombre(pnombre);
				patrocinador.setApellido1(pprimerApellido);
				patrocinador.setApellido2(psegundoApellido);
				patrocinador.setPaisOrigen(ppaisOrigen);
				patrocinador.setCiudadOrigen(pciudadOrigen);
				patrocinador.setFechaFallecimiento(pfechaFallecimiento);
				patrocinador.setFechaInicio(pfechaInicio);
				patrocinador.setFechaFin(pfechaFin);

				(new MultiPatrocinador()).actualizar(patrocinador);
				return "¡Patrocinador modificada!";
			}else{
				return "¡Patrocinador no registrada!";
			}
		}catch(SQLException e){
			return "SQL-error: "+e.getMessage();
		}catch(Exception e){
			return "Excep-error: "+e.getMessage();
		}
	}

	//LISTAR
	public String[][] patrocinadorConsultarLista(){
		try{
			ArrayList<Patrocinador> listaPatrocinadores = (new MultiPatrocinador()).listar();
			String[][] info = new String[listaPatrocinadores.size()][2];
			int i = 0;
			for(Patrocinador patrocinador : listaPatrocinadores){
				info[i][0]= patrocinador.getCedula();
				info[i][1]= patrocinador.getNombre() + " " + patrocinador.getApellido1() + " " + patrocinador.getApellido2();
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

	//CONSULTAR POR CÉDULA
	public String[] patrocinadorConsultarXCedula(String pcedula){
		try{
			Patrocinador patrocinador = new MultiPatrocinador().consultarXCedula(pcedula);
			if(patrocinador != null){
				String[] info = new String[]{patrocinador.getCedula(), patrocinador.getNombre(), patrocinador.getApellido1(),
						patrocinador.getApellido2(), patrocinador.getPaisOrigen(), patrocinador.getCiudadOrigen(),
						patrocinador.getFechaFallecimiento(), patrocinador.getFechaInicio(), patrocinador.getFechaFin()};
				return info;
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//CONSULTAR POR CÉDULA
		public String[][] patrocinadorFinanciarPatrocinadores(String pcedula){
			try{
				String[][] info;
				ArrayList<Pintor> listaPintores = new MultiPintor().listarPorPatrocinador(pcedula);
				
				if(listaPintores != null){
					info = new String[listaPintores.size()][3];
						
					int i = 0;
					for(Pintor a : listaPintores){
						info[i][0] = listaPintores.get(i).getCedula();
						info[i][0] = listaPintores.get(i).getNombre() + " " + listaPintores.get(i).getApellido1() + " " + listaPintores.get(i).getApellido2();
						info[i][0] = listaPintores.get(i).getTipo();
						i++;
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

	//ELIMINAR
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

	//REGISTRAR
	public String escuelaRegistrar(String pcedulaJuridica, String pnombre, String ppaisFundacion, String pfechaFundacion, String pdescripcion){
		try{
			new MultiEscuela().crear(pcedulaJuridica, pnombre, ppaisFundacion, pfechaFundacion, pdescripcion);
			return "¡Patrocinador registrado!";
		}catch (SQLException e) {
			e.printStackTrace();
			return "SQL-error: " + e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return "Excep-error: " + e.getMessage();
		}
	}

	//MODIFICAR
	public String escuelaModificar(String pcedulaJuridica, String pnombre, String ppaisFundacion, String pfechaFundacion, String pdescripcion){
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

	//LISTAR
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

	//CONSULTAR POR CÉDULA
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

	//ELIMINAR
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

	//LISTAR
	public String[][] pintorListarSinPintor(String pcedulaPintor){
		try{
			ArrayList<Pintor> listaPintores = (new MultiPintor()).listarSinPintor(pcedulaPintor);
			String[][] info = new String[listaPintores.size()][2];
			int i = 0;
			for(Pintor pintor : listaPintores){
				info[i][0]= pintor.getCedula();
				info[i][1]= pintor.getNombre()+" "+pintor.getApellido1()+" "+pintor.getApellido2();
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

	//LISTAR
	public String[][] pintorConsultarListaNoMaestros(String pcedula){
		try{
			ArrayList<Pintor> listaPintores = (new MultiPintor()).listarNoMaestros(pcedula);
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
}
