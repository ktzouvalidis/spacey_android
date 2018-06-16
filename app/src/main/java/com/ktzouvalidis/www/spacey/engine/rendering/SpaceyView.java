package com.ktzouvalidis.www.spacey.engine.rendering;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class SpaceyView extends GLSurfaceView {

    private SpaceyRenderer renderer;

    public SpaceyView(Context context, int screenWidth, int screenHeight) {
        super(context);
        renderer = new SpaceyRenderer();
        renderer.screenWidth = screenWidth;
        renderer.screenHeight = screenHeight;

        setEGLContextClientVersion(2);
        setRenderer(renderer);
    }
}
