package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CCamera implements Component {

    private Matrix4f projectionMatrix;
    private Vector3f position;
    private Vector3f lookAt;
    private boolean active = true;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector3f lookAt) {
        this.lookAt = lookAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }
}
