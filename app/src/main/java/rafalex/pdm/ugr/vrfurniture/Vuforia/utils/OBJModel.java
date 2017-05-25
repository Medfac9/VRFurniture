package rafalex.pdm.ugr.vrfurniture.Vuforia.utils;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Build;
import android.util.Log;

import java.nio.Buffer;

public class OBJModel extends MeshObject {

    private static final String LOGTAG = "OBJModel";

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    private int indicesNumber = 0;
    private int verticesNumber = 0;


    private int objMtlProgramID;
    private int objMtlVertexHandle;
    private int objMtlNormalHandle;
    private int objMtlMvpMatrixHandle;
    private int objMtlMvMatrixHandle;
    private int objMtlNormalMatrixHandle;
    private int objMtlLightPosHandle;
    private int objMtlLightColorHandle;

    private int objMtlExtra;
    private int objMtlGroupAmbientColorsHandle;
    private int objMtlGroupDiffuseColorsHandle;
    private int objMtlGroupSpecularColorsHandle;
    private int objMtlGroupTransparencyHandle;


    public OBJModel (double [] v, double [] vt, double [] vn, short [] indices) {
        setVerts(v);
        setTexCoords(vt);
        setNorms(vn);
        setIndices(indices);
    }


    private void setVerts(double [] v) {
        mVertBuff = fillBuffer(v);
        verticesNumber = v.length / 3;
    }


    private void setTexCoords(double [] vt) {
        mTexCoordBuff = fillBuffer(vt);

    }


    private void setNorms(double [] vn) {
        double[] TEAPOT_NORMS = { };
        mNormBuff = fillBuffer(TEAPOT_NORMS);
    }


    private void setIndices(short [] indices) {
        mIndBuff = fillBuffer(indices);
        indicesNumber = indices.length;
    }


    public int getNumObjectIndex()
    {
        return indicesNumber;
    }


    @Override
    public int getNumObjectVertex()
    {
        return verticesNumber;
    }


    @Override
    public Buffer getBuffer(MeshObject.BUFFER_TYPE bufferType) {
        Buffer result = null;
        switch (bufferType) {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
            default:
                break;

        }

        return result;
    }

}
