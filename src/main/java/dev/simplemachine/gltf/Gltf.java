package dev.simplemachine.gltf;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

class Gltf {
     GltfAccessor[] accessors;
     GltfAnimation[] animations;
     GltfAsset asset;
     GltfBuffer[] buffers;
     GltfBufferView[] bufferViews;
     GltfCamera[] cameras;
     GltfImage[] images;
     GltfMaterial[] materials;
     GltfMesh[] meshes;
     GltfNode[] nodes;
     GltfSampler[] samplers;
     GltfScene[] scenes;

     Integer scene;
     GltfSkin[] skins;
     GltfTexture[] textures;

     @Override
     public String toString() {
          return "Gltf{" +
                  "accessors=" + Arrays.toString(accessors) +
                  ", animations=" + Arrays.toString(animations) +
                  ", asset=" + asset +
                  ", buffers=" + Arrays.toString(buffers) +
                  ", bufferViews=" + Arrays.toString(bufferViews) +
                  ", cameras=" + Arrays.toString(cameras) +
                  ", images=" + Arrays.toString(images) +
                  ", materials=" + Arrays.toString(materials) +
                  ", meshes=" + Arrays.toString(meshes) +
                  ", nodes=" + Arrays.toString(nodes) +
                  ", samplers=" + Arrays.toString(samplers) +
                  ", scenes=" + Arrays.toString(scenes) +
                  ", scene=" + scene +
                  ", skins=" + Arrays.toString(skins) +
                  ", textures=" + Arrays.toString(textures) +
                  '}';
     }
}
