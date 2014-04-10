package com.jamper91.Lector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Reporte extends Activity 
{
	Administrador admin= Administrador.getInstance(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte);
		inicializar();
	}
	public <T> T[] append(T[] arr, T element) {
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
	private void inicializar()
	{
		GridView gridDatos=(GridView)findViewById(R.id.Reporte_gridDatos);
		gridDatos.setVerticalSpacing(1);
		gridDatos.setHorizontalSpacing(1);
//		final String[] datos=new String[]{"Ciclo","Ruta","Cantidad Lecturas","Tomadas","Pendientes"};
		ArrayList<String> datos=new ArrayList<String>();
		datos.add("Ciclo");
		datos.add("Ruta");
		datos.add("Cantidad");
		datos.add("Tomadas");
		datos.add("Pendientes");
		//Obtengo todos los planesLectura del  usuario logeado
		Vector<String[]> planes=admin.getPlanLecturasByLogin(admin.getLogin());
		Log.i("inicializar", planes.toString());
		for (String[] plan : planes) 
		{
			//Obtengo el ciclo y ruta y consulto lo necesario
			String ciclo=plan[0];
			String ruta=plan[1];
			String inf[]=admin.getInformacionRuta(ciclo,ruta);
			TableRow row= new TableRow(this);
	        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
	        row.setLayoutParams(lp);
	        datos.add(ciclo);
	        datos.add(ruta);
	        datos.add(inf[0]);
	        datos.add(inf[1]);
	        datos.add(inf[2]);
	        
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, datos);
        gridDatos.setAdapter(adapter);
	}
	

}
