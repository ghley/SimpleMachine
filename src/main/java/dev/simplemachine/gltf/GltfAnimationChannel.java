package dev.simplemachine.gltf;

class GltfAnimationChannel {
    Integer sampler;
    GltfAnimationChannelTarget target;

    @Override
    public String toString() {
        return "GltfAnimationChannel{" +
                "sampler=" + sampler +
                ", target=" + target +
                '}';
    }
}
