package dev.simplemachine.opengl.gltf;

import java.util.Arrays;

class GltfAccessor {
    Integer bufferView;
    Integer byteOffset;
    Integer componentType;
    Boolean normalized;
    Integer count;
    String type;
    Number[] max;
    Number[] min;
    String name;

    @Override
    public String toString() {
        return "GltfAccessor{" +
                "bufferView=" + bufferView +
                ", byteOffset=" + byteOffset +
                ", componentType=" + componentType +
                ", normalized=" + normalized +
                ", count=" + count +
                ", type='" + type + '\'' +
                ", max=" + Arrays.toString(max) +
                ", min=" + Arrays.toString(min) +
                ", name='" + name + '\'' +
                '}';
    }
}
