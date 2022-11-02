package dev.simplemachine.ecs.components;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CTransform {
    private final Matrix4f transform = new Matrix4f();

    private final Vector3f translation = new Vector3f(0, 0, 0);
    private final Quaternionf rotation = new Quaternionf().identity();
    private final Vector3f scale = new Vector3f(1, 1, 1);

    public CTransform() {
        transform.translationRotateScale(translation, rotation, scale);
    }

    public Matrix4f getTransform() {
        return transform;
    }

    public void setTranslation(Vector3f translation) {
        this.translation.set(translation);
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation.set(rotation);
    }

    public void setScale(Vector3f scale) {
        this.scale.set(scale);
    }
}
