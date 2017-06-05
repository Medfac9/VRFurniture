package rafalex.pdm.ugr.vrfurniture;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 123;
    private final int MY_PERMISSIONS_REQUEST_INTERNET = 234;
    private final int MY_PERMISSIONS_REQUEST_NETWORK_STATE = 345;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 456;

    //Datos para dialogos
    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    //Muebles
    private ArrayList<Categoria> categorias = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos los arrays de muebles

        ArrayList<Mueble> sillas = new ArrayList<Mueble> (Arrays.asList(new Mueble(R.drawable.chair2, "chair2.obj", "Textile3.jpg"), new Mueble(R.drawable.chair4, "chair4.obj", "tipical.jpg")));
        ArrayList<Mueble> mesas = new ArrayList<Mueble> (Arrays.asList(new Mueble(R.drawable.black_table, "black_table.obj", "wood_bump.jpg"), new Mueble(R.drawable.mesa_marmol, "table_granmother_marble.obj", "marble.jpg")));
        ArrayList<Mueble> armarios = new ArrayList<Mueble> (Arrays.asList(new Mueble(R.drawable.armario, "armario_arancio.obj", "lightWood.jpg"), new Mueble(R.drawable.armoire_lotus, "armoireLotus.obj", "armoireLotus_hetrenoir.png")));
        ArrayList<Mueble> mesitas = new ArrayList<Mueble> (Arrays.asList(new Mueble(R.drawable.comodino_azurro, "comodinoArancio.obj", "lightWoodAzul.jpg"), new Mueble(R.drawable.comodino_arancio, "comodinoArancio.obj", "lightWood.jpg")));

        //Inicializamos el array de Categorias
        categorias.add(new Categoria("Sillas", sillas));
        categorias.add(new Categoria("Mesas", mesas));
        categorias.add(new Categoria("Armario", armarios));
        categorias.add(new Categoria("Mesitas", mesitas));

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

        // Establece la vista, el adaptador y la funcion al hacer click de los elementos de la lista
        ListView pairedListView = (ListView) findViewById(R.id.categoria_selector);
        pairedListView.setAdapter(new CategoriaAdapter(categorias));
        pairedListView.setOnItemClickListener(CategoriaClickListener);
        setListViewHeightBasedOnChildren(pairedListView);

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            ListView pairedListView1 = (ListView) findViewById(R.id.categoria_selector1);
            pairedListView1.setAdapter(new CategoriaAdapter(categorias));
            pairedListView1.setOnItemClickListener(CategoriaClickListener);
            setListViewHeightBasedOnChildren(pairedListView1);
        }

        //Pedimos los permisos necesarios al usuario
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT > 22)
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT > 22)
                requestPermissions(new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT > 22)
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, MY_PERMISSIONS_REQUEST_NETWORK_STATE);
        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT > 22)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL);

    }

    // OnItemClickListener para las imágenes de la lista
    private AdapterView.OnItemClickListener CategoriaClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int position, long id) {

            // Inicia la siguiente acitividad.
            Intent i = new Intent(MainActivity.this, MuebleActivity.class);
            i.putExtra("Categoria", (Categoria) av.getItemAtPosition(position));
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
