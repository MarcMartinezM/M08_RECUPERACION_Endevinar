package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import android.net.Uri;

public class Usuarios implements Comparable<Usuarios>{
    public String nombreUser;
    public int numFallos;
    public String photoPath;
    public int foto_default;

    public Usuarios(String nombreUser, int numFallos, String photoPath,int foto_default) {
        super();
        this.nombreUser = nombreUser;
        this.numFallos = numFallos;
        this.photoPath=photoPath;
        this.foto_default=foto_default;
    }
    public Usuarios() {


    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getFoto_default() {
        return foto_default;
    }

    public void setFoto_default(int foto_default) {
        this.foto_default = foto_default;
    }

    public String getNombreUser() {
        return nombreUser;
    }
    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }
    public int getNumFallos() {
        return numFallos;
    }
    public void setNumFallos(int numFallos) {
        this.numFallos = numFallos;
    }
    public int compareTo(Usuarios usuarios){return this.numFallos-usuarios.numFallos;}

    public String getPhotoPath() {
        return photoPath;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "nombreUser='" + nombreUser + '\'' +
                ", numFallos=" + numFallos +
                ", photoPath='" + photoPath + '\'' +
                ", foto_default=" + foto_default +
                '}';
    }
}
