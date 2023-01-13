package dev.simplemachine.model;

import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.OglTexture;
import dev.simplemachine.opengl.objects.OglVertexArray;

import java.util.HashMap;
import java.util.Map;

public class StaticMesh {
    private OglVertexArray vao;
    private Map<Integer, OglTexture> textures = new HashMap<>();


    public StaticMesh() {

    }

    public void setVao(OglVertexArray vao) {
        this.vao = vao;
    }

    public Map<Integer, OglTexture> getTextures() {
        return textures;
    }

    public OglVertexArray getVao() {
        return vao;
    }
}
