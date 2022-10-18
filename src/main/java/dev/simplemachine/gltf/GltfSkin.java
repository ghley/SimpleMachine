package dev.simplemachine.gltf;

import java.util.Arrays;

class GltfSkin {
    Integer inverseBindMatrices;
    Integer skeleton;
    Integer[] joints;
    String name;

    @Override
    public String toString() {
        return "GltfSkin{" +
                "inverseBindMatrices=" + inverseBindMatrices +
                ", skeleton=" + skeleton +
                ", joints=" + Arrays.toString(joints) +
                ", name='" + name + '\'' +
                '}';
    }
}
