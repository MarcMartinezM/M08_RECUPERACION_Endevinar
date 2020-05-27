package com.projectofinal.marcmartinez_mo8_recuperacion_endevinar;

import android.net.Uri;

public class Usuarios implements Comparable<Usuarios>{
    public String nombreUser;
    public int numFallos;
    public Uri photoPath;

    public Usuarios(String nombreUser, int numFallos, Uri photoPath) {
        super();
        this.nombreUser = nombreUser;
        this.numFallos = numFallos;
        this.photoPath=photoPath;
    }
    public Usuarios() {


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

    public String toString() {
        return "Usuarios [nombreUser=" + nombreUser + ", numFallos=" + numFallos + "]";
    }


}
