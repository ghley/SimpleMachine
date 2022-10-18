package dev.simplemachine.gltf;

class GltfBufferView {
    Integer buffer;
    Integer byteOffset;
    Integer byteLength;
    Integer byteStride;
    Integer target;
    String name;

    @Override
    public String toString() {
        return "GltfBufferView{" +
                "buffer=" + buffer +
                ", byteOffset=" + byteOffset +
                ", byteLength=" + byteLength +
                ", byteStride=" + byteStride +
                ", target=" + target +
                ", name='" + name + '\'' +
                '}';
    }
}
