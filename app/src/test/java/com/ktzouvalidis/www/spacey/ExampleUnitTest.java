package com.ktzouvalidis.www.spacey;

import com.ktzouvalidis.www.spacey.engine.component.Camera;
import com.ktzouvalidis.www.spacey.engine.core.Matrix4f;
import com.ktzouvalidis.www.spacey.engine.core.Transform;
import com.ktzouvalidis.www.spacey.engine.core.Vector3f;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Transform t = new Transform();
        Camera c = new Camera();
        Transform.setProjection(70, 1920, 1080, 0.1f, 1000);

        t.setCamera(c);
        t.getProjectedTransformation();
        c.rotateX(90);
        t.getProjectedTransformation();
        assertEquals(4, 2 + 2);
    }
}