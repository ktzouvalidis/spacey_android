package com.ktzouvalidis.www.spacey.engine.rendering;

import android.opengl.GLES20;
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

    public float screenWidth;
    public float screenHeight;

    Camera camera;
    Mesh model;
    Mesh model1;
    float color[] = {0.8f, 0.5f, 0.1f, 1.0f};

    //region MAIN METHODS
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        shader = new Shader();
        transform = new Transform();
        camera = new Camera();
        Transform.setProjection(70, 1920, 1080, 0.1f, 1000);
        Transform.setCamera(camera);

        shader.addVertexShader(ResourceLoader.loadShader(R.raw.vertex_shader));
        shader.addFragmentShader(ResourceLoader.loadShader(R.raw.fragment_shader));
        shader.compileShader();
        shader.addUniform("u_Color");
        shader.addUniform("u_Transform");

        RenderUtil.initGraphics();
        createObjects();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {}

    @Override
    public void onDrawFrame(GL10 gl) {
        RenderUtil.clearScreen();

        transform.setTranslation(0, 0, 5);
        transform.setRotation(0, 0, 0);

        drawObjects();
    }
    //endregion

    //region CUSTOM METHODS
    private void createObjects() {
        model = ResourceLoader.loadMesh("torus.obj");
        model1 = ResourceLoader.loadMesh("torus.obj");
    }

    private void drawObjects() {
        shader.bind();
        // Can change the uniforms ONLY when the program is bound
        shader.setUniform("u_Color", color);
        shader.setUniform("u_Transform", transform.getProjectedCameraTransformation());
        model.draw();
        model1.draw();
    }
    //endregion
}
