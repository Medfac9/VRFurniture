package rafalex.pdm.ugr.vrfurniture.Vuforia.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Struct;
import java.util.ArrayList;

public class ObjLoader {


    //Carga un fichero obj sin tener en cuenta las normales
    public static OBJModel LoadOBJ (Context context, String fname) throws IOException {

        InputStream in = context.getResources().getAssets().open(fname);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        String temps = null;

        ArrayList<Vector3> v = new ArrayList();
        ArrayList<Vector2> vt = new ArrayList();
        ArrayList<Short> vertexIndices = new ArrayList();
        ArrayList<Short> textureIndices = new ArrayList();

        ArrayList<Vector3> duplicateVerts = new ArrayList();
        ArrayList<Vector2> duplicateTextureVerts = new ArrayList();

        //Leee el archivo .obj
        while ((temps = br.readLine()) != null) {

            String[] tempsa = temps.split("[ ]+");
            if (tempsa[0].trim().equals("v"))
                v.add(new Vector3(Double.parseDouble(tempsa[1]), Double.parseDouble(tempsa[2]), Double.parseDouble(tempsa[3])));

            if (tempsa[0].trim().equals("vt"))
                vt.add(new Vector2(Double.parseDouble(tempsa[1]), Double.parseDouble(tempsa[2])));

            if (tempsa[0].trim().equals("f")) {

                String[] tempf = tempsa[1].split("[/]+");
                vertexIndices.add((short) (Short.parseShort(tempf[0]) - 1));
                if (tempf.length > 1)
                    textureIndices.add((short) (Short.parseShort(tempf[1]) - 1));

                tempf = tempsa[2].split("[/]+");
                vertexIndices.add((short) (Short.parseShort(tempf[0]) - 1));
                if (tempf.length > 1)
                    textureIndices.add((short) (Short.parseShort(tempf[1]) - 1));

                tempf = tempsa[3].split("[/]+");
                vertexIndices.add((short) (Short.parseShort(tempf[0]) - 1));
                if (tempf.length > 1)
                    textureIndices.add((short) (Short.parseShort(tempf[1]) - 1));
            }
        }


        short[] indices = new short[vertexIndices.size()];
        duplicateVerts = (ArrayList<Vector3>) v.clone();
        if (textureIndices.size() > 0) {
            for (int i = 0; i < duplicateVerts.size(); i++)
                duplicateTextureVerts.add(null);

            for (int i = 0; i < vertexIndices.size(); i++) {

                int vertex = vertexIndices.get(i);
                int textureVertex = textureIndices.get(i);

                //Si el vertice no tiene asignado ninguna coordenada, la asigna.
                if (duplicateTextureVerts.get(vertex) == null) {
                    duplicateTextureVerts.set(vertex, vt.get(textureVertex));
                    indices[i] = (short) vertex;
                }
                //Si el vertice ya tenia una coordenada asignada
                else {

                    int indice = -1;
                    //Busca si la pareja vertice, coordenada ya ha sido añadida
                    for (int n = v.size(); n < duplicateVerts.size() && indice == -1; n++)
                        if (duplicateVerts.get(n) == v.get(vertex))
                            if (duplicateTextureVerts.get(n) == vt.get(textureVertex))
                                indice = n;

                    //Si no la ha encontrado se añada un nuveo vertice y textura
                    if (indice == -1) {
                        duplicateVerts.add(v.get(vertex));
                        duplicateTextureVerts.add(vt.get(textureVertex));
                        indice = duplicateVerts.size() - 1;
                    }

                    //Se actualizan los vertices
                    indices[i] = (short) indice;
                }
            }
        }
        else
            for (int i = 0; i < indices.length; i++)
                indices[i] = vertexIndices.get(i);

        //Genera el objeto

        double [] vertices = new double [duplicateVerts.size() * 3];
        for (int i = 0; i < duplicateVerts.size(); i++) {
            vertices[i * 3] = duplicateVerts.get(i).x;
            vertices[(i * 3) + 1] = duplicateVerts.get(i).y;
            vertices[(i * 3) + 2] = duplicateVerts.get(i).z;
        }

        double [] verticesTexturas = new double [duplicateTextureVerts.size() * 2];
        for (int i = 0; i < duplicateTextureVerts.size(); i++) {
            verticesTexturas[i * 2] = duplicateTextureVerts.get(i).x;
            verticesTexturas[(i * 2) + 1] = duplicateTextureVerts.get(i).y;
        }

        double [] verticesNormales = new double[0];

        return new OBJModel(vertices, verticesTexturas, verticesNormales, indices);
    }


