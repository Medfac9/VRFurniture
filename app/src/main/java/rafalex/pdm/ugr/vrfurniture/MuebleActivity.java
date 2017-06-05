package rafalex.pdm.ugr.vrfurniture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;


public class MuebleActivity extends AppCompatActivity {

    Categoria categoria;

    //Datos para dialogos
    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    //Muebles
    private ArrayList<Mueble> muebles = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mueble);

        //Crea los dialogos
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.help_dialog, null);

        helpDialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setView(dialogView)
                .setTitle(R.string.menu_help)
                .setNeutralButton(R.string.ok_button,null);

        menuDialog = helpDialog.create();
        ((TextView) dialogView.findViewById(R.id.helpText)).setText(R.string.appDescription);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //Función Resume de la actividad
    @Override
    public void onResume() {

        super.onResume();

        System.out.println ("RESUME");

        categoria = (Categoria) getIntent().getExtras().getSerializable("Categoria");

        muebles = categoria.getMuebles();

        // Establece la vista, el adaptador y la funcion al hacer click de los elementos de la lista
        ListView pairedListView = (ListView) findViewById(R.id.mueble_selector);
        pairedListView.setAdapter(new MuebleAdapter(muebles));
        pairedListView.setOnItemClickListener(MuebleClickListener);
        setListViewHeightBasedOnChildren(pairedListView);


        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            ListView pairedListView1 = (ListView) findViewById(R.id.mueble_selector1);
            pairedListView1.setAdapter(new MuebleAdapter(muebles));
            pairedListView1.setOnItemClickListener(MuebleClickListener);
            setListViewHeightBasedOnChildren(pairedListView1);
        }
    }

    // OnItemClickListener para las imágenes de la lista
    private AdapterView.OnItemClickListener MuebleClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int position, long id) {

            boolean card_boar = getSharedPreferences("BotonCardboard", Context.MODE_PRIVATE).getBoolean("Estado", true);

            // Inicia la siguiente acitividad.
            Intent i = new Intent(MuebleActivity.this, ARViewer.class);
            i.putExtra("Mueble", (Mueble) av.getItemAtPosition(position));
            i.putExtra("Cardboard", card_boar);
            startActivity(i);
        }
    };

    //Función onCreateOptionMenu, para añadir el estilo de nuestro action_bar
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        MenuItem ar_visible = menu.findItem(R.id.ar_visible);

        SharedPreferences estado_boton_cardboard = getSharedPreferences("BotonCardboard", Context.MODE_PRIVATE);
        ar_visible.setChecked(estado_boton_cardboard.getBoolean("Estado", true));
        if (ar_visible.isChecked())
            ar_visible.setIcon(R.drawable.ic_card_board_on);
        else
            ar_visible.setIcon(R.drawable.ic_card_board_off);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu); // set your file name
        return super.onCreateOptionsMenu(menu);
    }

    //Función onOptionItemSelected, para definir el funcionamiento de las opciones del action_bar
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.help) {

            menuDialog.show();
            return true;
        }

        if (item.getItemId() == R.id.ar_visible) {

            SharedPreferences.Editor estado_boton_cardboard = getSharedPreferences("BotonCardboard", Context.MODE_PRIVATE).edit();

            if (item.isChecked()) {

                estado_boton_cardboard.putBoolean("Estado", false);
                item.setChecked(false);
                item.setIcon(R.drawable.ic_card_board_off);
            }
            else {

                estado_boton_cardboard.putBoolean("Estado", true);
                item.setChecked(true);
                item.setIcon(R.drawable.ic_card_board_on);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}