package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    private ImageView foto;
    private EditText input_name;
    private Button botonOk,botonFoto;
    private dialogListener listener;
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog,null);
        builder.setView(view)
                .setTitle("Datos")
        .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        foto = view.findViewById(R.id.foto);
        input_name = view.findViewById(R.id.input_nombre);
        botonFoto = view.findViewById(R.id.butonCamera);

        botonOk = view.findViewById(R.id.butonok);
        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String nombre = input_name.getText().toString();
                Log.i("nombre",nombre);
              if(nombre.equals("") || nombre.equals(null)){
                  Toast toast = Toast.makeText(getActivity(), "No puedes dejar el nombre en blanco", Toast.LENGTH_LONG);

              }else{
                  listener.applyText(nombre);
                  dismiss();
              }

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        try {
            listener = (dialogListener)context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"must implement dialogListener");
        }

    }

    public interface  dialogListener{
        void applyText(String nombre);
    }
}
