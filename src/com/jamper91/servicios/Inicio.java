package com.jamper91.servicios;

import com.jamper91.Administrador.MenuAdmin;
import com.jamper91.Lector.MenuLector;
import com.jamper91.base.Administrador;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inicio extends Activity {

	// Declaracion de elementos a usar
	private EditText txtUsuario, txtClave;
	private Button btnIngresar;

	// Clase generica, con funciones de utiliad
	Administrador admin = Administrador.getInstance(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_inicio);
		// Inicializamos los objetos
		inicializar();
	}
	@Override
	public void onResume()
	{
		super.onResume();
		limpiarCampos();
	}

	private void inicializar() {
		txtUsuario = (EditText) findViewById(R.id.txtUsuario);
		txtClave = (EditText) findViewById(R.id.txtClave);
		btnIngresar = (Button) findViewById(R.id.btnIngresar);

	}

	private void limpiarCampos() {
		// Limpio los campos de usuario y clave
		txtUsuario.setText("");
		txtClave.setText("");
	}

	public void ingresar(View v) {
		String login, clave;
		login = txtUsuario.getText().toString();
		clave = txtClave.getText().toString();
		// Verifico que la constraseña y el usuario no esten vaciones
		if (login != "" && clave != "") {
			// Verifico si existe un usuario con ese login y clave
			if (admin.logear(login, clave) != null) {
				// Actualizo el rol y login
				admin.setLogin(login);
				// Roles del sistema
				String[] roles = getResources().getStringArray(R.array.Roles);
				// Obtengo el rol del usuario
				String rol = admin.getRol(login);
				// Actualizo el rol y login
				admin.setLogin(login);
				admin.setRol(rol);
				// Si el usuario es rol Super o Admin
				if (rol.equals(roles[0]) || rol.equals(roles[1])) {
					Intent i = new Intent(this, MenuAdmin.class);
					startActivity(i);
				} else if (rol.equals(roles[2])) {
					Intent i = new Intent(this, MenuLector.class);
					startActivity(i);
				} else {
					dialogo("El modulo para " + rol + " aun no ha sido implementado", "Error");
				}
			} else {
				dialogo("Usuario y/o contraseña incorrectos", "Error");
			}
		} else {
			dialogo("Ningun campo puede estar vacio", "Error");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
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
