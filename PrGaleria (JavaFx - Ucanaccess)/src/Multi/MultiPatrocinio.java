package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Patrocinio;
import CapaLogica.Pintor;
import ConexionBD.Conector;

public class MultiPatrocinio {
	
	public Patrocinio crear(String pfechaInicio, String pfechaFin, String pcedulaPintor, String pcedulaPatrocinador) throws SQLException,Exception{
		
		Patrocinio patrocinador = null;

		String sql = 	"INSERT INTO TPatrocinio " +
						"(FechaInicio, FechaFin, CedulaPintor, CedulaPatrocinador) " +
						"VALUES (?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pfechaInicio);
			pstm.setString(2,pfechaFin);
			pstm.setString(3,pcedulaPintor);
			pstm.setString(4,pcedulaPatrocinador);
			pstm.executeUpdate();
			
			patrocinador = new Patrocinio(pfechaInicio, pfechaFin, pcedulaPintor, pcedulaPatrocinador);
		}
		return patrocinador;
	}
	
	public boolean buscar(String pcedulaPintor) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TPatrocinio WHERE CedulaPintor LIKE '%" + pcedulaPintor + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}
	
	public Patrocinio consultarXCedula(String pcedulaPintor) throws SQLException,Exception{
		Patrocinio patrocinio = null;
		
		String sql = "SELECT * FROM TPatrocinio WHERE CedulaPintor LIKE '%" + pcedulaPintor + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				patrocinio = new Patrocinio(rs.getString("FechaInicio"), rs.getString("FechaFin"), 
						rs.getString("CedulaPintor"), rs.getString("CedulaPatrocinador"));
			}
		}
		return patrocinio;
	}
	
	public ArrayList<Patrocinio> listar() throws SQLException,Exception{
		Patrocinio patrocinio = null;
		ArrayList<Patrocinio> listaPatrocinios = null;
		String sql; 
		
		sql = "SELECT * FROM TPatrocinio";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPatrocinios = new ArrayList<>();
			
			while (rs.next()){
				patrocinio = new Patrocinio(rs.getString("FechaInicio"), rs.getString("FechaFin"), 
						rs.getString("CedulaPintor"), rs.getString("CedulaPatrocinador"));
				listaPatrocinios.add(patrocinio);
			}
		}

		return listaPatrocinios;
	}
	
	public ArrayList<Pintor> buscarPatrocinios(String pcedulaPatrocinador) throws SQLException,Exception{
		Pintor patrocinio = null;
		ArrayList<Pintor> listaPintores = null;
		
		String sql = "SELECT * FROM TPatrocinio WHERE CedulaPintor LIKE '%" + pcedulaPatrocinador + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPintores = new ArrayList<>();
			
			while (rs.next()){
				patrocinio = new Pintor(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("Seudonimo"), rs.getString("Nacionalidad"),
						rs.getString("PaisOrigen"), rs.getString("CiudadOrigen"), rs.getString("FechaNacimiento"), rs.getString("FechaFallecimiento"), rs.getString("Tipo"), rs.getString("CedulaEscuela"));
				listaPintores.add(patrocinio);
			}
		}

		return listaPintores;
	}
	
	public void actualizar(Patrocinio ppatrocinador) throws SQLException,Exception{
		
		String sql =	"UPDATE TPatrocinio " +
						"SET FechaInicio= ?, " +
						"FechaFin= ?, " +
						"CedulaPintor= ?, " +
						"CedulaPatrocinio= ? " +
						"WHERE CodPatrocinio LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppatrocinador.getFechaInicio());
			pstm.setString(2,ppatrocinador.getFechaFin());
			pstm.setString(3,ppatrocinador.getCedulaPintor());
			pstm.setString(4,ppatrocinador.getCedulaPatrocinador());
			pstm.setString(5,"%"+ppatrocinador.getCodPatrocinio()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pcedulaPintor) throws SQLException, Exception{
		
		String sql = "DELETE FROM TPatrocinio WHERE CedulaPintor LIKE '%" + pcedulaPintor + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}

}
