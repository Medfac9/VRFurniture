package rafalex.pdm.ugr.vrfurniture;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Mueble implements Serializable {

    private int muebles;

    public Mueble(int mueble) {

        this.muebles = mueble;
    }

    public int getMuebles() {

        return muebles;
    }

}
