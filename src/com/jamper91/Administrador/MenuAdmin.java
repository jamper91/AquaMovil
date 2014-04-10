package com.jamper91.Administrador;

import java.util.ArrayList;
import java.util.Vector;

import com.jamper91.Administrador.DialogoArchivos.DialogoArchivosListener;
import com.jamper91.base.Administrador;
import com.jamper91.base.DialogoConfirmar;
import com.jamper91.base.DialogoConfirmar.DialogoConfirmarListener;
import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint( "NewApi")
public class MenuAdmin extends Activity implements DialogoArchivosListener,
		DialogoConfirmarListener {
	// Variables de la interfaz grafica
	private ImageView imgO1, imgO2, imgO3, imgO4;

	// Clase generica, con funciones de utiliad
	Administrador admin = Administrador.getInstance(this);

	// Variables para mostrar un cuadro de cargando
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	//Variable que almacena los archivos a cargar
	private ArrayList archivosCargar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_admin);
		updateBarHandler = new Handler();
	}

	@SuppressLint("NewApi")
	public void menu(View v) {
		Log.e("menu", "Ingresando al menu");
		switch (v.getId()) {
		// En este caso, se leer el archivo del sistema y se insertan los datos
		// en la base de datos, en tal caso, se borran los datos anteriores
		case R.id.imgOpcion1:
			// Muestro el cuadro de dialogo
			DialogFragment dA = new DialogoArchivos();
			dA.show(getFragmentManager(), "DialogoArchivosListener");

			break;
		// En este caso, se almacena la informacion de la base de datos en los
		// archivos
		case R.id.imgOpcion2:
			if (admin.generarBackUp()) {
				Toast.makeText(MenuAdmin.this, "BackUp generado con exito",
						Toast.LENGTH_SHORT).show();
				// Para que el archivo sea visible desde el explorador de
				// windows
				sendBroadcast(new Intent(
						Intent.ACTION_MEDIA_MOUNTED,
						Uri.parse("file://"
								+ Environment
										.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))));
			} else
				Toast.makeText(MenuAdmin.this, "Error al generar el BackUp",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.imgOpcion3:
			Intent intent=new Intent(this,Preferencias.class);
			startActivity(intent);
			break;
		case R.id.imgOpcion4:
			break;
		}
	}

	// Evento que se llama cuando el usuario le da clic en el boton Cargar del
	// dialogo
	@Override
	public void onDialogCargarClick(DialogFragment dialog,
			final ArrayList itemsListas) {
		archivosCargar=itemsListas;
		// Muestro el cuadro de dialogo
		DialogFragment dA = new DialogoConfirmar("Esta accion reemplazara los datos existentes, esta seguro de cargar estos archivos?");
		dA.show(getFragmentManager(), "DialogoConfirmarListener");

	}

	@Override
	public void onDialogCancelarClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDialogAceptarClick(DialogFragment g) {
		final ProgressDialog ringProgressDialog = ProgressDialog.show(
				MenuAdmin.this, "Por favor espere ...", "Cargando Archivos...",
				true);
		ringProgressDialog.setCancelable(false);
		ringProgressDialog.setCanceledOnTouchOutside(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Vector con el nombre de los archivos a buscar
					String[] arc = getResources().getStringArray(
							R.array.Archivos);
					// Cargo los archivos seleccionados
					for (Object string : archivosCargar) {
						String archivo = arc[Integer.parseInt(string.toString())];

						int cantidad=admin.leerArchivo(archivo);
						Log.e("onDialogAceptarClick", cantidad+"");
						String mensaje="";
						if(cantidad>0)
						{
							mensaje= "Cantidad de registros leidos de "+archivo+": "+cantidad;
						}else{
							mensaje="Error al leer el archivo "+archivo;
							
						}
						final String mensajeToask=mensaje;
						MenuAdmin.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								//Toast.makeText(MenuAdmin.this, mensajeToask, Toast.LENGTH_SHORT).show();
								dialogo(mensajeToask, "Archivos");
							}
						});
				        
						//Thread.sleep(2000);
					}
				} catch (Exception e) {
					Log.e("onDialogAceptarClick",e.getMessage());
				}
				ringProgressDialog.dismiss();
			}
		}).start();

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

}
