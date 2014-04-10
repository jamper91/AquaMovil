package com.jamper91.Administrador;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Preferencias extends Activity {

	TextView txtValidar;
	Administrador admin= Administrador.getInstance(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administrador_preferencias);
		inicializar();
	}
	private void inicializar()
	{
		txtValidar=(TextView)findViewById(R.id.administradorPreferencias_txtValidar);
	}
	
	public void ejecutar(View v)
	{ 	
		switch (v.getId()) {
		case R.id.administradorPreferencias_btnAceptar:
			int cant=Integer.parseInt(txtValidar.getText().toString());
			admin.addParametro("validar", cant+"");
			
			Log.i("ejecutar", "almacenado con exito: "+cant);
			break;

		default:
			break;
		}
	}

}
