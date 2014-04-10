package com.jamper91.Lector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jamper91.servicios.R;

public class DialogoValidar extends DialogFragment 
{
	//Elementos graficos a usar
		EditText txtNumeroContador;
		View view;
		public interface DialogoValidarListener
		{
			public void onDialogValidarAceptarClick(DialogFragment dialog,String numeroContador);
		}
		DialogoValidarListener mListener;
		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			try{
				mListener=(DialogoValidarListener) activity;
			}catch(ClassCastException e)
			{
				throw new ClassCastException(activity.toString()
	                    + " debe implementar DialogoValidarListener");
			}
		}
		public void iniciarlizar()
		{
			txtNumeroContador=(EditText)view.findViewById(R.id.DialogoValidar_txtNumeroContador);
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
			view=getActivity().getLayoutInflater().inflate(R.layout.dialogo_validar, null);
			builder.setView(view)// Set the action buttons
	        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int id) 
	            {
	            	iniciarlizar();
	            	
	         	   mListener.onDialogValidarAceptarClick(DialogoValidar.this,txtNumeroContador.getText().toString());
	         	   
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
