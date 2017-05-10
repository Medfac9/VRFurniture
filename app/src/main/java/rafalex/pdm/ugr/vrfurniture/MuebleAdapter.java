package rafalex.pdm.ugr.vrfurniture;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MuebleAdapter extends BaseAdapter {

    //ArrayList con todos los Mueble a mostrar
    private ArrayList<Mueble> muebles;

    public MuebleAdapter(ArrayList<Mueble> muebles) {
        this.muebles = muebles;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }

    //Devuelve el numero de elementos
    public int getCount() {
        return muebles.size();
    }

    //Devuelve el elemento de una posición
    public Object getItem(int position) {
        return muebles.get(position);
    }

    //Devulve el ID del elemento (Generalmente no se usa)
    public long getItemId(int position) {
        return position;
    }

    //Devuelve la vista de un elemento
    public View getView(int position, View convertView, ViewGroup parent) {

        //Si el contentView ya tiene un device, lo reutilizaremos con los nuevos datos
        // Si no crearemos uno nuevo
        MuebleView view;
        if (convertView == null)
            view = new MuebleView(parent.getContext());
        else
            view = (MuebleView) convertView;

        //Asignamos los valores del Device a mostrar
        view.setDibujo(muebles.get(position));

        return view;
    }
}
