package dev.simplemachine.opengl.gltf;

import java.util.Arrays;

class GltfAnimation {
    GltfAnimationChannel[] channels;
    GltfAnimationSampler[] samplers;
    String name;

    @Override
    public String toString() {
        return "GltfAnimation{" +
                "channels=" + Arrays.toString(channels) +
                ", samplers=" + Arrays.toString(samplers) +
                ", name='" + name + '\'' +
                '}';
    }
}
