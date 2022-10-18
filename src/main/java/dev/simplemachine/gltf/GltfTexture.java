package dev.simplemachine.gltf;

class GltfTexture {
    Integer sampler;
    Integer source;
    String name;

    @Override
    public String toString() {
        return "GltfTexture{" +
                "sampler=" + sampler +
                ", source=" + source +
                ", name='" + name + '\'' +
                '}';
    }
}
