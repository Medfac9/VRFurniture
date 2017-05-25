package rafalex.pdm.ugr.vrfurniture;

import java.io.Serializable;
import java.util.ArrayList;

public class Categoria implements Serializable {

    private ArrayList<Mueble> muebles = new ArrayList ();
    private String nombre_mueble;

    public Categoria(String nombre_mueble, ArrayList<Mueble> muebles) {

        this.nombre_mueble = nombre_mueble;
        this.muebles = muebles;
    }

    public String getNombreMueble () {

        return nombre_mueble;
    }

    public int getImgMueble() {

        return muebles.get(0).getMueble();
    }

    public ArrayList<Mueble> getMuebles () {

        return muebles;
    }

}