package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Patrocinador;
import ConexionBD.Conector;

public class MultiPatrocinador {

	public Patrocinador crear(String pcedula, String pnombre, String papellido1, String papellido2, String ppaisNacimiento, 
			String pciudadNacimiento, String pfechaFallecimiento) throws SQLException,Exception{
		
		Patrocinador patrocinador = null;

		String sql = 	"INSERT INTO TPatrocinador " +
						"(Cedula, Nombre, Apellido1, Apellido2, PaisOrigen, CiudadOrigen, FechaFallecimiento) " +
						"VALUES (?,?,?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pcedula);
			pstm.setString(2,pnombre);
			pstm.setString(3,papellido1);
			pstm.setString(4,papellido2);
			pstm.setString(5,ppaisNacimiento);
			pstm.setString(6,pciudadNacimiento);
			pstm.setString(7,pfechaFallecimiento);
			pstm.executeUpdate();
			
			patrocinador = new Patrocinador(pcedula, pnombre, papellido1, papellido2, ppaisNacimiento, 
					pciudadNacimiento, pfechaFallecimiento);
		}
		return patrocinador;
	}
	
	public boolean buscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TPatrocinador WHERE Cedula LIKE '%" + pcedula + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}
	
	public Patrocinador consultarXCedula(String pcedula) throws SQLException,Exception{
		Patrocinador patrocinador = null;
		
		String sql = "SELECT Cedula, Nombre, Apellido1, Apellido2, PaisOrigen, CiudadOrigen, FechaFallecimiento, FechaInicio, FechaFin FROM TPatrocinador WHERE Cedula LIKE '%" + pcedula + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				patrocinador = new Patrocinador(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("PaisOrigen"), 
						rs.getString("CiudadOrigen"), rs.getString("FechaFallecimiento"));
			}
		}
		return patrocinador;
	}
	
	public ArrayList<Patrocinador> listar() throws SQLException,Exception{
		Patrocinador patrocinador = null;
		ArrayList<Patrocinador> listaPatrocinadores = null;
		String sql; 
		
		sql = "SELECT Cedula, Nombre, Apellido1, Apellido2, PaisOrigen, CiudadOrigen, FechaFallecimiento, FechaInicio, FechaFin FROM TPatrocinador";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPatrocinadores = new ArrayList<>();
			
			while (rs.next()){
				patrocinador = new Patrocinador(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("PaisOrigen"), 
						rs.getString("CiudadOrigen"), rs.getString("FechaFallecimiento"));
				listaPatrocinadores.add(patrocinador);
			}
		}

		return listaPatrocinadores;
	}
	
	// LISTAR CON LA CEDULA DEL PINTOR
		public ArrayList<Patrocinador> listar(String pcedulaPintor) throws SQLException,Exception{
			Patrocinador patrocinador = null;
			ArrayList<Patrocinador> listaPatrocinadores = null;

			String sql = "SELECT * FROM TPatrocinador WHERE CedulaPintor LIKE '%" + pcedulaPintor + "%';";

			try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
				listaPatrocinadores = new ArrayList<>();
				
				while (rs.next()){
					patrocinador = new Patrocinador(rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getString("PaisOrigen"), 
							rs.getString("CiudadOrigen"), rs.getString("FechaFallecimiento"));
					listaPatrocinadores.add(patrocinador);
				}
			}
			return listaPatrocinadores;
		}

	public void actualizar(Patrocinador ppatrocinador) throws SQLException,Exception{
		
		String sql =	"UPDATE TPatrocinador " +
						"SET Nombre= ?, " +
						"Apellido1= ?, " +
						"Apellido2= ?, " +
						"PaisOrigen= ?, " +
						"CiudadOrigen= ?, " +
						"FechaFallecimiento= ? " +
						"WHERE Cedula LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppatrocinador.getNombre());
			pstm.setString(2,ppatrocinador.getApellido1());
			pstm.setString(3,ppatrocinador.getApellido2());
			pstm.setString(4,ppatrocinador.getPaisOrigen());
			pstm.setString(5,ppatrocinador.getCiudadOrigen());
			pstm.setString(6,ppatrocinador.getFechaFallecimiento());
			pstm.setString(7,"%"+ppatrocinador.getCedula()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pcedula) throws SQLException, Exception{
		
		String sql = "DELETE FROM TPatrocinador WHERE Cedula LIKE '%" + pcedula + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
	
}
