package rafalex.pdm.ugr.vrfurniture;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

        ArrayList<Integer> sillas = new ArrayList<Integer> (Arrays.asList(R.drawable.silla, R.drawable.silla2, R.drawable.sillaoficina));
        ArrayList<Integer> mesas = new ArrayList<Integer> (Arrays.asList(R.drawable.mesa, R.drawable.mesa1));
        ArrayList<Integer> armarios = new ArrayList<Integer> (Arrays.asList(R.drawable.armario));
        ArrayList<Integer> mesitas = new ArrayList<Integer> (Arrays.asList(R.drawable.mesita));

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

    //Función Resume de la actividad
    @Override
    public void onResume() {

        super.onResume();

        // Establece la vista, el adaptador y la funcion al hacer click de los elementos de la lista
        ListView pairedListView = (ListView) findViewById(R.id.categoria_selector);
        pairedListView.setAdapter(new CategoriaAdapter(categorias));
        pairedListView.setOnItemClickListener(CategoriaClickListener);

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

        return super.onOptionsItemSelected(item);
    }
}
