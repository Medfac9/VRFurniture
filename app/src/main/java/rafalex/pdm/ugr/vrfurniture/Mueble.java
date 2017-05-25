package rafalex.pdm.ugr.vrfurniture;

import java.io.Serializable;

public class Mueble implements Serializable {

    private int mueble;
    private String modeloMueble;
    private String texturaMueble;

    public Mueble(int mueble, String modeloMueble, String texturaMueble) {

        this.mueble = mueble;
        this.modeloMueble = modeloMueble;
        this.texturaMueble = texturaMueble;
    }

    public int getMueble() {

        return mueble;
    }

    public String getModelo() {

        return modeloMueble;
    }

    public String getTextura() {

        return texturaMueble;
    }

}
