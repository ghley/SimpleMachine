package dev.simplemachine.gltf;

class GltfCameraPerspective {
    Number aspectRatio;
    Number yfov;
    Number zfar;
    Number znear;

    @Override
    public String toString() {
        return "GltfCameraPerspective{" +
                "aspectRatio=" + aspectRatio +
                ", yfov=" + yfov +
                ", zfar=" + zfar +
                ", znear=" + znear +
                '}';
    }
}
