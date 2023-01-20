package dev.simplemachine.physics;

import org.joml.Vector3f;

public class AxisAlignedBoundingBox {
    private Vector3f min;
    private Vector3f max;

    public Vector3f getMin() {
        return min;
    }

    public void setMin(Vector3f min) {
        this.min = min;
    }

    public Vector3f getMax() {
        return max;
    }

    public void setMax(Vector3f max) {
        this.max = max;
    }
}
