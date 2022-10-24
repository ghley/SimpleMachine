package dev.simplemachine.opengl.gltf;

class GltfNormalTextureInfo extends GltfTextureInfo{
    Number scale;

    @Override
    public String toString() {
        return "GltfNormalTextureInfo{" +
                "scale=" + scale +
                ", index=" + index +
                ", texCoord=" + texCoord +
                '}';
    }
}
