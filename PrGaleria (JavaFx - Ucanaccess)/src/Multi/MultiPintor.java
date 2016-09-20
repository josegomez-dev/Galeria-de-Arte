package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Pintor;
import ConexionBD.Conector;

public class MultiPintor {

	public Pintor crear(String pcedula, String pnombre, String papellido1, String papellido2, String pseudonimo, String pnacionalidad,
			String ppaisOrigen, String pciudad, String pfechaNacimiento, String pfechaFallecimiento, String ptipo, String pcedulaEscuela) throws SQLException,Exception{

		Pintor pintor = null;

		String sql = 	"INSERT INTO TPintor " +
						"(Cedula, Nombre, Apellido1, Apellido2, Seudonimo, Nacionalidad, PaisOrigen, " +
						"CiudadOrigen, FechaNacimiento, FechaFallecimiento, Tipo, CedulaEscuela)" +
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){

			pstm.setString(1,pcedula);
			pstm.setString(2,pnombre);
			pstm.setString(3,papellido1);
			pstm.setString(4,papellido2);
			pstm.setString(5,pseudonimo);
			pstm.setString(6,pnacionalidad);
			pstm.setString(7,ppaisOrigen);
			pstm.setString(8,pciudad);
			pstm.setString(9,pfechaNacimiento);
			pstm.setString(10,pfechaFallecimiento);
			pstm.setString(11,ptipo);
			pstm.setString(12,pcedulaEscuela);
			pstm.executeUpdate();
			pintor = new Pintor(pcedula, pnombre, papellido1, papellido2, pseudonimo, pnacionalidad,
					ppaisOrigen, pciudad, pfechaNacimiento, pfechaFallecimiento, ptipo, pcedulaEscuela);
		}
		return pintor;
	}

	public void asignarMaestro(String pcedulaPintor, String pcedulaMaestro) throws SQLException,Exception{
		String sql = 	"INSERT INTO TMaestrosXPintor " +
						"(Cedula_pintor, Cedula_maestro)" +
						"VALUES (?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pcedulaPintor);
			pstm.setString(2,pcedulaMaestro);
			pstm.executeUpdate();
		}
	}

	public boolean buscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TPintor WHERE Cedula LIKE '%" + pcedula + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	public Pintor consultarXCedula(String pcedula) throws SQLException,Exception{
		Pintor pintor = null;

		String sql = "SELECT * FROM TPintor WHERE Cedula LIKE '%" + pcedula + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				pintor = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("Seudonimo"), rs.getString("Nacionalidad"),
						rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"), rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"), rs.getString("Tipo"), rs.getString("CedulaEscuela"));
			}
		}
		return pintor;
	}

	public Pintor consultarXNombre(String pnombre) throws SQLException,Exception{
		Pintor pintor = null;

		String sql = "SELECT * FROM TPintor WHERE Nombre LIKE '%" + pnombre + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				pintor = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("Seudonimo"),
						rs.getString("Nacionalidad"), rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"), rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"),
						rs.getString("Tipo"), rs.getString("CedulaEscuela"));
			}
		}
		return pintor;
	}

	public ArrayList<Pintor> listar() throws SQLException,Exception{
		Pintor pintor = null;
		ArrayList<Pintor> listaPintores = null;

		String sql = "SELECT * FROM TPintor";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPintores = new ArrayList<>();
			while (rs.next()){
				pintor = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("Seudonimo"),
						rs.getString("Nacionalidad"), rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"), rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"),
						rs.getString("Tipo"), rs.getString("CedulaEscuela"));
				listaPintores.add(pintor);
			}
		}

		return listaPintores;
	}

	public ArrayList<Pintor> listarXPatrocinador(String pcedulaPatrocinador) throws SQLException,Exception{
		Pintor pintor = null;
		ArrayList<Pintor> listaPintores = null;

		String sql = "SELECT * FROM TPintoresXPatrocinador WHERE CedulaPatrocinador LIKE '%" + pcedulaPatrocinador + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPintores = new ArrayList<>();
			while (rs.next()){
				pintor = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"),
									rs.getString("Apellido2"), rs.getString("Seudonimo"), rs.getString("Nacionalidad"),
									rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"),
									rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"),
									rs.getString("Tipo"), rs.getString("CedulaEscuela"));
				listaPintores.add(pintor);
			}
		}

		return listaPintores;
	}

	public ArrayList<Pintor> listarMaestrosXPintor(String pcedulaPintor) throws SQLException,Exception{
		Pintor pintor = null;
		ArrayList<Pintor> listaPintores = null;

		String sql = "SELECT * FROM TMaestrosXPintor WHERE Cedula_pintor LIKE '%" + pcedulaPintor + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPintores = new ArrayList<>();
			while (rs.next()){
				pintor = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"),
									rs.getString("Apellido2"), rs.getString("Seudonimo"), rs.getString("Nacionalidad"),
									rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"),
									rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"),
									rs.getString("Tipo"), rs.getString("CedulaEscuela"));
				listaPintores.add(pintor);
			}
		}

		return listaPintores;
	}

	public ArrayList<Pintor> listarPintoresNoMaestros(String pcedula) throws SQLException,Exception{
		Pintor pintor = null;
		ArrayList<Pintor> listaPatrocinadores = null;

		String sql =	"SELECT * FROM TPintor WHERE Cedula NOT IN " +
						"	(SELECT Cedula_maestro " +
						"	 FROM TMaestrosXPintor " +
						"	 WHERE Cedula_pintor LIKE '%" + pcedula + "%');";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPatrocinadores = new ArrayList<>();

			while (rs.next()){
				pintor = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("Seudonimo"),
						rs.getString("Nacionalidad"), rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"), rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"),
						rs.getString("Tipo"), rs.getString("CedulaEscuela"));
				listaPatrocinadores.add(pintor);
			}
		}
		return listaPatrocinadores;
	}

	public void actualizar(Pintor ppintor) throws SQLException,Exception{

		String sql =	"UPDATE TPintor " +
						"SET Nombre= ?, " +
						"Apellido1= ?, " +
						"Apellido2= ?, " +
						"Seudonimo= ?, " +
						"Nacionalidad= ?, " +
						"PaisOrigen= ?, " +
						"CiudadOrigen= ?, " +
						"FechaNacimiento= ?, " +
						"FechaFallecimiento= ?, " +
						"Tipo= ?, " +
						"CedulaEscuela= ? " +
						"WHERE Cedula LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppintor.getNombre());
			pstm.setString(2,ppintor.getApellido1());
			pstm.setString(3,ppintor.getApellido2());
			pstm.setString(4,ppintor.getSeudónimo());
			pstm.setString(5,ppintor.getNacionalidad());
			pstm.setString(6,ppintor.getPaisOrigen());
			pstm.setString(7,ppintor.getCiudadOrigen());
			pstm.setString(8,ppintor.getFechaNacimiento());
			pstm.setString(9,ppintor.getFechaFallecimiento());
			pstm.setString(10,ppintor.getTipo());
			pstm.setString(11,ppintor.getCedulaEscuela());
			pstm.setString(12,"%"+ppintor.getCedula()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pcedula) throws SQLException, Exception{

		String sql = "DELETE FROM TPintor WHERE Cedula LIKE '%" + pcedula + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}

}
