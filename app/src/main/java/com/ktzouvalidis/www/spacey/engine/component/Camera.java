package com.ktzouvalidis.www.spacey.engine.component;

import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;

import com.ktzouvalidis.www.spacey.engine.core.Vector3f;

/**
 * Created by Kwstas T on 27-Feb-18.
 */

public class Camera {
    private final Vector3f Y_AXIS = new Vector3f(0, 1, 0); // Absolute Up Direction (Up in the world)

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;

    public Camera() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up) {
        this.pos = pos;
        this.forward = forward;
        this.up = up;

        up.normalize();
        forward.normalize();
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

    public Vector3f getUp() {
        return up;
    }

    public Vector3f getLeft() {
        Vector3f left = forward.cross(up); // Cross product hand!
        left.normalize();
        return left;
    }

    public Vector3f getRight() {

        Vector3f right = up.cross(forward); // Negative value for right
        right.normalize();
        return right;
    }

    public void move(Vector3f dir, float amount) {
        pos = pos.add(dir.mul(amount));
    }

    public void rotateX(float angle) {
        Vector3f horizontalAxis = Y_AXIS.cross(forward); // Horizontal to the world (Relative to global axis)
        horizontalAxis.normalize();

        forward.rotate(angle, horizontalAxis);
        forward.normalize();

        up = forward.cross(horizontalAxis);
        up.normalize();
    }

    public void rotateY(float angle) {
        Vector3f horizontalAxis = Y_AXIS.cross(forward); // Horizontal to the world (Relative to global axis)
        horizontalAxis.normalize();

        forward.rotate(angle, Y_AXIS);
        forward.normalize();

        up = forward.cross(horizontalAxis);
        up.normalize();
    }

    public void input(MotionEvent motionEvent) {
        float movAmount = 10;
        float rotAmount = 70;

        if(motionEvent.getAction() == motionEvent.ACTION_DOWN) {
            rotateX(rotAmount);
            Log.i("Touched! ", "At: X: " + motionEvent.getX() + " Y: " + motionEvent.getY() + "\n"
                    + "New position: " + pos + "\nNew rotation: " + up);
        }
    }
}
