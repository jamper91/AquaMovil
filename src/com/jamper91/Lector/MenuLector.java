package com.jamper91.Lector;

import java.util.ArrayList;
import java.util.Vector;

import com.jamper91.Administrador.MenuAdmin;
import com.jamper91.Lector.DialogoCicloRuta.DialogoCicloRutaListener;
import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuLector extends Activity implements DialogoCicloRutaListener {
	//Administrador
	Administrador admin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_lector);
		admin=Administrador.getInstance(null);
	}
	
	
	@SuppressLint("NewApi")
	public void menu(View v)
	{
		switch (v.getId()) {
		case R.id.imgLecturas:
				//Muestro un dialogo donde pido el ciclo y la ruta
				DialogFragment dia= new DialogoCicloRuta();
				dia.show(getFragmentManager(), "DialogoCicloRutaListener");
			break;
		case R.id.imgEncuestas:
				Intent intent=new Intent(this, Reporte.class);
				startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	// Evento que se llama cuando el usuario le da clic en el boton Cargar del
	// dialogo
	@Override
	public void onDialogAceptarClick(DialogFragment dialog,String ciclo, String ruta) 
	{
		try
		{
			//Valido que este usuario pueda realizar la operacion
			if(validar(ciclo, ruta))
			{
				//Me encargo de buscar en la base de datos la primera matricula a leer y la cantidad de elementos
				Vector<String> aux=admin.getLecturasByCicloRuta(Integer.parseInt(ciclo), Integer.parseInt(ruta));
				if(aux!=null)
				{
					String cantidad=aux.get(0);
					String matricula=aux.get(1);
					
					Intent i = new Intent(this, Lectura.class);
					i.putExtra("cantidad", cantidad);
					i.putExtra("matricula", matricula);
					startActivity(i);
				}
			}else{
				dialogo("Los datos que intenta cargar para lecturas no están asignados al usuario, verifique el plan de lecturas con el administrador","Error");
			}
		}catch(Exception e)
		{
			dialogo("Los datos que intenta cargar para lecturas no están asignados al usuario, verifique el plan de lecturas con el administrador","Error L-ML-oDAC");
		}
		
		
		
		
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
	public boolean validar(String ciclo, String ruta)
	{
		Vector<String> planLecturas=admin.getPlanLecturasByCicloRuta(ciclo, ruta);
		if(admin.getLogin().equals(planLecturas.get(2))==true)
			return true;
		else
			return false;
	}

}
