package rafalex.pdm.ugr.vrfurniture;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Datos para dialogos
    private AlertDialog menuDialog;
    private AlertDialog.Builder helpDialog;

    //Muebles
    private ArrayList<Mueble> tiposMuebles = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos el array de muebles
        tiposMuebles.add(new Mueble(R.drawable.silla));
        tiposMuebles.add(new Mueble(R.drawable.mesa));
        tiposMuebles.add(new Mueble(R.drawable.armario));
        tiposMuebles.add(new Mueble(R.drawable.mesita));

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
        ListView pairedListView = (ListView) findViewById(R.id.level_selector);
        pairedListView.setAdapter(new MuebleAdapter(tiposMuebles));
        pairedListView.setOnItemClickListener(MuebleClickListener);

    }

    // OnItemClickListener para las imágenes de la lista
    private AdapterView.OnItemClickListener MuebleClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView av, View v, int position, long id) {

            // Inicia la siguiente acitividad.
            Intent i = new Intent(MainActivity.this, Paint.class);
            i.putExtra("Mueble", (Mueble) av.getItemAtPosition(position));
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
