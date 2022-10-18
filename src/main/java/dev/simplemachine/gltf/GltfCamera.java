package dev.simplemachine.gltf;

class GltfCamera {
    GltfCameraOrthographic orthographic;
    GltfCameraPerspective perspective;
    String type;
    String name;

    @Override
    public String toString() {
        return "GltfCamera{" +
                "orthographic=" + orthographic +
                ", perspective=" + perspective +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
