package Multi;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Pintura;
import ConexionBD.Conector;

public class MultiPintura {

	public Pintura crear(String pnombre, double palto, double pancho, String pfecha, int panios, int pmeses, int pdias,
							String ptecnica, String pfamoso, String phistoria, String pcedulaPintor) throws SQLException,Exception{
		Pintura pintura = null;
		int consecutivo;

		double[] dimensiones = new double[2];
		dimensiones[0] = palto;
		dimensiones[1] = pancho;

		int[] duracion = new int[3];
		duracion[0] = panios;
		duracion[1] = pmeses;
		duracion[2] = pdias;

		String sql=	"INSERT INTO TPintura " +
					"(Nombre, Alto, Ancho, Fecha_creada, Anios_duracion, Meses_duracion, Dias_duracion,  Tecnica, Famosa," +
					" Historia, Ced_pintor)" +
					"VALUES (?,?,?,?,?,?,?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pnombre);
			pstm.setDouble(2,palto);
			pstm.setDouble(3,pancho);
			pstm.setString(4,pfecha);
			pstm.setInt(5,panios);
			pstm.setInt(6,pmeses);
			pstm.setInt(7,pdias);
			pstm.setString(8,ptecnica);
			pstm.setString(9,pfamoso);
			pstm.setString(10,phistoria);
			pstm.setString(11,pcedulaPintor);
			pstm.executeUpdate();

			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					consecutivo = generatedKeys.getInt(1);
				}else{
					throw new SQLException("¡Identificador de la pintura no creado!");
				}
			}
			pintura = new Pintura(consecutivo, pnombre, dimensiones, pfecha, duracion, ptecnica, pfamoso,
									phistoria, pcedulaPintor);
		}
		return pintura;
	}

	public ArrayList<Pintura> listar() throws SQLException,Exception{
		Pintura pintura = null;
		ArrayList<Pintura> listaPinturas = null;

		double[] dimensiones = new double[2];
		int[] duracion = new int[3];

		String sql = "SELECT * FROM TPintura";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPinturas = new ArrayList<>();

			while (rs.next()){
				dimensiones[0] = rs.getDouble("Alto");
				dimensiones[1] = rs.getDouble("Ancho");

				duracion[0] = rs.getInt("Anios_duracion");
				duracion[1] = rs.getInt("Meses_duracion");
				duracion[2] = rs.getInt("Dias_duracion");

				pintura = new Pintura(rs.getInt("Numero"), rs.getString("Nombre"), dimensiones, rs.getString("Fecha_creada"),
										duracion, rs.getString("Tecnica"), rs.getString("Famosa"), rs.getString("Historia"),
										rs.getString("Ced_pintor"));
				listaPinturas.add(pintura);
			}
		}

		return listaPinturas;
	}

	public Pintura consultarXNumero(int pnumero) throws SQLException,Exception{
		Pintura pintura = null;

		double[] dimensiones = new double[2];
		int[] duracion = new int[3];

		String sql = "SELECT * FROM TPintura WHERE Numero = " + pnumero + ";";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				dimensiones[0] = rs.getDouble("Alto");
				dimensiones[1] = rs.getDouble("Ancho");

				duracion[0] = rs.getInt("Anios_duracion");
				duracion[1] = rs.getInt("Meses_duracion");
				duracion[2] = rs.getInt("Dias_duracion");

				pintura = new Pintura(rs.getInt("Numero"), rs.getString("Nombre"), dimensiones, rs.getString("Fecha_creada"),
										duracion, rs.getString("Tecnica"), rs.getString("Famosa"), rs.getString("Historia"),
										rs.getString("Ced_pintor"));
				pintura.setCedulaGaleria(rs.getString("Ced_galeria"));
			}
		}
		return pintura;
	}

	public ArrayList<Pintura> consultarXPintor(String pcedulaPintor) throws SQLException,Exception{
		Pintura pintura = null;
		ArrayList<Pintura> listaPinturas = null;

		double[] dimensiones = new double[2];
		int[] duracion = new int[3];

		String sql = "SELECT * FROM TPintura WHERE Ced_pintor LIKE '%" + pcedulaPintor + "%';";;

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPinturas = new ArrayList<>();

			while (rs.next()){
				dimensiones[0] = rs.getDouble("Alto");
				dimensiones[1] = rs.getDouble("Ancho");

				duracion[0] = rs.getInt("Anios_duracion");
				duracion[1] = rs.getInt("Meses_duracion");
				duracion[2] = rs.getInt("Dias_duracion");

				pintura = new Pintura(rs.getInt("Numero"), rs.getString("Nombre"), dimensiones, rs.getString("Fecha_creada"),
										duracion, rs.getString("Tecnica"), rs.getString("Famosa"), rs.getString("Historia"),
										rs.getString("Ced_pintor"));
				listaPinturas.add(pintura);
			}
		}

		return listaPinturas;
	}

	public boolean buscar(int pnumero) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TPintura WHERE Numero = " + pnumero + ";";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	public void actualizar(Pintura ppintura) throws SQLException,Exception{
		String sql =	"UPDATE TPintura " +
						"SET Nombre= ?, " +
						"Alto= ?, " +
						"Ancho= ?, " +
						"Fecha_creada= ?, " +
						"Anios_duracion= ?, " +
						"Meses_duracion= ?, " +
						"Dias_duracion= ?, " +
						"Tecnica= ?, " +
						"Famosa= ?, " +
						"Historia= ?, " +
						"Ced_pintor= ? " +
						"WHERE Numero = ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppintura.getNombre());
			pstm.setDouble(2,ppintura.getDimensiones()[0]);
			pstm.setDouble(3,ppintura.getDimensiones()[1]);
			pstm.setString(4,ppintura.getFechaCreada());
			pstm.setInt(5,ppintura.getDuracionCreada()[0]);
			pstm.setInt(6,ppintura.getDuracionCreada()[1]);
			pstm.setInt(7,ppintura.getDuracionCreada()[2]);
			pstm.setString(8,ppintura.getTecnica());
			pstm.setString(9,ppintura.getFamosa());
			pstm.setString(10,ppintura.getHistoria());
			pstm.setString(11,ppintura.getCedulaPintor());
			pstm.setInt(12,ppintura.getNumero());
			pstm.executeUpdate();
		}
	}

	public void borrar(int pnumero) throws SQLException, Exception{
		String sql = "DELETE FROM TPintura WHERE Numero = " + pnumero + ";";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}

	public Pintura consultarXNombre(String pnombre) throws SQLException,Exception{
		Pintura pintura = null;

		double[] dimensiones = new double[2];
		int[] duracion = new int[3];

		String sql = "SELECT * FROM TPintura WHERE Nombre LIKE '%" + pnombre + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				dimensiones[0] = rs.getDouble("Alto");
				dimensiones[1] = rs.getDouble("Ancho");

				duracion[0] = rs.getInt("Anios_duracion");
				duracion[1] = rs.getInt("Meses_duracion");
				duracion[2] = rs.getInt("Dias_duracion");

				pintura = new Pintura(rs.getInt("Numero"), rs.getString("Nombre"), dimensiones, rs.getString("Fecha_creada"),
										duracion, rs.getString("Tecnica"), rs.getString("Famosa"), rs.getString("Historia"),
										rs.getString("Ced_pintor"));
			}
		}
		return pintura;
	}

	public void asignarGaleria(Pintura ppintura) throws SQLException, Exception{
		String sql ="UPDATE TPintura " +
					"SET Ced_galeria= ?, " +
					"Estado= ? " +
					"WHERE Numero = ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppintura.getCedulaGaleria());
			pstm.setString(2,"Adquirida");
			pstm.setInt(3,ppintura.getNumero());
			pstm.executeUpdate();
		}
	}

	public void quitarPinturaGaleria(Pintura ppintura) throws SQLException, Exception{
		String sql ="UPDATE TPintura " +
					"SET Ced_galeria= ?, " +
					"Estado= ? " +
					"WHERE Numero = ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,"");
			pstm.setString(2,"");
			pstm.setInt(3,ppintura.getNumero());
			pstm.executeUpdate();
		}
	}

	public ArrayList<Pintura> listarPinturasNoAdquiridas() throws SQLException, Exception{
		ArrayList<Pintura> pinturasNoAdquiridas = new ArrayList<>();
		Pintura pintura = null;

		double[] dimensiones = new double[2];
		int[] duracion = new int[3];

		String sql = "SELECT * FROM TPintura WHERE NOT Numero IN (SELECT Numero FROM TPintura WHERE Estado LIKE '%Adquirida%');";

		try( java.sql.ResultSet rs = Conector.getConector().consultarSQL(sql)){
			while (rs.next()){
				dimensiones[0] = rs.getDouble("Alto");
				dimensiones[1] = rs.getDouble("Ancho");

				duracion[0] = rs.getInt("Anios_duracion");
				duracion[1] = rs.getInt("Meses_duracion");
				duracion[2] = rs.getInt("Dias_duracion");

				pintura = new Pintura(rs.getInt("Numero"), rs.getString("Nombre"), dimensiones, rs.getString("Fecha_creada"),
										duracion, rs.getString("Tecnica"), rs.getString("Famosa"), rs.getString("Historia"),
										rs.getString("Ced_pintor"));
				pinturasNoAdquiridas.add(pintura);
			}
		}
		return pinturasNoAdquiridas;
    }

	public Pintura noAdquiridaConsultarXNombre(String pnombre) throws SQLException,Exception{
		Pintura pintura = null;

		double[] dimensiones = new double[2];
		int[] duracion = new int[3];

		String sql ="SELECT * FROM TPintura WHERE Nombre LIKE '%" + pnombre + "%' "+
					"AND NOT Numero IN (SELECT Numero FROM TPintura WHERE Estado LIKE '%Adquirida%');";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				dimensiones[0] = rs.getDouble("Alto");
				dimensiones[1] = rs.getDouble("Ancho");

				duracion[0] = rs.getInt("Anios_duracion");
				duracion[1] = rs.getInt("Meses_duracion");
				duracion[2] = rs.getInt("Dias_duracion");

				pintura = new Pintura(rs.getInt("Numero"), rs.getString("Nombre"), dimensiones, rs.getString("Fecha_creada"),
										duracion, rs.getString("Tecnica"), rs.getString("Famosa"), rs.getString("Historia"),
										rs.getString("Ced_pintor"));
			}
		}
		return pintura;
	}
}