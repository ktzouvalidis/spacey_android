package com.ktzouvalidis.www.spacey.engine.rendering;

import android.opengl.GLSurfaceView;

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
    private Camera camera;

    public float screenWidth;
    public float screenHeight;

    Mesh model;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        shader = new Shader();
        camera = new Camera(new Vector3f(0, 0, -1.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(0, 1, 0));

        transform = new Transform();
        Transform.setProjection(70, screenWidth, screenHeight, 0.1f, 1000);
        Transform.setCamera(camera);

        shader.addVertexShader(ResourceLoader.loadShader(R.raw.vertex_shader));
        shader.addFragmentShader(ResourceLoader.loadShader(R.raw.fragment_shader));
        shader.compileShader();
        shader.addUniform("u_Color");
        shader.setUniform("u_Color", new Vector3f(1.0f, 1.0f, 0.0f));
        shader.addUniform("u_Transform");

        transform.setScale(0.5f, 0.5f, 0.5f);
        transform.setTranslation(0.0f, 0.0f, 1.0f);
        transform.setRotation(90, 0, 0f);

        RenderUtil.initGraphics();
        createObjects();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        RenderUtil.clearScreen();

        shader.setUniform("u_Transform", transform.getProjectedTransformation());

        drawObjects();
    }

    private void createObjects() {
        //triangle = new Mesh();
        model = ResourceLoader.loadMesh("torus.obj");
        //triangle.addVertices(triangleData, triangleIndices);
    }

    private void drawObjects() {
        shader.bind();
        model.draw();
        //triangle.draw();
    }
}
