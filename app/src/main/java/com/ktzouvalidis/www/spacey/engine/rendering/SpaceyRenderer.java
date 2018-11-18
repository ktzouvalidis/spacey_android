package com.ktzouvalidis.www.spacey.engine.rendering;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.ktzouvalidis.www.spacey.R;
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

    //region MAIN METHODS
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        shader = new Shader();
        transform = new Transform();
        triangle = new Mesh();

        shader.addVertexShader(ResourceLoader.loadShader(R.raw.vertex_shader));
        shader.addFragmentShader(ResourceLoader.loadShader(R.raw.fragment_shader));
        shader.compileShader();
        shader.addUniform("u_Color");
        shader.addUniform("transform");

        RenderUtil.initGraphics();
        createObjects();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {}

    @Override
    public void onDrawFrame(GL10 gl) {
        RenderUtil.clearScreen();

        transform.setScale(0.5f, 0.5f, 0.5f);
        transform.setRotation(0, 0, 90);

        drawObjects();
    }
    //endregion

    //region CUSTOM METHODS
    private void createObjects() {
        //model = ResourceLoader.loadMesh("torus.obj");
        triangle.addVertices(triangleData);
    }

    private void drawObjects() {
        shader.bind();
        // Can change the uniforms ONLY when the program is bound
        shader.setUniform("u_Color", color);
        shader.setUniform("transform", transform.getTransformation());
        triangle.draw();
    }
    //endregion
}
