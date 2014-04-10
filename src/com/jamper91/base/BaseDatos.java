package com.jamper91.base;

import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

public class BaseDatos extends SQLiteOpenHelper {

	/*
	 * public BaseDatos(Context context) { super(context, "servicios", null, 2);
	 * Log.e("BaseDatos", "Creada"); // TODO Auto-generated constructor stub }
	 */

	public BaseDatos(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		Log.e("onCreate", "Creandose por primera vez");
		String sql = "CREATE TABLE Causales (   CodigoCausal integer NOT NULL,   Descripcion text );";
		arg0.execSQL(sql);
		sql = "CREATE TABLE Lecturas (Matricula text NOT NULL,   Ciclo text,   Ruta text,   Consecutivo integer,   Direccion text,   NumMedidor text, TipoMedidor integer,   LecturaAnterior integer,   ConsumoMedio integer,   NuevoCiclo integer,   NuevaRuta integer,   NuevoConsecutivo integer,   NuevaLectura integer,   Observacion1 integer,   Observacion2 integer,   Observacion3 integer,   Causal integer,   Foto text,   FechaHora text,   Latitud text,   Longitud text,   Altitud text,   Intentos integer,   Login text,   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ) ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE Observaciones (   CodigoObservacion integer NOT NULL,   Descripcion text ) ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE PlanLecturas (   Ciclo character(255) NOT NULL,   Ruta text NOT NULL,   Login text NOT NULL )  ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE TiposMedidor (   Tipo text NOT NULL,   LecturaMaxima text ) ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE Usuarios ( id integer,  Nombre text,   Apellido text,   Login text NOT NULL,   Clave text,   Rol text ) ";
		arg0.execSQL(sql);

		// Si estoy aqui, es porque hasta ahora se va a crear la base de datos,
		// por eso voy agregar un usuario administrador
		sql = "INSERT INTO Usuarios VALUES(0, 'Super Usuario', null,'super','super','Super');";
		arg0.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.e("BaseDatos", "onUpgrade");
		// Borro todas las tablas anteriores
		String sql = "drop table Causales;";
		arg0.execSQL(sql);
		sql = "drop table Lecturas;";
		arg0.execSQL(sql);
		sql = "drop table Observaciones;";
		arg0.execSQL(sql);
		sql = "drop table PlanLecturas;";
		arg0.execSQL(sql);
		sql = "drop table TiposMedidor;";
		arg0.execSQL(sql);
		sql = "drop table Usuarios;";
		arg0.execSQL(sql);
		// Genero las nuevas tablas;

		sql = "CREATE TABLE Causales (   CodigoCausal integer NOT NULL,   Descripcion text );";
		arg0.execSQL(sql);
		sql = "CREATE TABLE Lecturas (   Matricula text NOT NULL,   Ciclo text,   Ruta text,   Consecutivo integer,   Direccion text,   NumMedidor text, TipoMedidor integer,  LecturaAnterior integer,   ConsumoMedio integer,   NuevoCiclo integer,   NuevaRuta integer,   NuevoConsecutivo integer,   NuevaLectura integer,   Observacion1 integer,   Observacion2 integer,   Observacion3 integer,   Causal integer,   Foto text,   FechaHora text,   Latitud text,   Longitud text,   Altitud text,   Intentos integer,   Login text,   id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL) ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE Observaciones (   CodigoObservacion integer NOT NULL,   Descripcion text ) ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE PlanLecturas (   Ciclo character(255) NOT NULL,   Ruta text NOT NULL,   Login text NOT NULL )  ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE TiposMedidor (   Tipo text NOT NULL,   LecturaMaxima text ) ";
		arg0.execSQL(sql);
		sql = "CREATE TABLE Usuarios ( id integer,  Nombre text,   Apellido text,   Login text NOT NULL,   Clave text,   Rol text ) ";
		arg0.execSQL(sql);

		// Si estoy aqui, es porque hasta ahora se va a crear la base de datos,
		// por eso voy agregar un usuario administrador
		sql = "INSERT INTO Usuarios VALUES(0, 'Super Usuario', null,'super','super','Super');";
		arg0.execSQL(sql);
	}

