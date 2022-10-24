package dev.simplemachine.opengl.gltf;

class GltfCameraOrthographic {
    Number xmag;
    Number ymag;
    Number zfar;
    Number znear;

    @Override
    public String toString() {
        return "GltfCameraOrthographic{" +
                "xmag=" + xmag +
                ", ymag=" + ymag +
                ", zfar=" + zfar +
                ", znear=" + znear +
                '}';
    }
}
