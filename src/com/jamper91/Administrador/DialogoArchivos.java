package com.jamper91.Administrador;

import java.util.ArrayList;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DialogoArchivos extends DialogFragment {

	// Lista para almacenar los archivo seleccionados
	ArrayList itemsListas;
	
	//Elementos para mostrar dialogo de carga
	ProgressDialog barProgressDialog;
	Handler updateBarHandler;

	Administrador admin=Administrador.getInstance(null);
	
	/**
	 * Interfaz a implementar, para que la pagina que llame a este dialogo sepa cuando se da clic en aceptar 
	 */
	public interface DialogoArchivosListener{
		public void onDialogCargarClick(DialogFragment dialog, ArrayList items) ;
		public void onDialogCancelarClick(DialogFragment dialog);
	}
	DialogoArchivosListener mListener;
	 
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			mListener=(DialogoArchivosListener) activity;
		}catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
                    + " debe implementar DialogoArchivosListener");
		}
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		itemsListas=new ArrayList();
		
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		builder.setTitle("Archivos a Cargar").setMultiChoiceItems(R.array.Tablas, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which,
                    boolean isChecked) {
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                	if(admin.getRol().equals(getResources().getStringArray(R.array.Roles)[0]) && which!=5)
                	{
                		
                		((AlertDialog) dialog).getListView().setItemChecked(which, false);
                		//Toast.makeText(getActivity(), "Solo puedes seleccionar el archivo de Usuarios", Toast.LENGTH_SHORT).show();
                	}else{
                		itemsListas.add(which);
                	}
                		
                } else if (itemsListas.contains(which)) {
                    // Else, if the item is already in the array, remove it 
                	itemsListas.remove(Integer.valueOf(which));
                }
            }
        })
        // Set the action buttons
           .setPositiveButton("Cargar", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) 
               {
            	   
            	   mListener.onDialogCargarClick(DialogoArchivos.this,itemsListas);
            	   
               }
           })
           .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   mListener.onDialogCancelarClick(DialogoArchivos.this);
               }
           });
		return builder.create();
	}

}
