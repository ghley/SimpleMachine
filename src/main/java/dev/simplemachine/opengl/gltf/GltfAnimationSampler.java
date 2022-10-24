package dev.simplemachine.opengl.gltf;

class GltfAnimationSampler {
    Integer input;
    String interpolation;
    Integer output;

    @Override
    public String toString() {
        return "GltfAnimationSampler{" +
                "input=" + input +
                ", interpolation='" + interpolation + '\'' +
                ", output=" + output +
                '}';
    }
}
