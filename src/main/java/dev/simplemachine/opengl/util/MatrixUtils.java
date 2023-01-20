package dev.simplemachine.opengl.util;

import dev.simplemachine.physics.Ray;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixUtils {

    public static Ray rayFromClipCoordinates(float x, float y, int width, int height, Matrix4f proj, Matrix4f view) {
        float xClip = (x / width) * 2 - 1;
        float yClip = -((y / height) * 2 - 1);

        var near = new Vector3f(xClip, yClip, -1);
        var far = new Vector3f(xClip, yClip, 1);

        var invProj = new Matrix3f(proj).invert();
        var invView = new Matrix3f(view).invert();

        invProj.transform(near);
        invView.transform(near);
        invProj.transform(far);
        invView.transform(far);

        return new Ray(near, new Vector3f(far).sub(near).normalize());
    }
}
