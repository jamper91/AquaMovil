package com.jamper91.Lector;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import com.jamper91.Administrador.DialogoArchivos;
import com.jamper91.Lector.DialogoCausal.DialogoCausalListener;
import com.jamper91.Lector.DialogoObservaciones.DialogoObservacionesListener;
import com.jamper91.Lector.DialogoValidar.DialogoValidarListener;
import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Lectura extends Activity implements DialogoCausalListener,DialogoObservacionesListener, DialogoValidarListener {

	//Elementos que recibe este intent
	private String cantidad, matricula;
	//Variables necesarias para los botones siguiente, anterior
	private int ciclo,ruta,id;
	
	private Administrador admin = Administrador.getInstance(this);
	
	//Variable para determinar si tengo que hacer una validacion al usuario
	private boolean validar=false;
	/**
	 * Elementos de la interfaz grafica
	 */
	EditText txtLectura;
	TextView txtCantidad, txtMatricula, txtEnrutamiento, txtDireccion,lblCausal,txtnumContador,txttipoContador,lblObservacion;
	ImageButton btnRegistrar, btnAnterior, btnSiguiente,btnSalir;
	ImageView btnCausal, btnObservacion;

	/**
	 * Elementos que actualizaran la interfaz grafica
	 * 
	 */
	String Dlectura,DCantidad,DMatricula,DEnrutamiento,DDireccion,DCausal,DNumContador,DTipoContador,DObservaciones,posicion;
	//Elementos a enviar al dialogo que crear las causales
	private String enrutamiento,rutaFoto=null,causal=null;
	
	//Elementos para las observaciones
	private String ob1=null,fob1=null,ob2=null,fob2=null,ob3=null,fob3=null;
	
	//Elementos generales
	int consumoMedio=0,lecturaAnterior=0,lecturaMaxima=0;
	//Esta variable es para determiar cuantas veces se ha ingresado mal la lectura
	int lecturaError=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lectura);
		// Obtengo las variables del intent
		Bundle bundle = getIntent().getExtras();
		cantidad = bundle.getString("cantidad");
		matricula = bundle.getString("matricula");
		//Determino si tengo que hacer una validacion
		
		// inicializo los elementos necesarios
		inicializar();
		consultar();
		validar();
		if(!validar)
			actualizarGUI();
		else{
			//Hago la validacion
			hacerValidacion();
		}
		

	}
	private void hacerValidacion()
	{
		//Muestro un dialogo donde pido el numero del contador
		DialogFragment dia= new DialogoValidar();
		dia.show(getFragmentManager(), "DialogoValidarListener");
	}
	private void validar()
	{
		
		try {
			int dato = Integer.parseInt(admin.getParametroByNombre("validar"));
			Log.i("validar", "Datos: " + dato);
			if (dato != 0) {
				//Determino si este elemento es multiplo del de validar
				int pos = Integer.parseInt(posicion);
				Log.i("validar", "Datos: " + dato + " --Pos: " + pos);
				if (pos % dato == 0) {
					validar = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("validar", e.toString());
		}

	}
	private void inicializar() 
	{
		
		txtMatricula = (TextView) findViewById(R.id.Lectura_txtMatricula);
		txtEnrutamiento = (TextView) findViewById(R.id.Lectura_txtEnrutamiento);
		txtDireccion = (TextView) findViewById(R.id.Lectura_txtDireccion);
		txtLectura = (EditText) findViewById(R.id.Lectura_txtLectura);
		btnCausal = (ImageView) findViewById(R.id.Lectura_btnCausal);
		btnObservacion = (ImageView) findViewById(R.id.Lectura_btnObservacion);
		txtCantidad = (TextView) findViewById(R.id.Lecturas_txtCantidad);
		txtCantidad.setText("1/" + cantidad);
		btnAnterior = (ImageButton) findViewById(R.id.Lectura_btnAnterior);
		btnRegistrar = (ImageButton) findViewById(R.id.Lectura_btnRegistrar);
		btnSiguiente = (ImageButton) findViewById(R.id.Lectura_btnSiguiente);
		lblCausal=(TextView)findViewById(R.id.Lectura_lblCausal);
		txtnumContador=(TextView)findViewById(R.id.Lectura_NumContador);
		txttipoContador=(TextView)findViewById(R.id.Lectura_TipoContador);
		lblObservacion=(TextView)findViewById(R.id.Lectura_lblObservacion);
		btnSalir=(ImageButton)findViewById(R.id.Lectura_btnSalir);
	}

	// Esta funcion se encarga de consultar en la base de datos la informacion
	// necesaria del elemento a actualizar
	private void consultar() 
	{
		try{
			Vector<String> lectura = admin.getLecturaMatricula(matricula);
			Log.i("consultar", "lectura: "+lectura.toString());
			if (lectura != null) {
			
				//Actualizo la para que el usuario la vea
				
				try
				{
					DMatricula=lectura.get(0);
				}catch(Exception e)
				{
					DMatricula="Error";
				}
				
				try {
					DEnrutamiento=lectura.get(1) + "-" + lectura.get(2) + "-"+ lectura.get(3);
					
				} catch (Exception e1) {
					DEnrutamiento="Error";
				}
				try {
					DDireccion=lectura.get(4);
					
				} catch (Exception e1) {
					DDireccion="Error";
				}
				
				try {
					Dlectura=lectura.get(12);
					
				} catch (Exception e1) {
					Dlectura="Error";	
				}
				
				try {
					DNumContador=lectura.get(5);
					
				} catch (Exception e1) {
					DNumContador="Error";
				}
				try {
					DTipoContador=lectura.get(6);
					
				} catch (Exception e1) {
					DTipoContador="Error";
				}
				//Actualizo las variables globales de ciclo y ruta;
				try {
					this.ruta=Integer.parseInt(lectura.get(2));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					this.ciclo=Integer.parseInt(lectura.get(1));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					this.id=Integer.parseInt(lectura.get(24));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					this.causal=lectura.get(16).toString();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					this.causal=null;
				}
				
				//Para determinar si esta lectura ya tiene un causal
				if(causal!=null)
				{
					lblCausal.setText("Causal(1)");
				}else
					causal=null;
				/*
				if(enru!="")
					this.enrutamiento=enru;
				else
					this.enrutamiento=null;
				//Es para mostrar la foto del causal
				try {
					this.rutaFoto=lectura.get(17);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					this.rutaFoto="";
				}
				if(rutaFoto=="")
					this.rutaFoto=null;
				*/
				try {
					this.consumoMedio=Integer.parseInt(lectura.get(8));
					Log.i("Lectura consumo medio", consumoMedio+"");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.e("Error en el consumo medio", e.getMessage());
					this.consumoMedio=0;
				}
				try {
					this.lecturaAnterior=Integer.parseInt(lectura.get(7));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					this.lecturaAnterior=0;
				}
				//Obtengo la lectura mexima del medido especificado
				try {
					this.lecturaMaxima=Integer.parseInt(admin.getTipoMedidor(txttipoContador.getText().toString()).get(1));
				} catch (Exception e) {
					// TODO: handle exception
					this.lecturaMaxima=0;
				}
				//Obtengo las observaciones
				try {
					int cantO=0;
					if(!lectura.get(13).equals("0")){
						this.ob1=lectura.get(13);
						cantO++;
					}
					if(!lectura.get(14).equals("0")){
						this.ob2=lectura.get(14);
						cantO++;
					}
					if(!lectura.get(15).equals("0")){
						this.ob3=lectura.get(15);
						cantO++;
					}
					this.lblObservacion.setText("Observacion("+cantO+")");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				//Consulto la posicion de esta matricual entre el resto del mismo cilo ruta
				try {
					posicion=admin.getPosicionUltimaLecturaEditada(String.valueOf(this.id), this.ciclo, this.ruta);
					posicion=Integer.parseInt(posicion)+1+"";
					this.txtCantidad.setText(posicion+"/"+cantidad);
				} catch (Exception e) {
					// TODO: handle exception
					this.txtCantidad.setText("x/"+cantidad);
					Log.e("consultar", e.toString());
				}
				
			}
		}catch(Exception e)
		{
			Toast.makeText(Lectura.this, "Eror consultado la matricula: "+e.toString(), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	/**
	 * Esta funcion se encarga de mostrar los datos consultados en la interfas grafica
	 */
	private void actualizarGUI()
	{
		txtMatricula.setText(DMatricula);
		txtEnrutamiento.setText(DEnrutamiento);
		txtDireccion.setText(DDireccion);
		txtLectura.setText(Dlectura);
		txtnumContador.setText(DNumContador);
		txttipoContador.setText(DTipoContador);
	}
	
	@SuppressLint("NewApi")
	public void accion(View v) {
		switch (v.getId()) {
		case R.id.Lectura_btnAnterior:
				anterior();
			break;
		case R.id.Lectura_btnRegistrar:
			registrar();
			break;
		case R.id.Lectura_btnSiguiente:
				siguiente();
			break;
		case R.id.Lectura_btnCausal:
			//admin.cerrarConexionBD();
			// Muestro el cuadro de dialogo
			DialogFragment dA= DialogoCausal.newInstance(this.enrutamiento,this.causal,this.rutaFoto);
			dA.show(getFragmentManager(), "DialogoCausalListener");

			break;
		case R.id.Lectura_btnObservacion:
			DialogFragment dO= DialogoObservaciones.newInstance(ob1, ob2, ob3, enrutamiento);
			dO.show(getFragmentManager(), "DialogoObservacionesListener");
			break;
		
		case R.id.Lectura_btnSalir:
			Intent i= new Intent(this, MenuLector.class);
			startActivity(i);
		default:
			break;
		}
	}
	
	private void anterior()
	{
		//Obtengo la matricula de la anterior lectura
		
		matricula=admin.getPrevLectura(id, ciclo, ruta);
		if(matricula!=null)
		{
			Log.i("anterior-matricula", matricula);
			Intent i = new Intent(this, Lectura.class);
			i.putExtra("cantidad", cantidad);
			i.putExtra("matricula", matricula);
			startActivityForResult(i, 48);
		}else{
			Log.i("anterior", "No hay anterior");
		}
		
		//startActivity(i);
	}
	private void siguiente()
	{
		//Obtengo la matricula de la anterior lectura
		
		matricula=admin.getNextLectura(id, ciclo, ruta);
		if(matricula!=null)
		{
			Log.i("sigr-matricula", matricula);
			Intent i = new Intent(this, Lectura.class);
			i.putExtra("cantidad", cantidad);
			i.putExtra("matricula", matricula);
			startActivityForResult(i, 48);
		}else{
			Log.i("anterior", "No hay siguiente");
			dialogo("Recorrido terminado, se tomaron "+cantidad+" lecturas de "+cantidad, "Lecturas");
		}
		
		//startActivity(i);
	}

	private void registrar() {
		// Tom los datos a usar
		int nuevoCiclo, nuevaRuta, nuevoConsecutivo,nuevaLectura;
		Date fecha;
		nuevoCiclo=this.ciclo;
		nuevaRuta=this.ruta;
		nuevoConsecutivo=-1;
		try
		{
			if(txtLectura.getText().toString().length()>=0)
			{
				nuevaLectura=Integer.parseInt(txtLectura.getText().toString());
				String validarL=validarLectura(nuevaLectura);
				Log.i("validas",validarL);
				if(validarL.equals("ok") || nuevaLectura==lecturaError)
				{
					if(rutaFoto==null)
						rutaFoto="";
					if(causal==null)
						causal="0";
					if(ob1==null)
						ob1="0";
					if(ob2==null)
						ob2="0";
					if(ob3==null)
						ob3="0";
					Vector<String> co=posicion();
					if(admin.updateLectura(this.matricula, nuevoCiclo, nuevaRuta, nuevoConsecutivo, nuevaLectura,Integer.parseInt(ob1), Integer.parseInt(ob2), Integer.parseInt(ob3), Integer.parseInt(this.causal), this.rutaFoto, new Date(), co.get(0), co.get(1), co.get(2), 0, admin.getLogin()))
					{
//						dialogo("Lectura registrada", "Registro");	
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User clicked OK button
					        	   siguiente();
					           }
					       });

						// 2. Chain together various setter methods to set the dialog characteristics
						builder.setMessage("Lectura registrada")
						       .setTitle("Registro");

						// 3. Get the AlertDialog from create()
						AlertDialog dialog = builder.create();
						dialog.show();
					}else{
						//Toast.makeText(this, "Error al actualizar el registro", Toast.LENGTH_SHORT).show();
						dialogo("Error al actualizar el registro","Error");
					}
				}else{
//					Toast.makeText(this,"Verificar lectura del predio" , Toast.LENGTH_SHORT).show();
					dialogo("Verificar lectura del predio", "Error");
					txtLectura.setText("");
					lecturaError=nuevaLectura;
				}
				
			}else{
				//Toast.makeText(this, "Error al actualizar el registro: la lectura no puede estar vacia ", Toast.LENGTH_SHORT).show();
				dialogo("Error al actualizar el registro: la lectura no puede estar vacia ", "Error");
			}
			
		}catch(Exception e)
		{
			Toast.makeText(this, "Error al actualizar el registro: "+e.toString(), Toast.LENGTH_SHORT).show();
			dialogo("Error al actualizar el registro: "+e.toString(), "Error");
		}
		

	}

	@Override
	public void onDialogAceptarClick(DialogFragment dialog, String causal1,
			String rutaFoto1) {
		// TODO Auto-generated method stub
		this.causal=causal1;
		this.rutaFoto=rutaFoto1;
		if(causal!= null && rutaFoto!=null)
			lblCausal.setText("Causal(1)");

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		Log.i("resultCode",resultCode+"");
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==48)
		{
			try {
				//Bundle bundle =data.getExtras(); 
				cantidad = data.getStringExtra("cantidad");
				matricula = data.getStringExtra("matricula");
				// inicializo los elementos necesarios
				inicializar();
				consultar();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e("onActivityRESULT",e.getMessage());
			}
		}
	}

	private String validarLectura(int lectura)
	{
		Log.i("Lectura Anterior", this.lecturaAnterior+"");
		Log.i("Consumo Medio: ",this.consumoMedio+"");
		Log.i("Lectura nueva", lectura+"");
		Log.i("lecturaMaxima", lecturaMaxima+"");
		int consumo=0;
		if(lectura>=lecturaAnterior)
			consumo=lectura-lecturaAnterior;
		if(lectura<lecturaAnterior)
			consumo=(this.lecturaMaxima-lecturaAnterior)+lectura+1;
		Log.i("Consumo nuevo", consumo+"");
		if(consumoMedio>40)
		{
			if(consumo>=(consumoMedio*1.35))
			{
				return "Consumo Alto";
			}
			if(consumo<=(consumoMedio*0.65))
			{
				return "Consumo Bajo";
			}
		}else if(consumoMedio<=40)
		{
			if(consumo>=(consumoMedio*1.65))
			{
				return "Consumo Alto";
			}
			if(consumo<=(consumoMedio*0.35))
			{
				return "Consumo Bajo";
			}
		}
		Log.i("Lectura Anterior", this.lecturaAnterior+"");
		Log.i("Consumo Medio: ",this.consumoMedio+"");
		Log.i("Lectura nueva", lectura+"");
		Log.i("Consumo nuevo", consumo+"");
		return "ok";
		
	}

	@Override
	public void onDialogAceptarClick(DialogFragment dialog, String obs1,
			String obs2, String obs3) {
		// TODO Auto-generated method stub
		//Variable para determinal la cantidad de observaciones que se agregaron
		int canO=0;
		if(obs1!=null)
			canO++;
		if(obs2!=null)
			canO++;
		if(obs3!=null)
			canO++;
		lblObservacion.setText("Observacion("+canO+")");

		Log.i("ob1", obs1+"");
		Log.i("ob2", obs2+"");
		Log.i("ob3", obs3+"");
		this.ob1=obs1;
		this.ob2=obs2;
		this.ob3=obs3;

		
	}
	private void dialogo(String men, String tit)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(men)
		       .setTitle(tit);

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	private  Vector<String>  posicion()
	{
		 Vector<String> coordenadas=new Vector<String>();
		 LocationManager locationManager;
		 locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		 Location location =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 if(location!=null)
		 {
			 coordenadas.add(location.getLatitude()+"");
			 coordenadas.add(location.getLongitude()+"");
			 coordenadas.add(location.getAltitude()+"");
		 
		 }else{
			 coordenadas.add("0");
			 coordenadas.add("0");
			 coordenadas.add("0");
		 }
		 return coordenadas;
	}
	@Override
	public void onDialogValidarAceptarClick(DialogFragment dialog,
			String numeroContador) {
		
		Log.i("onDialogValidarAceptarClick", numeroContador);
		Log.i("DNumContador", DNumContador);
		if(numeroContador.equals(DNumContador))
		{
			actualizarGUI();
		}else{
			dialogo("EL numero del contador no coincide, verifique por favor","Datos incorrectos");
		}
		
	}
	
}
