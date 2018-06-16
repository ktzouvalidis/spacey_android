package com.ktzouvalidis.www.spacey.engine.core;

import android.util.Log;

import com.ktzouvalidis.www.spacey.engine.component.Camera;
import com.ktzouvalidis.www.spacey.util.CoreUtil;

/**
 * Created by Kwstas T on 23-Feb-18.
 */

public class Transform {
    private static Camera camera;
    private static float zNear;
    private static float zFar;
    private static float width;
    private static float height;
    private static float fov;

    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    public Transform() {
        translation = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);
    }

    public static void setProjection(float fov, float width, float height, float zNear, float zFar) {
        Transform.fov = fov;
        Transform.width = width;
        Transform.height = height;
        Transform.zNear = zNear;
        Transform.zFar = zFar;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public static void setCamera(Camera camera) {
        Transform.camera = camera;
    }

    public static Camera getCamera() {
        return camera;
    }

    public Matrix4f getProjectedTransformation() {
        Matrix4f transformationMatrix = getTransformation();
        Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width, height, zNear, zFar);
        Matrix4f cameraRotationMatrix = new Matrix4f().initCamera(camera.getForward(), camera.getUp());
        // Negatives because we want the whole world move to the opposite way when the camera moves
        Matrix4f cameraTranslationMatrix = new Matrix4f().initTranslation(-camera.getPos().getX(), -camera.getPos().getY(), -camera.getPos().getZ());

        Matrix4f pm = projectionMatrix.mul(cameraRotationMatrix.mul(cameraTranslationMatrix.mul(transformationMatrix)));
        //CoreUtil.i("Projection Matrix", CoreUtil.matrixInfo(pm.getM()));

        return pm;
        //return projectionMatrix.mul(cameraRotationMatrix.mul(cameraTranslationMatrix.mul(transformationMatrix)));
    }

    public Matrix4f getProjectedCameraTransformation() {
        Matrix4f transformationMatrix = getTransformation();
        Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width, height, zNear, zFar);
        Matrix4f cameraRotationMatrix = new Matrix4f().initCamera(camera.getForward(), camera.getUp());
        // Negatives because we want the whole world move to the opposite way when the camera moves
        Matrix4f cameraTranslationMatrix = new Matrix4f().initTranslation(-camera.getPos().getX(), -camera.getPos().getY(), -camera.getPos().getZ());

        Matrix4f pm = projectionMatrix.mul(cameraRotationMatrix.mul(cameraTranslationMatrix.mul(transformationMatrix)));
        //CoreUtil.i("Projection Matrix", CoreUtil.matrixInfo(pm.getM()));

        return pm;
        //return projectionMatrix.mul(cameraRotationMatrix.mul(cameraTranslationMatrix.mul(transformationMatrix)));
    }

    public Matrix4f getTransformation() {
        Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.getX(), translation.getY(), translation.getZ());
        Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

        Matrix4f ma = translationMatrix.mul(rotationMatrix.mul(scaleMatrix));

        return ma;
    }
}
