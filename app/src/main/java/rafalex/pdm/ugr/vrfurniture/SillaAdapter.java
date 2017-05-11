package rafalex.pdm.ugr.vrfurniture;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SillaAdapter extends BaseAdapter {

    //ArrayList con todos las Sillas a mostrar
    private ArrayList<Silla> sillas;

    public SillaAdapter(ArrayList<Silla> sillas) {
        this.sillas = sillas;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }

    //Devuelve el numero de elementos
    public int getCount() {
        return sillas.size();
    }

    //Devuelve el elemento de una posición
    public Object getItem(int position) {
        return sillas.get(position);
    }

    //Devulve el ID del elemento (Generalmente no se usa)
    public long getItemId(int position) {
        return position;
    }

    //Devuelve la vista de un elemento
    public View getView(int position, View convertView, ViewGroup parent) {

        //Si el contentView ya tiene un device, lo reutilizaremos con los nuevos datos
        // Si no crearemos uno nuevo
        SillaView view;
        if (convertView == null)
            view = new SillaView(parent.getContext());
        else
            view = (SillaView) convertView;

        //Asignamos los valores del Device a mostrar
        view.setSilla(sillas.get(position));

        return view;
    }
}
