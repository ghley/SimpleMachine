package dev.simplemachine.gltf;

class GltfSampler {
    Integer magFilter;
    Integer minFilter;
    Integer wrapS;
    Integer wrapT;
    String name;

    @Override
    public String toString() {
        return "GltfSampler{" +
                "magFilter=" + magFilter +
                ", minFilter=" + minFilter +
                ", wrapS=" + wrapS +
                ", wrapT=" + wrapT +
                ", name='" + name + '\'' +
                '}';
    }
}
