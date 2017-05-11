package rafalex.pdm.ugr.vrfurniture;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MuebleView extends LinearLayout {

    private ImageView image;

    public MuebleView(Context context) {
        super(context);
        inflate(context, R.layout.mueble_view, this);

        image = (ImageView) findViewById(R.id.image_list);
    }

    //Permite establecer el mueble a mostrar
    public void setMueble(Mueble mueble) {

        image.setImageResource(mueble.getMuebles());
    }
}
