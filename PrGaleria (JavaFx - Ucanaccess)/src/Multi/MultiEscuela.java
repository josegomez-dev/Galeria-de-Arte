package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Escuela;
import ConexionBD.Conector;

public class MultiEscuela {

	public Escuela crear(String pcedulaJuridica, String pnombre, String ppaisFundada, 
			String pfechaFundada, String pdescripcion) throws SQLException,Exception{
		
		Escuela pelicula = null;

		String sql = 	"INSERT INTO TEscuela " +
						"(CedulaJuridica, Nombre, PaisFundada, FechaFundada, Descripcion) " +
						"VALUES (?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pcedulaJuridica);
			pstm.setString(2,pnombre);
			pstm.setString(3,ppaisFundada);
			pstm.setString(4,pfechaFundada);
			pstm.setString(5,pdescripcion);
			pstm.executeUpdate();
			
			pelicula = new Escuela(pcedulaJuridica, pnombre, ppaisFundada, pfechaFundada, pdescripcion);
		}
		return pelicula;
	}
	
	public boolean buscar(String pcedulaJuridica) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TEscuela WHERE CedulaJuridica LIKE '%" + pcedulaJuridica + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}
	
	public Escuela consultarXCedulaJuridica(String pcedulaJuridica) throws SQLException,Exception{
		Escuela escuela = null;
		String sql;

		sql = "SELECT * FROM TEscuela WHERE CedulaJuridica LIKE '%" + pcedulaJuridica + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				escuela = new Escuela(rs.getString("CedulaJuridica"), rs.getString("Nombre"), rs.getString("PaisFundada"), 
						rs.getString("FechaFundada"), rs.getString("Descripcion"));
			}
		}
		return escuela;
	}
	
	public ArrayList<Escuela> listar() throws SQLException,Exception{
		Escuela escuela = null;
		ArrayList<Escuela> listaEscuelas = null;
		String sql; 
		
		sql = "SELECT * FROM TEscuela";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaEscuelas = new ArrayList<>();
			
			while (rs.next()){
				escuela = new Escuela(rs.getString("CedulaJuridica"), rs.getString("Nombre"), rs.getString("PaisFundada"), 
						rs.getString("FechaFundada"), rs.getString("Descripcion"));
				listaEscuelas.add(escuela);
			}
		}

		return listaEscuelas;
	}

	public void actualizar(Escuela pescuela) throws SQLException,Exception{
		
		String sql =	"UPDATE TEscuela " +
						"SET Nombre= ?, " +
						"PaisFundada= ?, " +
						"FechaFundada= ?, " +
						"Descripcion= ? " +
						"WHERE CedulaJuridica LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pescuela.getNombre());
			pstm.setString(2,pescuela.getPaisFundada());
			pstm.setString(3,pescuela.getFechaFundada());
			pstm.setString(4,pescuela.getDescCaracteristicas());
			pstm.setString(5,"%"+pescuela.getCedulaJuridica()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pcedulaJuridica) throws SQLException, Exception{
		
		String sql = "DELETE FROM TEscuela WHERE CedulaJuridica LIKE '%" + pcedulaJuridica + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
	
}
