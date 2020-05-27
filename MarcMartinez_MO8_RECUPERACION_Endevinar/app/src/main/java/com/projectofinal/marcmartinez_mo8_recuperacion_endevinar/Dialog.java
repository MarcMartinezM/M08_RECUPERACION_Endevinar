package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

public class Dialog extends AppCompatDialogFragment {
    private ImageView foto;
    private EditText input_name;
    private Button botonOk,botonFoto;
    private dialogListener listener;
    String nombreImagen;
    final int COD_FOTO = 20;
    public static String CARPETA_RAIZ = "MisFotosApp";
    public static String CARPETA_IMAGENES = "imagenes";
    public static String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    String path;

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
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
        botonFoto = view.findViewById(R.id.butonCamera);
        botonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto();
            }
        });
        botonOk = view.findViewById(R.id.butonok);
        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String nombre = input_name.getText().toString();
                Log.i("nombre",nombre);
              if(nombre.equals("") || nombre.equals(null) ||path.equals("") || path.equals(null)){
                  Toast toast = Toast.makeText(getActivity(), "No puedes dejar el nombre en blanco/foto", Toast.LENGTH_LONG);

              }else{
                  listener.applyText(nombre,nombreImagen,path,true);
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
    public void TomarFoto() {
        nombreImagen = "";

        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada == false) {
            isCreada = fileImagen.mkdirs();
        }

        if(isCreada == true) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
            Log.i("klk",nombreImagen);
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        System.out.println(path+nombreImagen);
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = getActivity().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(getActivity(), authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent, COD_FOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == -1) {
            switch (requestCode) {
                case COD_FOTO:
                    MediaScannerConnection.scanFile(getActivity(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    foto.setImageBitmap(bitmap);

                    break;
            }
        }
    }
    public interface  dialogListener{
        void applyText(String nombre, String nombrefoto,String pach,boolean seCrea);
    }
}
