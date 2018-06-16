package com.ktzouvalidis.www.spacey.engine.rendering;

import android.opengl.GLES20;

import com.ktzouvalidis.www.spacey.engine.core.Vector3f;
import com.ktzouvalidis.www.spacey.util.CoreUtil;
import com.ktzouvalidis.www.spacey.util.RenderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class Mesh {
    private int vbo;
    private int ibo;
    private int size; // Size of the data
    private boolean usingFaces;

    public Mesh() {
        int[] buffers = new int[2];
        GLES20.glGenBuffers(2, buffers, 0);

        vbo = buffers[0];
        ibo = buffers[1];
        size = 0;
    }

    public Mesh(Vertex[] data) {}

    public void addVertices(FloatBuffer verticesBuffer, IntBuffer indicesBuffer) {
        size = indicesBuffer.capacity();
        usingFaces = true;

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, verticesBuffer.capacity() * 4, verticesBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer.capacity() * 4, indicesBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        
    }

    public void addVertices(Vertex[] vertices, int[] indices) {
        size = indices.length;
        float[] verticesVector = CoreUtil.toVector(vertices);
        if(verticesVector == null) {
            System.err.println("Conversion Error: Couldn't convert to float vector of vertices.");
            System.exit(1);
        }
        usingFaces = true;

        ByteBuffer verticesByteBuffer = ByteBuffer.allocateDirect(verticesVector.length * 4); // 4 bytes for Float
        verticesByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer verticesBuffer = verticesByteBuffer.asFloatBuffer().put(verticesVector);
        verticesBuffer.position(0);

        ByteBuffer indicesByteBuffer = ByteBuffer.allocateDirect(indices.length * 4); // 4 bytes for Int
        indicesByteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer indicesBuffer = indicesByteBuffer.asIntBuffer().put(indices);
        indicesBuffer.position(0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, verticesBuffer.capacity() * 4, verticesBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer.capacity() * 4, indicesBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void addVertices(Vertex[] vertices) {
        size = vertices.length;
        float[] verticesVector = CoreUtil.toVector(vertices);
        if(verticesVector == null) {
            System.err.println("Conversion Error: Couldn't convert to float vector of vertices.");
            System.exit(1);
        }
        usingFaces = false;

        FloatBuffer verticesBuffer = RenderUtil.allocateFloatBuffer(verticesVector);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, verticesBuffer.capacity() * 4, verticesBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void addVertices(float[] vertices) {
        size = vertices.length / 3;

        ByteBuffer verticesByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4); // 4 bytes for Float
        verticesByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer verticesBuffer = verticesByteBuffer.asFloatBuffer().put(vertices);
        verticesBuffer.position(0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, verticesBuffer.capacity() * 4, verticesBuffer, GLES20.GL_STATIC_DRAW);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void draw() {
        if(usingFaces) {
            GLES20.glEnableVertexAttribArray(0);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);
            GLES20.glVertexAttribPointer(0, Vertex.SIZE, GLES20.GL_FLOAT, false, Vertex.VERTEX_BYTE_SIZE, 0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, ibo);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, size, GLES20.GL_UNSIGNED_INT, 0);
            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

            GLES20.glDisableVertexAttribArray(0);
        } else {
            GLES20.glEnableVertexAttribArray(0);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo);

            GLES20.glVertexAttribPointer(0, Vertex.SIZE, GLES20.GL_FLOAT, false, Vertex.VERTEX_BYTE_SIZE, 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, size);

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
            GLES20.glDisableVertexAttribArray(0);
        }
    }
}
