package dev.simplemachine.physics;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectUtilTest {



    @Test
    public void test() {
        var start = new Vector3f(0.0f, 0.0f, 10.0f);
        var dir = new Vector3f(0.0f, -1.0f, -1.0f).normalize();
        var ray = new Ray(start, dir);
        var plane = new Plane(new Vector3f(0.0f, 0.0f, 5.0f), new Vector3f(0, 0, 1));

        var value = IntersectUtil.intersects(ray, plane).get();
        System.out.println(value.x + " "+value.y + " "+value.z);
    }
}