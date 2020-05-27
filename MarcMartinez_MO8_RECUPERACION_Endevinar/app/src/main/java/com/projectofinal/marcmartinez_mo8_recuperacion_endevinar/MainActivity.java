package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Dialog.dialogListener {
    int numeroRandom;
    int intentos=0;
     TextView titulo,intentos_total,falloacierto;
     EditText imput_numero;
    Button buttonComporbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        numeroRandom =new Random().nextInt(100)+1;
         titulo=(TextView) findViewById(R.id.Titulo);
         imput_numero=(EditText) findViewById(R.id.imputid);
        intentos_total=(TextView) findViewById(R.id.intentos);
         buttonComporbar =(Button) findViewById(R.id.comprobar);
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

    @Override
    public void applyText(String nombre) {

    }
}
