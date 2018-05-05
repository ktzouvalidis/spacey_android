package com.ktzouvalidis.www.spacey.engine.rendering;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class SpaceyView extends GLSurfaceView {
    public SpaceyView(Context context, int screenWidth, int screenHeight) {
        super(context);
        SpaceyRenderer sr = new SpaceyRenderer();
        sr.screenWidth = screenWidth;
        sr.screenHeight = screenHeight;

        setEGLContextClientVersion(2);
        setRenderer(sr);
    }
}
