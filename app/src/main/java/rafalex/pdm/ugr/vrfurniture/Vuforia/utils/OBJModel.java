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


    private boolean initShaders() {
        Log.d(LOGTAG, "initShaders");

        // objmtl program
        objMtlProgramID = SampleUtils.createProgramFromShaderSrc(
                LightingShaders.LIGHTING_VERTEX_SHADER,
                LightingShaders.LIGHTING_FRAGMENT_SHADER);

        SampleUtils.checkGLError("v3d GLInitRendering #0");

        objMtlVertexHandle = GLES20.glGetAttribLocation(objMtlProgramID, "a_position");
        objMtlNormalHandle = GLES20.glGetAttribLocation(objMtlProgramID, "a_normal");
        objMtlExtra = GLES20.glGetAttribLocation(objMtlProgramID, "a_vertexExtra");

        Log.d(LOGTAG, ">GL> objMtlVertexHandle= " + objMtlVertexHandle);
        Log.d(LOGTAG, ">GL> objMtlExtra= " + objMtlExtra);

        objMtlMvpMatrixHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_mvpMatrix");
        objMtlMvMatrixHandle = GLES20.glGetUniformLocation(objMtlProgramID, "u_mvMatrix");
        objMtlNormalMatrixHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_normalMatrix");

        objMtlLightPosHandle = GLES20.glGetUniformLocation(objMtlProgramID, "u_lightPos");
        objMtlLightColorHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_lightColor");

        objMtlGroupAmbientColorsHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_groupAmbientColors");
        objMtlGroupDiffuseColorsHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_groupDiffuseColors");
        objMtlGroupSpecularColorsHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_groupSpecularColors");
        objMtlGroupTransparencyHandle = GLES20.glGetUniformLocation(objMtlProgramID,
                "u_groupTransparency");

        SampleUtils.checkGLError("v3d GLInitRendering #1");

        int total[] = new int[1];
        GLES20.glGetProgramiv(objMtlProgramID, GLES20.GL_ACTIVE_UNIFORMS, total, 0);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
        {
            Log.d(LOGTAG, "@@ nb uniforms: " + total[0]);
            for (int i = 0; i < total[0]; ++i) {
                int[] uniformType = new int[1];
                int[] uniformSize = new int[1];
                String name = GLES20.glGetActiveUniform(objMtlProgramID, i, uniformSize, 0, uniformType, 0);
                int location = GLES20.glGetUniformLocation(objMtlProgramID, name);
                Log.d(LOGTAG, "@@ uniform(" + name + "), location= " + location);
            }
        }

        Log.d(LOGTAG, "end of initShaders");

        return true;
    }

    public boolean render(float[] modelViewMatrix, float[] modelViewProjMatrix) {
        GLES20.glUseProgram(objMtlProgramID);

        GLES20.glVertexAttribPointer(objMtlVertexHandle, 3, GLES20.GL_FLOAT,
                false, 0, getVertices());
        GLES20.glEnableVertexAttribArray(objMtlVertexHandle);

        GLES20.glVertexAttribPointer(objMtlNormalHandle, 3, GLES20.GL_FLOAT,
                false, 0, getNormals());
        GLES20.glEnableVertexAttribArray(objMtlNormalHandle);

        GLES20.glVertexAttribPointer(objMtlExtra, 2,
                GLES20.GL_FLOAT, false, 0, getTexCoords());
        GLES20.glEnableVertexAttribArray(objMtlExtra);

        /*if (mTextures.size() > 0) {
            // activate texture 0, bind it, and pass to shader
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures.get(0).mTextureID[0]);
            GLES20.glUniform1i(texSampler2DHandle, 0);
        }*/


        if(objMtlMvpMatrixHandle >= 0) {
            GLES20.glUniformMatrix4fv(objMtlMvpMatrixHandle, 1, false,
                    modelViewProjMatrix, 0);
        }

        GLES20.glUniformMatrix4fv(objMtlMvMatrixHandle, 1, false,
                modelViewMatrix, 0);

        // compute normal matrix
        float[] inverseMatrix = new float[16];
        Matrix.invertM(inverseMatrix, 0, modelViewMatrix, 0);

        float[] normalMatrix = new float[16];
        Matrix.transposeM(normalMatrix, 0, inverseMatrix, 0);

        GLES20.glUniformMatrix4fv(objMtlNormalMatrixHandle, 1, false,
                normalMatrix, 0);

        GLES20.glUniform4f(objMtlLightPosHandle, 0.2f, -1.0f, 0.5f, -1.0f);
        GLES20.glUniform4f(objMtlLightColorHandle, 0.5f, 0.5f, 0.5f, 1.0f);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, getNumObjectIndex(),
                GLES20.GL_UNSIGNED_SHORT, getIndices());

        GLES20.glDisableVertexAttribArray(objMtlVertexHandle);
        GLES20.glDisableVertexAttribArray(objMtlNormalHandle);
        GLES20.glDisableVertexAttribArray(objMtlExtra);

        SampleUtils.checkGLError("v3d renderFrame");

        return true;
    }

}
