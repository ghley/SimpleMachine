package dev.simplemachine.opengl.gltf;

class GltfBuffer {
    String uri;
    Integer byteLength;
    String name;

    @Override
    public String toString() {
        return "GltfBuffer{" +
                "uri='" + uri + '\'' +
                ", byteLength=" + byteLength +
                ", name='" + name + '\'' +
                '}';
    }
}
