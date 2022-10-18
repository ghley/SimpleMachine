package dev.simplemachine.gltf;

class GltfAsset {
    String copyright;
    String generator;
    String version;
    String minVersion;

    @Override
    public String toString() {
        return "GltfAsset{" +
                "copyright='" + copyright + '\'' +
                ", generator='" + generator + '\'' +
                ", version='" + version + '\'' +
                ", minVersion='" + minVersion + '\'' +
                '}';
    }
}
