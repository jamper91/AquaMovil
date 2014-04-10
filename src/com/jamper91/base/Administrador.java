package com.jamper91.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Esta clase permite ejecutar funciones comunes para varias clases, sin
 * necesidad de instanciarlas
 * 
 * @author Jorge Moreno
 * 
 */
public class Administrador {

	private static BaseDatos bd;
	private static Administrador instancia = null;
	private static String  rutaEntrada="";
	
	private static String rutaSalida="";
	/**
	 * Almacena el nombre de todas las tablas: en este orden 0 --> Lecturas 1
	 * --> Observaciones 2 --> PlanLecturas 3 --> TiposMedidor 4 --> Usuarios 5
	 * --> Causales
	 */
	private static Vector<String> tablas = new Vector<String>();
	/**
	 * Variables que almacenan el rol del usuario actual, y el login del usuario
	 * actual
	 */
	private static String rol, login;

	private String rutaFoto,codigoCausal;
	

	/**
	 * Constructor de la clase
	 */
	private Administrador(Context c) {
		bd = new BaseDatos(c, "servicios2", null, 13);
		//Creo las tablas
		tablas.add("Lecturas");
		tablas.add("Observaciones");
		tablas.add("PlanLecturas");
		tablas.add("TiposMedidor");
		tablas.add("Usuarios");
		tablas.add("Causales");
		
		rutaEntrada =Environment.getExternalStoragePublicDirectory(
				"/AquaMovil/Entrada/").toString();
		rutaSalida=Environment.getExternalStoragePublicDirectory(
				"/AquaMovil/Salida/").toString();
	}

	/**
	 * Funcion para tener la instancia de la clase, y asi, solo tener un objeto
	 * de esta clase en todo el programa
	 * 
	 * @param c
	 *            Contexto para crear la base de datos
	 * @return La instancia del objeto
	 */
	public static Administrador getInstance(Context c) {
		if (instancia == null)
			instancia = new Administrador(c);

		return instancia;
	}
	
	public static void cerrarConexionBD()
	{
		bd.close();
	}

