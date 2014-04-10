package com.jamper91.Lector;

import java.io.Console;
import java.io.File;

import com.jamper91.Lector.DialogoCausal.DialogoCausalListener;
import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
@SuppressLint("NewApi")
public class DialogoObservaciones extends DialogFragment 
{
	View view;
	//Elementos de la interfaz grafica
	EditText txtOb1,txtOb2,txtOb3;
	ImageView btnOb1,btnOb2,btnOb3;
	
	//Elementos recibidos como parametros
	String obs1=null,obs2=null,obs3=null,enrutamiento;
	String path=null;
	
	
	Administrador admin = Administrador.getInstance(null);
	private final int REQUEST_CAMERA01 = 1,REQUEST_CAMERA02 = 2,REQUEST_CAMERA03 = 3;
	static DialogoObservaciones newInstance(String ob1,String ob2, String ob3, String en)
	{
		DialogoObservaciones dC=new DialogoObservaciones();
		Bundle args=new Bundle();
		args.putString("ob1", ob1);
		args.putString("ob2", ob2);
		args.putString("ob3", ob3);
		args.putString("enrutamiento", en);
		dC.setArguments(args);
		return dC;
	}

	public interface DialogoObservacionesListener {
		public void onDialogAceptarClick(DialogFragment dialog, String ob1,String ob2,String ob3);
	}
	
	DialogoObservacionesListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (DialogoObservacionesListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar DialogoObservacionesListener");
		}
	}
	public void iniciarlizar()
	{
		txtOb1 = (EditText) view.findViewById(R.id.DialogoObservacion_txtCodObs1);
		txtOb1.setText(obs1);
		txtOb2 = (EditText) view.findViewById(R.id.DialogoObservacion_txtCodObs2);
		txtOb2.setText(obs2);
		txtOb3 = (EditText) view.findViewById(R.id.DialogoObservacion_txtCodObs3);
		txtOb3.setText(obs3);
		btnOb1 = (ImageView) view.findViewById(R.id.DialogoObservacion_btnCamara1);
		btnOb2 = (ImageView) view.findViewById(R.id.imgEncuestas);
		btnOb3 = (ImageView) view.findViewById(R.id.DialogoObservacion_btnCamara3);
		btnOb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path+"01.jpg");
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(Intent.createChooser(i, "Capturar Foto"), REQUEST_CAMERA01);

    			} catch (Exception e) {

    			}
            }
        });
		btnOb2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path+"02.jpg");
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(Intent.createChooser(i, "Capturar Foto"), REQUEST_CAMERA02);

    			} catch (Exception e) {

    			}
            }
        });
		btnOb3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path+"03.jpg");
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(Intent.createChooser(i, "Capturar Foto"), REQUEST_CAMERA03);

    			} catch (Exception e) {

    			}
            }
        });
	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			Log.i("requestCode", requestCode+"");
			  
			  switch (requestCode)
		        {
		        	case REQUEST_CAMERA01:        
	                    	//obs1= path+"01.jpg";          
		            break;
		        	case REQUEST_CAMERA02:        

//	                    	obs2=path+"02.jpg";              
		            break;
		        	case REQUEST_CAMERA03:        

//	                    	obs3=path+"03.jpg";               
		            break;
		                             
		        }
		} catch (Exception e) {
			// TODO: handle exception
			
			Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
        
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		view = getActivity().getLayoutInflater().inflate(
				R.layout.dialogo_observacion, null);
		iniciarlizar();
		builder.setView(view)
				// Set the action buttons
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								//Obtengo la informacion de los cuadros de texto
								if(!txtOb1.getText().toString().equals(""))
									obs1=txtOb1.getText().toString();
								if(!txtOb2.getText().toString().equals(""))
									obs2=txtOb2.getText().toString();
								if(!txtOb3.getText().toString().equals(""))
									obs3=txtOb3.getText().toString();
								mListener.onDialogAceptarClick(DialogoObservaciones.this, obs1,obs2,obs3 );

							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
							}
						});
		return builder.create();
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obs1 = getArguments().getString("ob1");
        obs2 = getArguments().getString("ob2");
        obs3 = getArguments().getString("ob3");
        
        enrutamiento= getArguments().getString("enrutamiento");
        path=admin.getRutaSalida()+"/Observacion/Observacion-"+enrutamiento+"-";
        Log.i("path",path);
        
        
        
	}

}
