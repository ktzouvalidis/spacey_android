package com.ktzouvalidis.www.spacey.engine.rendering;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

import com.ktzouvalidis.www.spacey.R;
import com.ktzouvalidis.www.spacey.engine.component.Camera;
import com.ktzouvalidis.www.spacey.engine.core.Transform;
import com.ktzouvalidis.www.spacey.engine.rendering.resource.ResourceLoader;
import com.ktzouvalidis.www.spacey.engine.core.Vector3f;
import com.ktzouvalidis.www.spacey.util.RenderUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class SpaceyRenderer implements GLSurfaceView.Renderer{
    private Shader shader;
    private Transform transform;

    public float screenWidth;
    public float screenHeight;

    Mesh model;
    Mesh triangle;
    float color[] = {0.8f, 0.5f, 0.1f, 1.0f};

    Vertex[] triangleData = new Vertex[] {
            new Vertex(new Vector3f(-1, -1, 0)),
            new Vertex(new Vector3f(0, 1, 0)),
            new Vertex(new Vector3f(1, -1, 0))
    };

    //////////////////////////////////////////////////////////
    ////////////////////// MAIN METHODS //////////////////////
    //////////////////////////////////////////////////////////

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        shader = new Shader();
        triangle = new Mesh();

        shader.addVertexShader(ResourceLoader.loadShader(R.raw.vertex_shader));
        shader.addFragmentShader(ResourceLoader.loadShader(R.raw.fragment_shader));
        shader.compileShader();
        shader.addUniform("u_Color");

        RenderUtil.initGraphics();
        createObjects();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {}

    @Override
    public void onDrawFrame(GL10 gl) {
        RenderUtil.clearScreen();

        drawObjects();
    }

    //////////////////////////////////////////////////////////
    ///////////////////// CUSTOM METHODS /////////////////////
    //////////////////////////////////////////////////////////

    private void createObjects() {
        //model = ResourceLoader.loadMesh("torus.obj");
        triangle.addVertices(triangleData);
    }

    private void drawObjects() {
        shader.bind();
        // Can change the uniforms ONLY when the program is bound
        shader.setUniform("u_Color", color);
        triangle.draw();
        //triangle.draw();
    }
}
