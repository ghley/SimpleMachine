package dev.simplemachine.opengl.gltf;

import java.util.Arrays;

class GltfScene {
    Integer[] nodes;
    String name;

    @Override
    public String toString() {
        return "GltfScene{" +
                "nodes=" + Arrays.toString(nodes) +
                ", name='" + name + '\'' +
                '}';
    }
}
