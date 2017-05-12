package rafalex.pdm.ugr.vrfurniture;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoriaView extends LinearLayout {

    private ImageView image;
    private TextView text;

    public CategoriaView(Context context) {
        super(context);
        inflate(context, R.layout.categoria_view, this);

        image = (ImageView) findViewById(R.id.categoria_image);
        text = (TextView) findViewById(R.id.categoria_text);
    }

    //Permite establecer el mueble a mostrar
    public void setCategoria(Categoria categoria) {

        image.setImageResource(categoria.getImgMueble());
        text.setText(categoria.getNombreMueble());
    }
}
