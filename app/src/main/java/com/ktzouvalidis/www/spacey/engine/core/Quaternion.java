package com.ktzouvalidis.www.spacey.engine.core;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class Quaternion {
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getZ() {
        return z;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getW() {
        return w;
    }

    public float length() {
        return (float)Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion normalize() {
        float length = length();

        x /= length;
        y /= length;
        z /= length;
        w /= length;

        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mul(Quaternion r) {
        float _w = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float _x = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float _y = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float _z = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(_x, _y, _z, _w);
    }

    public Quaternion mul(Vector3f r) {
        float _w = -x * r.getX() - y * r.getY() - z * r.getZ();
        float _x =  w * r.getX() + y * r.getZ() - z * r.getY();
        float _y =  w * r.getY() + z * r.getX() - x * r.getZ();
        float _z =  w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(_x, _y, _z, _w);
    }
}

