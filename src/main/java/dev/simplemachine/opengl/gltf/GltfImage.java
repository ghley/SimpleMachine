package dev.simplemachine.opengl.gltf;

class GltfImage {
    String uri;
    String mimeType;
    Integer bufferView;
    String name;

    @Override
    public String toString() {
        return "GltfImage{" +
                "uri='" + uri + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", bufferView=" + bufferView +
                ", name='" + name + '\'' +
                '}';
    }
}
