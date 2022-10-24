package dev.simplemachine.opengl.gltf;

import java.util.Arrays;
import java.util.Map;

class GltfMeshPrimitive {
    Map<String, Integer> attributes;
    Integer indices;
    Integer material;
    Integer mode;
    Object[] targets;

    @Override
    public String toString() {
        return "GltfMeshPrimitive{" +
                "attributes=" + attributes +
                ", indices=" + indices +
                ", material=" + material +
                ", mode=" + mode +
                ", targets=" + Arrays.toString(targets) +
                '}';
    }
}
