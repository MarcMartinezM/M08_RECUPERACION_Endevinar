package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class Adapter_ranking extends BaseAdapter {
    private Context context;
    private static List<Usuarios> arrayRanking;

    public Adapter_ranking(Context context, List<Usuarios> arrayRanking) {
        super();
        this.arrayRanking= arrayRanking;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayRanking.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayRanking.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_ranking,null);
        TextView Nombre = convertView.findViewById(R.id.text_name);
        TextView intentos = convertView.findViewById(R.id.text_tries);
        ImageView foto = convertView.findViewById(R.id.imageView2);

        Nombre.setText(arrayRanking.get(position).getNombreUser());
        intentos.setText(String.valueOf(arrayRanking.get(position).getNumFallos()));
        File f = new File(arrayRanking.get(position).getPhotoPath());
        if(f.exists()){
            Uri uri = Uri.parse(arrayRanking.get(position).getPhotoPath());
            //Bitmap bitmap = BitmapFactory.decodeFile(arrayRanking.get(position).getPhotoPath());
            foto.setImageURI(uri);
        }else{
            foto.setImageResource(arrayRanking.get(position).getFoto_default());
        }


        return convertView;
    }
}
