package com.jamper91.Lector;

import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

@SuppressLint("NewApi")
public class DialogoCicloRuta extends DialogFragment 
{
	//Elementos graficos a usar
	EditText txtCiclo,txtRuta;
	View view;
	public interface DialogoCicloRutaListener
	{
		public void onDialogAceptarClick(DialogFragment dialog,String ciclo,String ruta);
	}
	
	DialogoCicloRutaListener mListener;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			mListener=(DialogoCicloRutaListener) activity;
		}catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
                    + " debe implementar DialogoArchivosListener");
		}
	}
	public void iniciarlizar()
	{
		txtCiclo=(EditText)view.findViewById(R.id.txtCiclo);
		txtRuta=(EditText)view.findViewById(R.id.txtRuta);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
		view=getActivity().getLayoutInflater().inflate(R.layout.dialogo_ciclo_ruta, null);
		builder.setView(view)// Set the action buttons
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) 
            {
            	iniciarlizar();
            	
         	   mListener.onDialogAceptarClick(DialogoCicloRuta.this,txtCiclo.getText().toString(),txtRuta.getText().toString());
         	   
            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //mListener.onDialogCancelarClick(DialogoCicloRuta.this);
            }
        });		
		return builder.create();
	}
	

}
