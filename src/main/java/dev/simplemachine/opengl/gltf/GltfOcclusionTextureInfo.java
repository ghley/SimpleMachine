package dev.simplemachine.opengl.gltf;

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
