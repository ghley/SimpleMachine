package dev.simplemachine.gltf;

import java.util.Arrays;

class GltfMesh {
    GltfMeshPrimitive[] primitives;
    Number[] weights;
    String name;

    @Override
    public String toString() {
        return "GltfMesh{" +
                "primitives=" + Arrays.toString(primitives) +
                ", weights=" + Arrays.toString(weights) +
                ", name='" + name + '\'' +
                '}';
    }
}
