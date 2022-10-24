package dev.simplemachine.opengl.gltf;

class GltfMaterial {
    String name;
    GltfMaterialPbrMetallicRoughness pbrMetallicRoughness;
    GltfNormalTextureInfo normalTexture;
    GltfOcclusionTextureInfo occlusionTexture;
    GltfTextureInfo emissiveFactor;

    @Override
    public String toString() {
        return "GltfMaterial{" +
                "name='" + name + '\'' +
                ", pbrMetallicRoughness=" + pbrMetallicRoughness +
                ", normalTexture=" + normalTexture +
                ", occlusionTexture=" + occlusionTexture +
                ", emissiveFactor=" + emissiveFactor +
                '}';
    }
}
