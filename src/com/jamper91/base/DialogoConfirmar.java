package com.jamper91.base;

import com.jamper91.Administrador.DialogoArchivos.DialogoArchivosListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

@SuppressLint({ "NewApi", "ValidFragment" })
public class DialogoConfirmar extends DialogFragment
{
	public DialogoConfirmar(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	private String mensaje="";
	public interface DialogoConfirmarListener
	{
		public void onDialogAceptarClick(DialogFragment g);
		public void onDialogCancelarClick(DialogFragment g);
	}
	
	DialogoConfirmarListener mListener;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			mListener=(DialogoConfirmarListener) activity;
		}catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
                    + " debe implementar DialogoArchivosListener");
		}
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.mensaje)
               .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mListener.onDialogAceptarClick(DialogoConfirmar.this);
                   }
               })
               .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       mListener.onDialogCancelarClick(DialogoConfirmar.this);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
	}

}
