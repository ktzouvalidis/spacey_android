package com.ktzouvalidis.www.spacey.engine.core;

import android.util.Log;

import com.ktzouvalidis.www.spacey.util.CoreUtil;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class Matrix4f {
    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    public float[][] getM() {
        return m;
    }

    public void set(int i, int j, float value) {
        m[i][j] = value;
    }

    public float get(int i, int j) {
        return m[i][j];
    }

    public Matrix4f initIdentity() {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = 0;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
      //x               y               z               w          <--  How much I want
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = x; // x Final
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = y; // y Final
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = z; // z Final
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1; // w Final

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        //x               y               z               w          <--  How much I want
        m[0][0] = x;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0; // x Final
        m[1][0] = 0;    m[1][1] = y;    m[1][2] = 0;    m[1][3] = 0; // y Final
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = z;    m[2][3] = 0; // z Final
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1; // w Final

        return this;
    }

    public Matrix4f initRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        x = (float)Math.toRadians(x);
        y = (float)Math.toRadians(y);
        z = (float)Math.toRadians(z);

        //x                                 y                                   z                                   w             <--  How much I want
        rz.m[0][0] = (float)Math.cos(z);    rz.m[0][1] = -(float)Math.sin(z);   rz.m[0][2] = 0;                     rz.m[0][3] = 0; // x Final
        rz.m[1][0] = (float)Math.sin(z);    rz.m[1][1] = (float)Math.cos(z);    rz.m[1][2] = 0;                     rz.m[1][3] = 0; // y Final
        rz.m[2][0] = 0;                     rz.m[2][1] = 0;                     rz.m[2][2] = 1;                     rz.m[2][3] = 0; // z Final
        rz.m[3][0] = 0;                     rz.m[3][1] = 0;                     rz.m[3][2] = 0;                     rz.m[3][3] = 1; // w Final

        rx.m[0][0] = 1;                     rx.m[0][1] = 0;                     rx.m[0][2] = 0;                     rx.m[0][3] = 0; // x Final
        rx.m[1][0] = 0;                     rx.m[1][1] = (float)Math.cos(x);    rx.m[1][2] = -(float)Math.sin(x);   rx.m[1][3] = 0; // y Final
        rx.m[2][0] = 0;                     rx.m[2][1] = (float)Math.sin(x);    rx.m[2][2] = (float)Math.cos(x);    rx.m[2][3] = 0; // z Final
        rx.m[3][0] = 0;                     rx.m[3][1] = 0;                     rx.m[3][2] = 0;                     rx.m[3][3] = 1; // w Final

        ry.m[0][0] = (float)Math.cos(y);    ry.m[0][1] = 0;                     ry.m[0][2] = -(float)Math.sin(y);   ry.m[0][3] = 0; // x Final
        ry.m[1][0] = 0;                     ry.m[1][1] = 1;                     ry.m[1][2] = 0;                     ry.m[1][3] = 0; // y Final
        ry.m[2][0] = (float)Math.sin(y);    ry.m[2][1] = 0;                     ry.m[2][2] = (float)Math.cos(y);    ry.m[2][3] = 0; // z Final
        ry.m[3][0] = 0;                     ry.m[3][1] = 0;                     ry.m[3][2] = 0;                     ry.m[3][3] = 1; // w Final

        m = rz.mul(ry.mul(rx)).getM();

        return this;
    }

    public Matrix4f initProjection(float fov, float width, float height, float zNear, float zFar) {
        float ar = width / height; // Aspect Ratio
        float tanHalfFOV = (float)Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar; // How much space we have in the depth
        // *OpenGL automaticaly devides by the z value later on

        m[0][0] = 1.0f / (tanHalfFOV * ar);     m[0][1] = 0;                    m[0][2] = 0;                        m[0][3] = 0;
        m[1][0] = 0;                            m[1][1] = 1.0f / tanHalfFOV;    m[1][2] = 0;                        m[1][3] = 0;
        m[2][0] = 0;                            m[2][1] = 0;                    m[2][2] = (-zNear - zFar) / zRange; m[2][3] = 2 * zFar * zNear / zRange;
        m[3][0] = 0;                            m[3][1] = 0;                    m[3][2] = 1;                        m[3][3] = 0;

        return this;
    }

    public Matrix4f initCamera(Vector3f forward, Vector3f up) {
        Vector3f f = forward;
        f.normalize(); // Just in case forward isn't normalized

        Vector3f r = up;
        r.normalize();
        r = r.cross(f); // Right vector

        Vector3f u = f.cross(r); // Up vector

        m[0][0] = r.getX(); m[0][1] = r.getY();  m[0][2] = r.getZ();    m[0][3] = 0;
        m[1][0] = u.getX(); m[1][1] = u.getY();  m[1][2] = u.getZ();    m[1][3] = 0;
        m[2][0] = f.getX(); m[2][1] = f.getY();  m[2][2] = f.getZ();    m[2][3] = 0;
        m[3][0] = 0;        m[3][1] = 0;         m[3][2] = 0;           m[3][3] = 1;

        String info = CoreUtil.matrixInfo(m);
        //CoreUtil.i("Camera Init Matrix:", info);

        return this;
    }

    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, m[i][0] * r.get(0, j) +
                            m[i][1] * r.get(1, j) +
                            m[i][2] * r.get(2, j) +
                            m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public Matrix4f muli(Matrix4f r) {
        Matrix4f res = new Matrix4f();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, m[0][i] * r.get(j, 0) +
                        m[1][i] * r.get(j, 1) +
                        m[2][i] * r.get(j, 2) +
                        m[3][i] * r.get(j, 3));
            }
        }

        return res;
    }

    @Override
    public String toString() {
        String str = "M: ";

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                str = str + m[i][j] + ", ";
            }
        }

        return str;
    }
}
