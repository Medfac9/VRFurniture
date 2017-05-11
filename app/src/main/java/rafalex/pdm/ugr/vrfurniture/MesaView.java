package rafalex.pdm.ugr.vrfurniture;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MesaView  extends LinearLayout {

    private ImageView image;

    public MesaView(Context context) {
        super(context);
        inflate(context, R.layout.mesa_view, this);

        image = (ImageView) findViewById(R.id.image_list_mesas);
    }

    //Permite establecer la mesa a mostrar
    public void setMesa(Mesa mesa) {

        image.setImageResource(mesa.getMesa());
    }
}
