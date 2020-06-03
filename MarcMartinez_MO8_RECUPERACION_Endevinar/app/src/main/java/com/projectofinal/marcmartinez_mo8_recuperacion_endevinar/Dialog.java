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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Dialog extends AppCompatDialogFragment {
    private ImageView foto;
    private EditText input_name;
    private Button botonOk,botonFoto;
    private dialogListener listener;

    public static String CARPETA_RAIZ = "MisFotosApp/";
    public static String CARPETA_IMAGENES = "imagenes";
    public static String RUTA_IMAGEN = CARPETA_RAIZ + CARPETA_IMAGENES;
    static File Fileimagen;
    static Bitmap bitmap;
    String path;
    final int COD_FOTO = 1;
    final int COD_SELECT = 10;

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
                // takefoto();
               // TomarFoto();
                takePhoto();
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
                  listener.applyText(nombre,path,true);
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
   private void takePhoto(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(getContext().getPackageManager())!=null){
          File photofile= null;
          try {
              photofile = createPhotoFile();


          }catch (Exception e){
              e.printStackTrace();
          }

          if(photofile!=null){
              Uri photoUri = FileProvider.getUriForFile(getActivity(),"com.projectofinal.marcmartinez_mo8_recuperacion_endevinar",photofile);
              takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
              startActivityForResult(takePhotoIntent,COD_FOTO);
          }
        }
   }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == COD_FOTO && resultCode == getActivity().RESULT_OK){
            Uri uri = Uri.parse(path);
            foto.setImageURI(uri);
        }

}

    private File createPhotoFile() throws IOException {
        String timestamp = new SimpleDateFormat( "yyyyMMdd_HHmmss").format(new Date());
        String imageFile = "imagen"+timestamp;
        File storeFile = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
          imageFile,".jpg",storeFile
        );

        path = photoFile.getAbsolutePath();
        return photoFile;
   }
    /*
        public void takefoto(){
            File miFile = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
            boolean isCreate = miFile.exists();
            if(isCreate==false){
                isCreate = miFile.mkdir();
            }
            if (isCreate==true){
                Long consecituvo = System.currentTimeMillis()/1000;
                String nombrePhoto =consecituvo.toString()+".jpg";
                //ruta de la foto
                path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombrePhoto;
                Fileimagen = new File(path);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(Fileimagen));
                startActivityForResult(intent, COD_FOTO);
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
                String klk =Environment.DIRECTORY_PICTURES;
                Log.i("klk",Environment.DIRECTORY_PICTURES);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case COD_SELECT:
                Uri miPath = data.getData();
                foto.setImageURI(miPath);
                break;
            case COD_FOTO:
                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("path",""+path);
                        }
                    });
                    bitmap = BitmapFactory.decodeFile(path);
                    foto.setImageBitmap(bitmap);
                break;
        }

    }
*/


    public interface  dialogListener{
        void applyText(String nombre,String pach,boolean seCrea);
    }
}
