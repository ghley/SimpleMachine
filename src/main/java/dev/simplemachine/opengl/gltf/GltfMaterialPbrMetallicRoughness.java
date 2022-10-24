package dev.simplemachine.opengl.gltf;

import java.util.Arrays;

class GltfMaterialPbrMetallicRoughness {
    Number[] baseColorFactor;
    GltfTextureInfo baseColorTexture;
    Number metallicFactor;
    Number roughnessFactor;
    GltfTextureInfo metallicRoughnessTexture;

    @Override
    public String toString() {
        return "GltfMaterialPbrMetallicRoughness{" +
                "baseColorFactor=" + Arrays.toString(baseColorFactor) +
                ", baseColorTexture=" + baseColorTexture +
                ", metallicFactor=" + metallicFactor +
                ", roughnessFactor=" + roughnessFactor +
                ", metallicRoughnessTexture=" + metallicRoughnessTexture +
                '}';
    }
}