    //Carga el modelo obj completo con las normales
    public static OBJModel LoadFullOBJ (Context context, String fname) throws IOException {

        InputStream in = context.getResources().getAssets().open(fname);
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        String temps = null;

        ArrayList<Vector3> v = new ArrayList();
        ArrayList<Vector2> vt = new ArrayList();
        ArrayList<Vector3> vn = new ArrayList();

        ArrayList<Short> vertexIndices = new ArrayList();
        ArrayList<Short> textureIndices = new ArrayList();
        ArrayList<Short> normalIndices = new ArrayList();

        ArrayList<Vector3> duplicateVerts = new ArrayList();
        ArrayList<Vector2> duplicateTextureVerts = new ArrayList();
        ArrayList<Vector3> duplicateNormals = new ArrayList();

        //Leee el archivo .obj
        while ((temps = br.readLine()) != null) {

            String[] tempsa = temps.split("[ ]+");
            if (tempsa[0].trim().equals("v"))
                v.add(new Vector3(Double.parseDouble(tempsa[1]), Double.parseDouble(tempsa[2]), Double.parseDouble(tempsa[3])));

            if (tempsa[0].trim().equals("vt"))
                vt.add(new Vector2(Double.parseDouble(tempsa[1]), Double.parseDouble(tempsa[2])));

            if (tempsa[0].trim().equals("vn"))
                vn.add(new Vector3 (Double.parseDouble(tempsa[1]), Double.parseDouble(tempsa[2]), Double.parseDouble(tempsa[3])));

            if (tempsa[0].trim().equals("f")) {

                String[] tempf = tempsa[1].split("[/]+");
                vertexIndices.add((short) (Short.parseShort(tempf[0]) - 1));
                if (tempf.length > 1)
                    textureIndices.add((short) (Short.parseShort(tempf[1]) - 1));
                if (tempf.length > 2)
                    normalIndices.add((short) (Short.parseShort(tempf[2]) - 1));

                tempf = tempsa[2].split("[/]+");
                vertexIndices.add((short) (Short.parseShort(tempf[0]) - 1));
                if (tempf.length > 1)
                    textureIndices.add((short) (Short.parseShort(tempf[1]) - 1));
                if (tempf.length > 2)
                    normalIndices.add((short) (Short.parseShort(tempf[2]) - 1));

                tempf = tempsa[3].split("[/]+");
                vertexIndices.add((short) (Short.parseShort(tempf[0]) - 1));
                if (tempf.length > 1)
                    textureIndices.add((short) (Short.parseShort(tempf[1]) - 1));
                if (tempf.length > 2)
                    normalIndices.add((short) (Short.parseShort(tempf[2]) - 1));
            }
        }

        short[] indices = new short[vertexIndices.size()];
        duplicateVerts = (ArrayList<Vector3>) v.clone();
        if (textureIndices.size() > 0 && normalIndices.size() > 0) {
            for (int i = 0; i < duplicateVerts.size(); i++) {
                duplicateTextureVerts.add(null);
                duplicateNormals.add(null);
            }

            for (int i = 0; i < vertexIndices.size(); i++) {

                int vertex = vertexIndices.get(i);
                int textureVertex = textureIndices.get(i);
                int normalVertex = normalIndices.get(i);

                //Si el vertice no tiene asignado ninguna coordenada, la asigna.
                if (duplicateTextureVerts.get(vertex) == null) {
                    duplicateTextureVerts.set(vertex, vt.get(textureVertex));
                    duplicateNormals.set(vertex, vn.get(normalVertex));
                    indices[i] = (short) vertex;
                }
                //Si el vertice ya tenia una coordenada asignada
                else {

                    int indice = -1;
                    //Busca si la pareja vertice, coordenada ya ha sido añadida
                    for (int n = v.size(); n < duplicateVerts.size() && indice == -1; n++)
                        if (duplicateVerts.get(n) == v.get(vertex))
                            if (duplicateTextureVerts.get(n) == vt.get(textureVertex) && duplicateNormals.get(n) == vn.get(normalVertex))
                                indice = n;

                    //Si no la ha encontrado se añada un nuveo vertice y textura
                    if (indice == -1) {
                        duplicateVerts.add(v.get(vertex));
                        duplicateNormals.add(vn.get(normalVertex));
                        duplicateTextureVerts.add(vt.get(textureVertex));
                        indice = duplicateVerts.size() - 1;
                    }

                    //Se actualizan los vertices
                    indices[i] = (short) indice;
                }
            }
        }else
            for (int i = 0; i < indices.length; i++)
                indices[i] = vertexIndices.get(i);

        //Genera el objeto

        double [] vertices = new double [duplicateVerts.size() * 3];
        for (int i = 0; i < duplicateVerts.size(); i++) {
            vertices[i * 3] = duplicateVerts.get(i).x;
            vertices[(i * 3) + 1] = duplicateVerts.get(i).y;
            vertices[(i * 3) + 2] = duplicateVerts.get(i).z;
        }

        double [] verticesTexturas = new double [duplicateTextureVerts.size() * 2];
        for (int i = 0; i < duplicateTextureVerts.size(); i++) {
            verticesTexturas[i * 2] = duplicateTextureVerts.get(i).x;
            verticesTexturas[(i * 2) + 1] = duplicateTextureVerts.get(i).y;
        }

        double [] verticesNormales = new double [duplicateNormals.size() * 3];
        for (int i = 0; i < duplicateNormals.size(); i++) {
            verticesNormales[i * 3] = duplicateNormals.get(i).x;
            verticesNormales[(i * 3) + 1] = duplicateNormals.get(i).y;
            verticesNormales[(i * 3) + 2] = duplicateNormals.get(i).z;
        }

        return new OBJModel(vertices, verticesTexturas, verticesNormales, indices);
    }
}