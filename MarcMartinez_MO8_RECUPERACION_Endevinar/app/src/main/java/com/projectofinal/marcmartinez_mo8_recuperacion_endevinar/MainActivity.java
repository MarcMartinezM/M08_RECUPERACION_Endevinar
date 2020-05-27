package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Dialog.dialogListener {
    int numeroRandom;
    int intentos=0;
    static List<Usuarios> arrayRanking = new ArrayList<Usuarios>();
     TextView titulo,intentos_total,falloacierto;
     EditText imput_numero;
    Button buttonComporbar, buttonRanking;
    boolean guardar=false;
    File dir = new File(Environment.getExternalStorageDirectory()+File.separator+Dialog.RUTA_IMAGEN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(arrayRanking.size()==0){
            loadInfo();
        }
        if(!dir.exists()){
            dir.mkdir();
        }
        numeroRandom =new Random().nextInt(100)+1;
         titulo=(TextView) findViewById(R.id.Titulo);
         imput_numero=(EditText) findViewById(R.id.imputid);
        intentos_total=(TextView) findViewById(R.id.intentos);
         buttonComporbar =(Button) findViewById(R.id.comprobar);
         buttonRanking = (Button) findViewById(R.id.Ranking);
         buttonRanking.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent pagRanking = new Intent(MainActivity.this,Ranking.class);
                 startActivity(pagRanking);
             }
         });
        buttonComporbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imput_numero.getText().toString().equals("")){
                    intentos++;
                    intentos_total.setText("intentos: "+intentos);
                }else{
                    comprobarNumero();
                }
            }
        });
    }




    public void comprobarNumero(){
        Log.i("OCULTO ","numero :"+numeroRandom);
        falloacierto=(TextView) findViewById(R.id.falloOacierto);
        int num=Integer.parseInt( imput_numero.getText().toString());
        if(num==numeroRandom){
            falloacierto.setText("!has ganado¡");
            openDialog();
            if(guardar==true){
                guardarInfo();
            }
            numeroRandom=new Random().nextInt(100)+1;
            intentos=0;
            guardar=false;
            imput_numero.setText("");

        }else if(num==0){
            falloacierto.setText("No has introduido datos!");
            intentos++;
            imput_numero.setText("");
        }else{
            if(num>numeroRandom){
                falloacierto.setText("el numer es menor!");
                intentos++;
                imput_numero.setText("");
            }else{
                falloacierto.setText("El numero es mayor!");
                intentos++;
                imput_numero.setText("");
            }
        }
        intentos_total.setText("intentos: "+intentos);

    }

    public void openDialog(){
        Dialog dd = new Dialog();
        dd.show(getSupportFragmentManager(),"dialog");
    }
    private void loadInfo(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("persistence.txt")));
            String linia;
            while((linia = br.readLine())!=null){
                arrayRanking.add(new Usuarios(linia.split(";")[0],Integer.parseInt(linia.split(";")[1]),linia.split(";")[2]));
            }
            br.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
private void guardarInfo(){
    try {
        OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("persistence.txt", Context.MODE_PRIVATE));
        for(int i=0;i<arrayRanking.size();i++){
            Log.i("arrayRanking",arrayRanking.toString());
            osw.write(arrayRanking.get(i).getNombreUser()+";"+arrayRanking.get(i).numFallos+";"+arrayRanking.get(i).getPhotoPath());
            osw.append("\r\n");
        }

        osw.close();
    }catch (Exception e){
        e.printStackTrace();
    }

}
    @Override
    public void applyText(String nombre,String nombrefoto,String pach,boolean seCrea) {
        Log.i("nombre",nombre);
        Log.i("Nombre foto",nombrefoto);
        Log.i("ruta foto",pach);
        if(seCrea==true){
            guardar=seCrea;
            arrayRanking.add(new Usuarios(nombre,intentos, pach));
        }
        }

}
