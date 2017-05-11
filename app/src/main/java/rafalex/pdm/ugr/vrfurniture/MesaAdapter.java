package rafalex.pdm.ugr.vrfurniture;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MesaAdapter  extends BaseAdapter {

    //ArrayList con todos las Mesas a mostrar
    private ArrayList<Mesa> mesas;

    public MesaAdapter(ArrayList<Mesa> mesas) {
        this.mesas = mesas;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }

    //Devuelve el numero de elementos
    public int getCount() {
        return mesas.size();
    }

    //Devuelve el elemento de una posición
    public Object getItem(int position) {
        return mesas.get(position);
    }

    //Devulve el ID del elemento (Generalmente no se usa)
    public long getItemId(int position) {
        return position;
    }

    //Devuelve la vista de un elemento
    public View getView(int position, View convertView, ViewGroup parent) {

        //Si el contentView ya tiene un device, lo reutilizaremos con los nuevos datos
        // Si no crearemos uno nuevo
        MesaView view;
        if (convertView == null)
            view = new MesaView(parent.getContext());
        else
            view = (MesaView) convertView;

        //Asignamos los valores del Device a mostrar
        view.setMesa(mesas.get(position));

        return view;
    }
}
