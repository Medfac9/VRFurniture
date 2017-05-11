package rafalex.pdm.ugr.vrfurniture;

import java.io.Serializable;

public class Mesa  implements Serializable {

    private int mesa;

    public Mesa(int mesa) {

        this.mesa = mesa;
    }

    public int getMesa() {

        return mesa;
    }

}