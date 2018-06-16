package com.ktzouvalidis.www.spacey.util;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_CW;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class RenderUtil {

    public static void clearScreen() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    public static void initGraphics() {
        GLES20.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);

        /*GLES20.glFrontFace(GL_CW);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);*/
    }

    public static FloatBuffer allocateFloatBuffer(float[] verticesVector) {
        ByteBuffer verticesByteBuffer = ByteBuffer.allocateDirect(verticesVector.length * 4); // 4 bytes for Float
        verticesByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer verticesBuffer = verticesByteBuffer.asFloatBuffer().put(verticesVector);
        verticesBuffer.position(0);

        return verticesBuffer;
    }
}
