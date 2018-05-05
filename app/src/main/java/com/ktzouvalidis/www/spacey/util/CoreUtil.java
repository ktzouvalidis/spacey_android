package com.ktzouvalidis.www.spacey.util;

import com.ktzouvalidis.www.spacey.engine.rendering.Vertex;
import com.ktzouvalidis.www.spacey.engine.core.Matrix4f;
import com.ktzouvalidis.www.spacey.engine.core.Vector3f;

/**
 * Created by Kwstas T on 23-Feb-18.
 */

public class CoreUtil {

    public static float[] toVector(Vertex[] vertices) {
        float[] verticesVector = new float[vertices.length * Vertex.SIZE];

        int counter = 0; // Counter for the verticesVector vector
        for(int i = 0; i < vertices.length; i++) {
            Vector3f v = vertices[i].getPos();
            verticesVector[counter++] = v.getX();
            verticesVector[counter++] = v.getY();
            verticesVector[counter++] = v.getZ();
        }

        return verticesVector;
    }

    public static float[] toVectorMatrix(Matrix4f matrix) {
        float[] matrixVector = new float[16];

        int counter = 0; // Counter for the matrixVector vector
        for(int j = 0; j < 4; j++) {
            matrixVector[counter++] = matrix.get(0, j);
            matrixVector[counter++] = matrix.get(1, j);
            matrixVector[counter++] = matrix.get(2, j);
            matrixVector[counter++] = matrix.get(3, j);
        }

        return matrixVector;
    }
}
