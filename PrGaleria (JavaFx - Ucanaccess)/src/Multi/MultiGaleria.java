package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Galeria;
import ConexionBD.Conector;

public class MultiGaleria {

	public Galeria crear(String pcedula, String pnombre, String pdireccion, String ptelefono, String pfecha,
							String pencargado, double pmetros) throws SQLException,Exception{
		Galeria galeria = null;

		String sql=	"INSERT INTO TGaleria " +
					"(Cedula_juridica, Nombre, Direccion, Telefono, Fecha_inaugurada, Encargado, Metros) " +
					"VALUES (?,?,?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pcedula);
			pstm.setString(2,pnombre);
			pstm.setString(3,pdireccion);
			pstm.setString(4,ptelefono);
			pstm.setString(5,pfecha);
			pstm.setString(6,pencargado);
			pstm.setDouble(7,pmetros);
			pstm.executeUpdate();
			galeria = new Galeria(pcedula, pnombre, pdireccion, ptelefono, pfecha, pencargado, pmetros);
		}
		return galeria;
	}

	public ArrayList<Galeria> listar() throws SQLException,Exception{
		Galeria galeria = null;
		ArrayList<Galeria> listaGalerias = null;

		String sql = "SELECT * FROM TGaleria";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaGalerias = new ArrayList<>();

			while (rs.next()){
				galeria = new Galeria(rs.getString("Cedula_juridica"), rs.getString("Nombre"), rs.getString("Direccion"),
										rs.getString("Telefono"), rs.getString("Fecha_inaugurada"),
										rs.getString("Encargado"), Double.parseDouble(rs.getString("Metros")));
				listaGalerias.add(galeria);
			}
		}

		return listaGalerias;
	}

	public Galeria consultarXCedula(String pcedula) throws SQLException,Exception{
		Galeria galeria = null;

		String sql = "SELECT * FROM TGaleria WHERE Cedula_juridica LIKE '%" + pcedula + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				galeria = new Galeria(rs.getString("Cedula_juridica"), rs.getString("Nombre"), rs.getString("Direccion"),
										rs.getString("Telefono"), rs.getString("Fecha_inaugurada"),
										rs.getString("Encargado"), Double.parseDouble(rs.getString("Metros")));
			}
		}
		return galeria;
	}

	public boolean buscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TGaleria WHERE Cedula_juridica LIKE '%" + pcedula + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	public void actualizar(Galeria pgaleria) throws SQLException,Exception{
		String sql =	"UPDATE TGaleria "+
						"SET Nombre= ?, "+
						"Direccion= ?, " +
						"Telefono= ?, " +
						"Fecha_inaugurada= ?, " +
						"Encargado= ?, " +
						"Metros= ? " +
						"WHERE Cedula_juridica LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pgaleria.getNombre());
			pstm.setString(2,pgaleria.getDireccion());
			pstm.setString(3,pgaleria.getTelefono());
			pstm.setString(4,pgaleria.getFechaInauguracion());
			pstm.setString(5,pgaleria.getNombreEncargado());
			pstm.setDouble(6,pgaleria.getMetrosCuadrados());
			pstm.setString(7,"%" + pgaleria.getCedulaJuridica() + "%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pcedula) throws SQLException, Exception{
		String sql = "DELETE FROM TGaleria WHERE Cedula_juridica LIKE '%" + pcedula + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
}
