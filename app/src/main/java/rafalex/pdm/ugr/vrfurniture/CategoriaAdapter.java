package rafalex.pdm.ugr.vrfurniture;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;


public class CategoriaAdapter extends BaseAdapter {

    //ArrayList con todos los Muebles a mostrar
    private ArrayList<Categoria> categorias;

    public CategoriaAdapter(ArrayList<Categoria> categoria) {
        this.categorias = categoria;

        //Cada vez que cambiamos los elementos debemos noficarlo
        notifyDataSetChanged();
    }

    //Devuelve el numero de elementos
    public int getCount() {
        return categorias.size();
    }

    //Devuelve el elemento de una posición
    public Object getItem(int position) {
        return categorias.get(position);
    }

    //Devulve el ID del elemento (Generalmente no se usa)
    public long getItemId(int position) {
        return position;
    }

    //Devuelve la vista de un elemento
    public View getView(int position, View convertView, ViewGroup parent) {

        //Si el contentView ya tiene un device, lo reutilizaremos con los nuevos datos
        // Si no crearemos uno nuevo
        CategoriaView view;
        if (convertView == null)
            view = new CategoriaView(parent.getContext());
        else
            view = (CategoriaView) convertView;

        //Asignamos los valores del Device a mostrar
        view.setCategoria(categorias.get(position));

        return view;
    }
}
