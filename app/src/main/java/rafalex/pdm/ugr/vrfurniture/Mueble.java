package rafalex.pdm.ugr.vrfurniture;

import java.io.Serializable;

public class Mueble implements Serializable {

    private int mueble;

    public Mueble(int mueble) {

        this.mueble = mueble;
    }

    public int getMuebles() {

        return mueble;
    }

}
