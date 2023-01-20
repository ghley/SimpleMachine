package dev.simplemachine.physics;

import org.joml.Vector3f;

import java.util.Optional;
import java.util.OptionalDouble;

public class IntersectUtil {
    public static OptionalDouble intersects(Ray ray, AxisAlignedBoundingBox aabb) {
        var min = aabb.getMin();
        var max = aabb.getMax();
        var invDir = new Vector3f(1f / ray.direction().x, 1f / ray.direction().y,
                1f / ray.direction().z);
        var t1 = new Vector3f((min.x - ray.origin().x) * invDir.x,
                (min.y - ray.origin().y) * invDir.y,
                (min.z - ray.origin().z) * invDir.z);
        var t2 = new Vector3f((max.x - ray.origin().x) * invDir.x,
                (max.y - ray.origin().y) * invDir.y,
                (max.z - ray.origin().z) * invDir.z);

        float tMin = Math.max(Math.max(Math.min(t1.x, t2.x), Math.min(t1.y, t2.y)), Math.min(t1.z, t2.z));
        float tMax = Math.min(Math.min(Math.max(t1.x, t2.x), Math.max(t1.y, t2.y)), Math.max(t1.z, t2.z));

        if (tMax < 0 || tMin > tMax) {
            return OptionalDouble.empty();
        }else {
            return OptionalDouble.of(tMin);
        }
    }

    public static Optional<Vector3f> intersects(Ray ray, Plane plane) {
        float denominator = plane.normal().dot(ray.direction());
        if (denominator == 0) {
            return Optional.empty();
        }
        var diff = new Vector3f(ray.origin()).sub(plane.reference());
        float p1 = diff.dot(plane.normal());
        float p2 = ray.direction().dot(plane.normal());
        float p3 = p1 / p2;
        return Optional.of(new Vector3f(ray.origin()).sub(new Vector3f(ray.direction()).mul(p3)));
    }
}
