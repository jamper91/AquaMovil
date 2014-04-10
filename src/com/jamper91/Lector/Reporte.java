package com.jamper91.Lector;

import java.util.Vector;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
	
	private void inicializar()
	{
		TableLayout datos=(TableLayout)findViewById(R.id.Reporte_tblReporte);
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
	        TextView txtCiclo = new TextView(this);
	        TextView txtRuta = new TextView(this);
	        TextView txtCantidad = new TextView(this);
	        TextView txtTomadas = new TextView(this);
	        TextView txtPendientes = new TextView(this);
	        txtCiclo.setText(ciclo+"");
	        txtRuta.setText(ruta+"");
	        txtCantidad.setText(inf[0]);
	        txtTomadas.setText(inf[1]);
	        txtPendientes.setText(inf[2]);
	        
	        row.addView(txtCiclo);
	        row.addView(txtRuta);
	        row.addView(txtCantidad);
	        row.addView(txtTomadas);
	        row.addView(txtPendientes);
	        datos.addView(row);
			
		}
	}
	

}
