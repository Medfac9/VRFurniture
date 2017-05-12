package rafalex.pdm.ugr.vrfurniture;

import java.io.Serializable;
import java.util.ArrayList;

public class Categoria implements Serializable {

    private ArrayList<Integer> img_mueble = new ArrayList ();
    private String nombre_mueble;

    public Categoria(String nombre_mueble, ArrayList<Integer> img_mueble) {

        this.nombre_mueble = nombre_mueble;
        this.img_mueble = img_mueble;
    }

    public String getNombreMueble () {

        return nombre_mueble;
    }

    public int getImgMueble() {

        return img_mueble.get(0);
    }

    public ArrayList<Integer> getImages () {

        return img_mueble;
    }

}