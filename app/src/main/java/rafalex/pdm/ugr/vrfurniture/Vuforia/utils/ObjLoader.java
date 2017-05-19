package rafalex.pdm.ugr.vrfurniture.Vuforia.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ObjLoader {

    public static ObjectLoaded LoadOBJ (Context context, String fname)  {

        try {
            InputStream in = context.getResources().getAssets().open(fname);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String temps = null;

            ArrayList<Double> v = new ArrayList();
            ArrayList<Double> vt = new ArrayList();
            ArrayList<Double> vn = new ArrayList();
            ArrayList<Short> indice = new ArrayList();

            //Read .obj files
            while ((temps = br.readLine()) != null) {

                String[] tempsa = temps.split("[ ]+");
                if (tempsa[0].trim().equals("v")) {

                    v.add(Double.parseDouble(tempsa[1]));
                    v.add(Double.parseDouble(tempsa[2]));
                    v.add(Double.parseDouble(tempsa[3]));
                }

                if (tempsa[0].trim().equals("vt")) {

                    vt.add(Double.parseDouble(tempsa[1]));
                    vt.add(Double.parseDouble(tempsa[2]));
                    if (tempsa.length > 3)
                        vt.add(Double.parseDouble(tempsa[3]));
                    else
                        vt.add(0.0);
                }

                if (tempsa[0].trim().equals("vn")) {

                    vn.add(Double.parseDouble(tempsa[1]));
                    vn.add(Double.parseDouble(tempsa[2]));
                    vn.add(Double.parseDouble(tempsa[3]));
                }

                if (tempsa[0].trim().equals("f")) {

                    String[] tempf = tempsa[1].split("[/]+");
                    indice.add((short) (Short.parseShort(tempf[0]) - 1));
                    tempf = tempsa[2].split("[/]+");
                    indice.add((short) (Short.parseShort(tempf[0]) - 1));
                    tempf = tempsa[3].split("[/]+");
                    indice.add((short) (Short.parseShort(tempf[0]) - 1));
                }
            }

            //Generate the object
            double [] vertices = new double [v.size()];
            for (int i = 0; i < vertices.length; i++)
                vertices[i] = v.get(i);

            double [] verticesTexturas = new double [vt.size()];
            for (int i = 0; i < verticesTexturas.length; i++)
                verticesTexturas[i] = vt.get(i);

            double [] verticesNormales = new double [vn.size()];
            for (int i = 0; i < verticesNormales.length; i++)
                verticesNormales[i] = vn.get(i);

            short [] indices = new short [indice.size()];
            for (int i = 0; i < indices.length; i++)
                indices[i] = indice.get(i);

            return new ObjectLoaded(vertices, verticesTexturas, verticesNormales, indices);

        } catch (IOException e) {
            Log.e("LOADOBJ", "Fail to open " + fname);
        }

        return null;
    }
}