	/**
	 * Funcion que permite leer varios archivos de texto en el telefono y
	 * almacenar sus datos en la tabla Usuarios de la bd
	 * 
	 * @param nombre
	 *            Vector con los nombres de los archivos a leer
	 */
	public void leerArchivo(Vector<String> nombres) {

		for (String nombre : nombres) {
			leerArchivo(nombre);
		}

	}
	/**
	 * Funcion que se encarga de leer un archivo y retornar la cantidad de lineas leida
	 * @param nombre Nombre del archivo a leer
	 * @return Cantidad de filas leidas, -1 en caso de algun error
	 */
	public int leerArchivo(String nombre) {
		
//		nombre = Environment.getExternalStoragePublicDirectory(
//				Environment.DIRECTORY_DOWNLOADS).toString()
//				+ "/" + nombre;
		nombre=rutaEntrada+"/"+nombre;
		File f = new File(nombre);
		int cantidadRegistros=0;
		BufferedReader entrada;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea;
			// Leo la primera linea, para determina a cual tabla se va a
			// insertar
			String tabla = entrada.readLine();
			// Quito el ;
			tabla = tabla.substring(0, tabla.length() - 1);
			//Elimino todos los datos que hayan en esa tabla
			deleteAllElementos(tabla);
			while (entrada.ready()) {
				// Leo la linea con datos
				linea = entrada.readLine();
				// Quito el ;
				linea = linea.substring(0, linea.length() - 1);

				// Divido los campos, separados por ","
				String[] campos = linea.split(",");
				// Esta funcion se encarga de insertar los datos en la base
				// de datos
				this.addElemento(tabla, campos);
				cantidadRegistros++;

			}
			entrada.close();
			return cantidadRegistros;

		} catch (IOException e) {
			
			Log.e("leerArchivo", "Error al leer el archivo "+nombre, e);
			return -1;
		}catch(Exception e)
		{
			return -1;
		}
	}

	/**
	 * Funcion que permite generar un archivo de texto en el telefono
	 * 
	 * @param nombre
	 *            Nombre del archivo a generar
	 * @param datos
	 *            Datos que iran dentro del archivo
	 */

	public void escribirArchivo(String nombre, Vector<String> datos) {
		nombre = rutaSalida + "/"+ nombre;
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		String linea;
		try {
			fichero = new FileWriter(nombre);
			pw = new PrintWriter(fichero);
			for (String dato : datos) {
				pw.println(dato+"\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e("escribirArchivo: nombre", e.getMessage());
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Esta funcion permite insertar un elmento en la base de datos
	 * 
	 * @param elemento
	 *            Nombre de la tabla en la cual se insertaran los datos
	 * @param datos
	 *            Datos a insertar
	 */
	public void addElemento(String elemento, String[] datos) {
		
		
		if (elemento.equals(tablas.get(4))) {
			bd.addUsuarios(Integer.parseInt(datos[0]), datos[1], datos[2],
					datos[3], datos[4], datos[5]);
		} else if (elemento.equals(tablas.get(2))) {
			bd.addPlanLecturas(datos[0], datos[1], datos[2]);
		} else if (elemento.equals(tablas.get(0))) {
			bd.addLecturas(datos[0], Integer.parseInt(datos[1]),
					Integer.parseInt(datos[2]), Integer.parseInt(datos[3]),
					datos[4], datos[5], Integer.parseInt(datos[6]),
					Integer.parseInt(datos[7]), Integer.parseInt(datos[8]));
		} else if (elemento.equals(tablas.get(1))) {
			bd.addObservaciones(Integer.parseInt(datos[0]), datos[1]);
		} else if (elemento.equals(tablas.get(3))) 
		{
			Log.i("Tipos medidor", "Datos: "+ datos[0]+"-"+datos[1]);
			bd.addTiposMedidor(datos[0], datos[1]);
		} else if (elemento.equals(tablas.get(5))) {
			bd.addCausales(Integer.parseInt(datos[0]), datos[1]);
		}
	}

	/**
	 * Elimina todos los datos de todas las tablas de la base de datis
	 * 
	 * @return true si la operacion se ejecuto con exito, false en caso
	 *         contrario
	 */
	public boolean deleteAll() {
		try {
			for (String tabla : tablas) {
				deleteAllElementos(tabla);

			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}
	}

	/**
	 * Permite borrar todos los elementos de una tabla
	 * 
	 * @param tabla
	 *            Nombre de la tabla con elementos a borrar
	 * @return true, en caso de que se pudiera haber realizado la eliminacion,
	 *         false en caso contrario
	 */
	public boolean deleteAllElementos(String tabla) {
		boolean r = true;
		if (tabla.equals(tablas.get(4))) {
			r = bd.deleteAllUsuarios();
		} else if (tabla.equals(tablas.get(2))) {
			r = bd.deleteAllPlanLecturas();
		} else if (tabla.equals(tablas.get(0))) {
			r = bd.deleteAllLecturas();
		} else if (tabla.equals(tablas.get(1))) {
			r = bd.deleteAllObservaciones();
		} else if (tabla.equals(tablas.get(4))) {
			
			r = bd.deleteAllTiposMedidor();
		} else if (tabla.equals(tablas.get(5))) {
			r = bd.deleteAllCausales();
		}
		return r;
	}

	/**
	 * Permite logearse en el sistema
	 * 
	 * @param login
	 *            Login dle usuario
	 * @param clave
	 *            Clave dle usuario
	 * @return Null en caso de que no se encuentre un usuario con esas
	 *         caracteristicas, en caso contrario el login del usuario
	 */
	public String logear(String login, String clave) {
		return bd.getUsuario(login, clave);
	}

	/**
	 * Permite obtener el rol de un usuario especifico
	 * 
	 * @param login
	 *            Login del usuario a buscar
	 * @return Null si no se encuentra el usuario, en caso contrario retornara
	 *         el rol
	 */
	public String getRol(String login) {
		return bd.getRol(login);
	}

	/**
	 * Genera los archivos de salida, con los datos almacenados en las tablas
	 * 
	 * @param nombres
	 *            Vector con los nombres de los archivos a almacenar
	 * @return True si los archivos se pudieron almacenar sin ningun problema,
	 *         false en caso contrario
	 */
	public boolean generarBackUp() {
		boolean r = true;
		Log.e("generarBackUp", "Generando backUp");
		try {

			for (String tabla : tablas) {
				// Datos a almacenar

				Vector<String> datos = new Vector<String>();

				datos.add(tabla + ";");

				datos.addAll(this.getAllElementos(tabla));

				escribirArchivo(tabla + "Out.aqu", datos);

			}

			r = true;
		} catch (Exception e) {
			r = false;
			Log.e("generarBackUp", e.toString());
		}

		return r;
	}

	/**
	 * Obtiene todo los elementos de una tabla dada
	 * 
	 * @param tabla
	 * @return
	 */
	public Vector<String> getAllElementos(String tabla) {
		if (tabla.equals(tablas.get(0))) {
			return bd.getAllLecturas();
		} else if (tabla.equals(tablas.get(1))) {
			return bd.getAllObservaciones();
		} else if (tabla.equals(tablas.get(2))) {
			return bd.getAllPlanLecturas();
		} else if (tabla.equals(tablas.get(3))) {
			return bd.getAllTipoMedidor();
		} else if (tabla.equals(tablas.get(4))) {

			return bd.getAllUsuarios();

		} else if (tabla.equals(tablas.get(5))) {
			return bd.getAllCausales();
		}
		return null;
	}

	public static String getRol() {
		return rol;
	}

	public static void setRol(String rol) {
		Administrador.rol = rol;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		Administrador.login = login;
	}
	
	public Vector<String> getLecturasByCicloRuta(int ciclo, int ruta)
	{
		return bd.getLecturasByCicloRuta(ciclo, ruta);
	}
	public Vector<String> getLecturaMatricula(String matricula)
	{
		return bd.getLecturaByMatricula(matricula);
	}
	public static String getRutaEntrada() {
		return rutaEntrada;
	}

	public static void setRutaEntrada(String rutaEntrada) {
		Administrador.rutaEntrada = rutaEntrada;
	}

	public static String getRutaSalida() {
		return rutaSalida;
	}

	public static void setRutaSalida(String rutaSalida) {
		Administrador.rutaSalida = rutaSalida;
	}
	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public String getCodigoCausal() {
		return codigoCausal;
	}

	public void setCodigoCausal(String codigoCausal) {
		this.codigoCausal = codigoCausal;
	}
	public boolean updateLectura(String matricula, int nuevoCiclo, int nuevaRuta, int nuevoConsecutivo, int nuevaLectura, int ob1, int ob2, int ob3, int causal, String foto, Date fecha, String latitud, String longitud, String altitud, int inten,String login)
	{
		return bd.updateLectura(matricula, nuevoCiclo, nuevaRuta, nuevoConsecutivo, nuevaLectura, ob1, ob2, ob3, causal, foto, fecha, latitud, longitud, altitud, inten, login);
	}
	public String getUltimaLecturaEditada(int ciclo, int ruta)
	{
		return bd.getUltimaLecturaEditada(ciclo, ruta);
	}
	public String getPrevLectura(int id,int ciclo, int ruta)
	{
		return bd.getPrevLectura(id, ciclo, ruta);
	}
	public String getNextLectura(int id,int ciclo, int ruta)
	{
		return bd.getNextLectura(id, ciclo, ruta);
	}
	public Vector<String> getTipoMedidor(String tipo)
	{
		return bd.getTipoMedidor(tipo);
	}
	public Vector<String> getPlanLecturasByCicloRuta(String ciclo, String ruta) {
		return bd.getPlanLecturasByCicloRuta(ciclo,ruta);
	}
	public String getPosicionUltimaLecturaEditada(String id,int ciclo, int ruta)
	{
		return bd.getPosicionUltimaLecturaEditada(id, ciclo, ruta);
	}
	public Vector<String[]> getPlanLecturasByLogin(String login) {
		return bd.getPlanLecturasByLogin(login);
	}
	public String[] getInformacionRuta(String ciclo, String ruta) 
	{
		return bd.getInformacionRuta(ciclo, ruta);
	}
	public String getParametroByNombre(String nombre)
	{
		return bd.getParametroByNombre(nombre);
	}
	public void addParametro(String nombre, String valor) {
		bd.addParametro(nombre, valor);
	}
	

}
