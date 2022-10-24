package dev.simplemachine.opengl.gltf;

import java.util.Arrays;

class GltfNode {
    Integer camera;
    Integer[] children;
    Integer skin;
    Number[] matrix;
    Integer mesh;
    Number[] rotation;
    Number[] scale;
    Number[] translation;
    Number[] weights;
    String name;

    @Override
    public String toString() {
        return "GltfNode{" +
                "camera=" + camera +
                ", children=" + Arrays.toString(children) +
                ", skin=" + skin +
                ", matrix=" + Arrays.toString(matrix) +
                ", mesh=" + mesh +
                ", rotation=" + Arrays.toString(rotation) +
                ", scale=" + Arrays.toString(scale) +
                ", translation=" + Arrays.toString(translation) +
                ", weights=" + Arrays.toString(weights) +
                ", name='" + name + '\'' +
                '}';
    }
}
