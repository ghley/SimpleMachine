package dev.simplemachine.gltf;

class GltfOcclusionTextureInfo extends GltfTextureInfo {
    Number strength;

    @Override
    public String toString() {
        return "GltfOcclusionTextureInfo{" +
                "strength=" + strength +
                ", index=" + index +
                ", texCoord=" + texCoord +
                '}';
    }
}
