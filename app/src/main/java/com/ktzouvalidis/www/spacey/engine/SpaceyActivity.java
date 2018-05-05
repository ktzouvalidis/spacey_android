package com.ktzouvalidis.www.spacey.engine;

import android.app.Activity;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Display;

import com.ktzouvalidis.www.spacey.engine.rendering.SpaceyView;

public class SpaceyActivity extends Activity {
    private GLSurfaceView spaceyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);

        spaceyView = new SpaceyView(this, resolution.x, resolution.y);
        setContentView(spaceyView);
    }
}
