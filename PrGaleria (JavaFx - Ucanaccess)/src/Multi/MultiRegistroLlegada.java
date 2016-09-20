package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.RegistroLlegada;
import ConexionBD.Conector;

public class MultiRegistroLlegada {

	public RegistroLlegada crear(int pnumPintura, String pexposicion, String pfecha, String pcondLlegada, String pcondActual,
							 		double pcostoAdquirida, String pcedGaleria) throws SQLException,Exception{
		RegistroLlegada registroLlegada = null;
		int consecutivo;

		String sql=	"INSERT INTO TRegistroLlegada " +
					"(Fecha, Condicion_llegada, Condicion_actual, Costo_adquirida, Num_pintura, Ced_galeria, Exposicion) " +
					"VALUES (?,?,?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pfecha);
			pstm.setString(2,pcondLlegada);
			pstm.setString(3,pcondActual);
			pstm.setDouble(4,pcostoAdquirida);
			pstm.setInt(5,pnumPintura);
			pstm.setString(6,pcedGaleria);
			pstm.setString(7,pexposicion);
			pstm.executeUpdate();

			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					consecutivo = generatedKeys.getInt(1);
				}else{
					throw new SQLException("¡Identificador de la llegada no creado!");
				}
			}

			registroLlegada = new RegistroLlegada(consecutivo, pfecha, pcondLlegada, pcondActual, pexposicion,
													pcostoAdquirida, pnumPintura, pcedGaleria);
		}
		return registroLlegada;
	}

	public ArrayList<RegistroLlegada> consultarXGaleria(String pcedGaleria) throws SQLException,Exception{
		RegistroLlegada registroLlegada = null;
		ArrayList<RegistroLlegada> listaRegistroLlegada = null;

		String sql = "SELECT * FROM TRegistroLlegada WHERE Ced_galeria LIKE '%" + pcedGaleria + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaRegistroLlegada = new ArrayList<>();
			while (rs.next()){
				registroLlegada = new RegistroLlegada(rs.getInt("Numero"), rs.getString("Fecha"),
														rs.getString("Condicion_llegada"), rs.getString("Condicion_actual"),
														rs.getString("Exposicion"), rs.getDouble("Costo_adquirida"),
														rs.getInt("Num_pintura"), rs.getString("Ced_galeria"));
				listaRegistroLlegada.add(registroLlegada);
			}
		}
		return listaRegistroLlegada;
	}

	public void borrar(int pnumPintura) throws SQLException, Exception{
		String sql = "DELETE FROM TRegistroLlegada WHERE Num_pintura = " + pnumPintura + ";";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}

	public RegistroLlegada consultarXPintura(int pnumPintura) throws SQLException,Exception{
		RegistroLlegada registroLlegada = null;

		String sql = "SELECT * FROM TRegistroLlegada WHERE Num_pintura = " + pnumPintura + ";";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()){
				registroLlegada = new RegistroLlegada(rs.getInt("Numero"), rs.getString("Fecha"),
														rs.getString("Condicion_llegada"), rs.getString("Condicion_actual"),
														rs.getString("Exposicion"), rs.getDouble("Costo_adquirida"),
														rs.getInt("Num_pintura"), rs.getString("Ced_galeria"));
			}
		}
		return registroLlegada;
	}

	public void cambiarExposicion(RegistroLlegada pllegada) throws SQLException,Exception{
		String sql =	"UPDATE TRegistroLlegada "+
						"SET Exposicion= ? "+
						"WHERE Num_pintura = ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pllegada.getExposicion());
			pstm.setInt(2,pllegada.getNumPintura());
			pstm.executeUpdate();
		}
	}

//	public ArrayList<RegistroLlegada> consultarXGaleriaYRangoFechas(String pcedGaleria, String pfecha1, String pfecha2) throws SQLException,Exception{
//		RegistroLlegada registroLlegada = null;
//		ArrayList<RegistroLlegada> listaRegistroLlegada = null;
//
//		String sql = "SELECT * FROM TRegistroLlegada WHERE Ced_galeria LIKE '%" + pcedGaleria + "%';";
//
//		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
//			listaRegistroLlegada = new ArrayList<>();
//			while (rs.next()){
//				registroLlegada = new RegistroLlegada(rs.getInt("Numero"), rs.getString("Fecha"),
//														rs.getString("Condicion_llegada"), rs.getString("Condicion_actual"),
//														rs.getString("Exposicion"), rs.getDouble("Costo_adquirida"),
//														rs.getInt("Num_pintura"), rs.getString("Ced_galeria"));
//				listaRegistroLlegada.add(registroLlegada);
//			}
//		}
//		return listaRegistroLlegada;
//	}
}
