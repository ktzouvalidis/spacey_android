package com.ktzouvalidis.www.spacey.engine.rendering;

import com.ktzouvalidis.www.spacey.engine.core.Vector3f;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class Vertex {
    public static final int SIZE = 3;
    private Vector3f pos;

    public Vertex(Vector3f pos) {
        this.pos = pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getPos() {
        return pos;
    }

}
