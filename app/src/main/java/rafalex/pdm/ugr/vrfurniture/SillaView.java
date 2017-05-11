package rafalex.pdm.ugr.vrfurniture;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SillaView  extends LinearLayout {

    private ImageView image;

    public SillaView(Context context) {
        super(context);
        inflate(context, R.layout.silla_view, this);

        image = (ImageView) findViewById(R.id.image_list_sillas);
    }

    //Permite establecer el silla a mostrar
    public void setSilla(Silla silla) {

        image.setImageResource(silla.getSilla());
    }
}