	// ################################ INSERT EN LA BASE DE DATOS
	// ################################
	/**
	 * Permite insertar un tipo de medidor en la tabla TiposMedidor
	 * 
	 * @param tipo
	 * @param lecturaMaxima
	 */
	public void addTiposMedidor(String tipo, String lecturaMaxima) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into TiposMedidor VALUES('" + tipo + "','"
				+ lecturaMaxima + "')");
	}

	/**
	 * Permite insertar una observacion en la tabla Observaciones
	 * 
	 * @param codigo
	 * @param descripcion
	 */
	public void addObservaciones(int codigo, String descripcion) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into Observaciones VALUES(" + codigo + ",'"
				+ descripcion + "')");
	}

	/**
	 * Permite insertar una causal en la tabla Causales
	 * 
	 * @param codigo
	 * @param descripcion
	 */
	public void addCausales(int codigo, String descripcion) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into Causales VALUES(" + codigo + ",'" + descripcion
				+ "')");
	}

	/**
	 * Permite agregar un PlanLenctura a la base de datos
	 * 
	 * @param ciclo
	 * @param ruta
	 * @param login
	 */
	public void addPlanLecturas(String ciclo, String ruta, String login) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into PlanLecturas VALUES('" + ciclo + "','" + ruta
				+ "','" + login + "')");
	}

	/**
	 * Funcion que me permite agregar un usuario a la tabla Usuarios
	 * 
	 * @param id
	 *            Id del usuario en la base de datos
	 * @param nombre
	 *            Nombre del usuario
	 * @param apellido
	 *            Apellidos dle usuario
	 * @param login
	 *            Login del usario
	 * @param clave
	 *            clave del usuario
	 * @param rol
	 *            Rol del usuario
	 */
	public void addUsuarios(int id, String nombre, String apellido,
			String login, String clave, String rol) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into Usuarios VALUES(" + id + ",'" + nombre + "','"
				+ apellido + "','" + login + "','" + clave + "','" + rol
				+ "');");
		db.close();

	}

	/**
	 * Inserta una lectura en la tabla Lecturas
	 * 
	 * @param matricula
	 * @param ciclo
	 * @param ruta
	 * @param consecutivo
	 * @param direccion
	 * @param numeroMedidor
	 * @param tipoMedidor
	 * @param lecturaAnterior
	 * @param consumoMedio
	 */
	public void addLecturas(String matricula, int ciclo, int ruta,
			int consecutivo, String direccion, String numeroMedidor,
			int tipoMedidor, int lecturaAnterior, int consumoMedio) {
		Log.i("consumo medio", consumoMedio+"");
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("insert into Lecturas (Matricula,Ciclo, Ruta, Consecutivo, Direccion, NumMedidor, TipoMedidor, LecturaAnterior, ConsumoMedio) VALUES('"
				+ matricula
				+ "',"
				+ ciclo
				+ ","
				+ ruta
				+ ","
				+ consecutivo
				+ ",'"
				+ direccion
				+ "','"
				+ numeroMedidor
				+ "',"
				+ tipoMedidor
				+ "," + lecturaAnterior + "," + consumoMedio + ");");
		db.close();

	}

	/**
	 * Se encarga de actualizar una lectura de la base de datos
	 * 
	 * @param matricula
	 * @param nuevoCiclo
	 * @param nuevaRuta
	 * @param nuevoConsecutivo
	 * @param nuevaLectura
	 * @param ob1
	 * @param ob2
	 * @param ob3
	 * @param causal
	 * @param foto
	 * @param fecha
	 * @param latitud
	 * @param longitud
	 * @param altitud
	 * @param inten
	 * @param login
	 * @return
	 */
	public boolean updateLectura(String matricula, int nuevoCiclo,
			int nuevaRuta, int nuevoConsecutivo, int nuevaLectura, int ob1,
			int ob2, int ob3, int causal, String foto, Date fecha, String latitud,
			String longitud, String altitud, int inten, String login) {

		Log.i("updateLectura", "nL: " + nuevaLectura);
		Log.i("updateLectura", "matricula: " + matricula);
		try {
			SQLiteDatabase db = getWritableDatabase();
			String sql = "UPDATE Lecturas SET " + "NuevoCiclo=" + nuevoCiclo
					+ ", " + "NuevaRuta=" + nuevaRuta + ", "
					+ "NuevoConsecutivo=" + nuevoConsecutivo + ","
					+ "NuevaLectura=" + nuevaLectura + "," + "Observacion1="
					+ ob1 + "," + "Observacion2=" + ob2 + "," + "Observacion3="
					+ ob3 + "," + "Causal=" + causal + "," + "Foto='" + foto
					+ "'," + "FechaHora='" + fecha.toString() + "',"
					+ "Latitud='" + latitud + "'," + "Longitud='" + longitud + "',"
					+ "Altitud='" + altitud + "'," + "Intentos=" + inten + ","
					+ "Login='" + login + "' " + "where " + "Matricula='"
					+ matricula + "'";
			Log.i("updateLectura", "sql: " + sql);
			db.execSQL(sql);
			db.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// ################################ FIN INSERT EN LA BASE DE DATOS
	// ################################

	// ################################ SELECT ALL EN LA BASE DE DATOS
	// ################################
	/**
	 * Funcion que retorna todos los tipos de medidor dle sistema
	 * 
	 * @return Un vector con todos los tipos de medidor del sistema
	 */
	public Vector<String> getAllTipoMedidor() {
		Vector<String> tiposMedidores = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM TiposMedidor;", null);
		while (c.moveToNext()) {
			tiposMedidores.add(c.getString(0) + "," + c.getString(1) + ";");
		}
		db.close();
		return tiposMedidores;

	}
	public Vector<String> getTipoMedidor(String tipo)
	{
		Vector<String> tiposMedidor= new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM TiposMedidor where Tipo="+tipo+";", null);
		while (c.moveToNext()) {
			tiposMedidor.add(c.getString(0));
			tiposMedidor.add(c.getString(1));
		}
		db.close();
		return tiposMedidor;

	}

	/**
	 * Funcion que retorna todas las observaciones del sistema
	 * 
	 * @return Retorna un vector con todas las observaciones del sistema
	 */
	public Vector<String> getAllObservaciones() {
		Vector<String> observaciones = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Observaciones;", null);
		while (c.moveToNext()) {
			observaciones.add(c.getString(0) + "," + c.getString(1) + ";");
		}
		db.close();
		return observaciones;

	}

	public Vector<String> getAllCausales() {
		Vector<String> causales = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Causales;", null);
		while (c.moveToNext()) {
			causales.add(c.getString(0) + "," + c.getString(1) + ";");
		}
		db.close();
		return causales;

	}

	public Vector<String> getAllPlanLecturas() {
		Vector<String> planes = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM PlanLecturas;", null);
		while (c.moveToNext()) {
			planes.add(c.getString(0) + "," + c.getString(1) + ","
					+ c.getString(2) + ";");
		}
		db.close();
		return planes;

	}
	public Vector<String> getPlanLecturasByCicloRuta(String ciclo, String ruta) {
		Vector<String> planes = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM PlanLecturas where Ciclo="+ciclo+" and Ruta="+ruta+";", null);
		while (c.moveToNext()) {
			planes.add(c.getString(0));
			planes.add(c.getString(1));
			planes.add(c.getString(2));
		}
		db.close();
		return planes;

	}

	/**
	 * Funcion que retorna todos los usuarios del sistema
	 * 
	 * @return Un vector con todos los usuarios deL sistema
	 */
	public Vector<String> getAllUsuarios() {
		Vector<String> usuarios = new Vector<String>();
		try {
			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery(
					"SELECT id,Nombre,Apellido,Login,Clave,Rol FROM Usuarios;",
					null);
			while (c.moveToNext()) {
				usuarios.add(c.getInt(0) + "," + c.getString(1) + ","
						+ c.getString(2) + "," + c.getString(3) + ","
						+ c.getString(4) + "," + c.getString(5) + ";");
			}
			db.close();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("getAllUsuarios", e.toString());
		}

		Log.e("getAllUsuarios Size", usuarios.size() + "");
		return usuarios;

	}

	public Vector<String> getAllLecturas() {
		Vector<String> usuarios = new Vector<String>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Lecturas;", null);
		while (c.moveToNext()) {
			usuarios.add(c.getString(0) + "," + c.getInt(1) + "," + c.getInt(2)
					+ "," + c.getInt(3) + "," + c.getString(4) + ","
					+ c.getString(5) + "," + c.getInt(6) + "," + c.getInt(7)
					+ "," + c.getInt(8) + "," + c.getInt(9) + ","
					+ c.getInt(10) + "," + c.getInt(11) + "," + c.getInt(12)
					+ "," + c.getInt(13) + "," + c.getInt(14) + ","
					+ c.getInt(15) + "," + c.getInt(16) + "," + c.getInt(17)
					+ "," + c.getInt(18) + "," + c.getInt(19) + ","
					+ c.getInt(20) + "," + c.getInt(21) + "," + c.getInt(22)
					+ "," + c.getInt(23) + "," + c.getInt(24) + ";");
		}
		db.close();
		return usuarios;

	}

	// ################################ FIN SELECT ALL EN LA BASE DE DATOS
	// ################################

	// ################################ DELETE ALL
	// ################################

	public boolean deleteAllTiposMedidor() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			db.delete("TiposMedidor", null, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean deleteAllObservaciones() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			db.delete("Observaciones", null, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean deleteAllCausales() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			db.delete("Causales", null, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean deleteAllPlanLecturas() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			db.delete("PlanLecturas", null, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean deleteAllUsuarios() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			db.delete("Usuarios", null, null);
			// Agrego el usuario con rol Super
			addUsuarios(0, "Super Usuario", null, "super", "super", "Super");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean deleteAllLecturas() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			db.delete("Lecturas", null, null);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	// ################################ FIN DELETE ALL
	// ################################

	// ################################ OTRAS CONSULTAS
	// ################################
	public String getPlanLecturas(String login) {
		String result = "";

		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery("select * from PlanLecturas where login='"
					+ login + "'", null);
			while (c.moveToNext()) {
				result = c.getString(0) + "," + c.getString(1) + ","
						+ c.getString(2);
			}
			db.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}
	}

	/**
	 * Function que retorar el usuario que conincida con los parametros
	 * 
	 * @param login
	 *            Login del usuario
	 * @param clave
	 *            Clave del usuario
	 * @return Null si no se encuentra un usuario con esos campos, en caso
	 *         contrario retornara el login del usuario
	 */
	public String getUsuario(String login, String clave) {
		String r = null;

		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery("SELECT Login FROM Usuarios WHERE Login='"
					+ login + "' and Clave='" + clave + "'", null);
			while (c.moveToNext()) {
				r = c.getString(0);
			}
			db.close();
			return r;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}
	}

	/**
	 * Retorna el rol de un usuario determinado
	 * 
	 * @param login
	 *            Identificador del usuario a buscar
	 * @return Null si no se encuentra un usario con ese campo, en caso
	 *         contrario retornara el rol del usuario
	 */
	public String getRol(String login) {
		String r = null;

		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery("SELECT Rol FROM Usuarios WHERE Login='"
					+ login + "' ", null);
			while (c.moveToNext()) {
				r = c.getString(0);
			}
			db.close();
			return r;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}
	}

	/**
	 * Retornar la cantidad de lecturas que hay en un ciclo-ruta y la matricula
	 * por la cual continuar el proceso
	 * 
	 * @param ciclo
	 * @param ruta
	 * @return
	 */
	public Vector<String> getLecturasByCicloRuta(int ciclo, int ruta) {
		Vector<String> retornar = new Vector<String>();
		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery(
					"select count(Matricula) as cantidad from Lecturas where Ciclo="
							+ ciclo + " and Ruta=" + ruta, null);
			if (c != null) {
				c.moveToFirst();
				retornar.add(c.getString(0));
				retornar.add(getUltimaLecturaEditada(ciclo, ruta));
				
			}

			db.close();
			if (retornar.size() > 0)
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}

	}

	public Vector<String> getLecturaByMatricula(String matricula) 
	{
		Log.i("getLecturaByMatricula", "Matricula: "+matricula);
		Vector<String> retornar = new Vector<String>();
		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery("select * from Lecturas where Matricula="
					+ matricula, null);
			if (c != null) {
				while (c.moveToNext()) {
					retornar.add(c.getString(0));
					retornar.add(c.getString(1));
					retornar.add(c.getString(2));
					retornar.add(c.getString(3));
					retornar.add(c.getString(4));
					retornar.add(c.getString(5));
					retornar.add(c.getString(6));
					retornar.add(c.getString(7));
					retornar.add(c.getString(8));
					retornar.add(c.getString(9));
					retornar.add(c.getString(10));
					retornar.add(c.getString(11));
					retornar.add(c.getString(12));
					retornar.add(c.getString(13));
					retornar.add(c.getString(14));
					retornar.add(c.getString(15));
					retornar.add(c.getString(16));
					retornar.add(c.getString(17));
					retornar.add(c.getString(18));
					retornar.add(c.getString(19));
					retornar.add(c.getString(20));
					retornar.add(c.getString(21));
					retornar.add(c.getString(22));
					retornar.add(c.getString(23));
					retornar.add(c.getString(24));
				}

			}
			db.close();
			Log.i("getLecturaByMatricula", "Size:"+retornar.size());
			if (retornar.size() > 0)
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}

	}

	public String getPosicionUltimaLecturaEditada(String id,int ciclo, int ruta)
	{
		String retornar = "";
		try {

			SQLiteDatabase db = getReadableDatabase();
			// Cursor c =
			// db.rawQuery("(select matricula from lecturas where nuevoCiclo is null order by id limit 2) UNION select matricula from lecturas where id<(select id from lecturas where nuevoCiclo is null order by id  limit 1)",
			// null);
			Cursor c = db.rawQuery(
					"select count(matricula) from lecturas where ciclo="
							+ ciclo + " and ruta=" + ruta
							+ " and id<"+id+" order by id limit 1", null);
			if (c != null) {
				while (c.moveToNext()) {
					retornar = c.getString(0);
				}

			}
			db.close();
			if (retornar != "")
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getPosicionUltimaLecturaEditada", e.getMessage());
			return null;
		}
	}
	
	/**
	 * Esta funcion se encarga de retornar la matricula de la proxima lectura a
	 * editar, asi como la anterior y siguiente
	 */
	public String getUltimaLecturaEditada(int ciclo, int ruta) {
		String retornar = "";
		try {

			SQLiteDatabase db = getReadableDatabase();
			// Cursor c =
			// db.rawQuery("(select matricula from lecturas where nuevoCiclo is null order by id limit 2) UNION select matricula from lecturas where id<(select id from lecturas where nuevoCiclo is null order by id  limit 1)",
			// null);
			Cursor c = db.rawQuery(
					"select matricula from lecturas where nuevoCiclo is null and ciclo="
							+ ciclo + " and ruta=" + ruta
							+ " order by id limit 1", null);
			if (c != null) {
				while (c.moveToNext()) {
					retornar = c.getString(0);
				}

			}
			db.close();
			if (retornar != "")
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUltimaLecturaEditada", e.getMessage());
			return null;
		}
	}

	public String getPrevLectura(int id, int ciclo, int ruta) {
		String retornar = "";
		try {

			SQLiteDatabase db = getReadableDatabase();
			// Cursor c =
			// db.rawQuery("(select matricula from lecturas where nuevoCiclo is null order by id limit 2) UNION select matricula from lecturas where id<(select id from lecturas where nuevoCiclo is null order by id  limit 1)",
			// null);
			Cursor c = db.rawQuery(
					"select matricula from lecturas where ciclo=" + ciclo
							+ " and ruta=" + ruta + " and id<" + id
							+ " order by id DESC limit 1", null);
			if (c != null) {
				while (c.moveToNext()) {
					retornar = c.getString(0);
				}

			}
			db.close();
			if (retornar != "")
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}
	}

	public String getNextLectura(int id, int ciclo, int ruta) {
		String retornar = "";
		try {

			SQLiteDatabase db = getReadableDatabase();
			// Cursor c =
			// db.rawQuery("(select matricula from lecturas where nuevoCiclo is null order by id limit 2) UNION select matricula from lecturas where id<(select id from lecturas where nuevoCiclo is null order by id  limit 1)",
			// null);
			Cursor c = db.rawQuery(
					"select matricula from lecturas where ciclo=" + ciclo
							+ " and ruta=" + ruta + " and id>" + id
							+ " order by id limit 1", null);
			if (c != null) {
				while (c.moveToNext()) {
					retornar = c.getString(0);
				}

			}
			db.close();
			if (retornar != "")
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}
	}
	public Vector<String[]> getPlanLecturasByLogin(String login) {
		Vector<String[]> retornar =new Vector<String[]>(); 
		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery(
					"select * from PlanLecturas where login='"+login+"'", null);
			if (c != null) {
				while (c.moveToNext()) {
					String[] pl=new String[3];
					pl[0]=c.getString(0);
					pl[1]=c.getString(1);
					pl[2]=c.getString(2);
					retornar.add(pl);
				}

			}
			db.close();
			if (retornar.size()>0)
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getUsuario", e.getMessage());
			return null;
		}
	}
	public String[] getInformacionRuta(String ciclo, String ruta) 
	{
		String[] retornar =new String[3]; 
		try {

			SQLiteDatabase db = getReadableDatabase();
			Cursor c = db.rawQuery("select a.c as c, b.t as t, c.p as p from " +
					"(select count(matricula) as c from Lecturas where ciclo="+ciclo+" and ruta="+ruta+") as a, " +
					"(select count(matricula) as t from Lecturas where ciclo="+ciclo+" and ruta="+ruta+" and nuevoCiclo is not null) as b, " +
					"(select count(matricula) as p from Lecturas where ciclo="+ciclo+" and ruta="+ruta+" and nuevoCiclo is null) as c", null);
			if (c != null) {
				Log.i("getInformacionRuta","Entre");
				while (c.moveToNext()) {
					String[] pl=new String[3];
					pl[0]=c.getString(0);
					pl[1]=c.getString(1);
					pl[2]=c.getString(2);
					retornar=pl;
				}

			}
			db.close();
			if (c!=null)
				return retornar;
			else
				return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("getInformacionRuta", e.getMessage());
			return null;
		}
	}

}
