package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.OglVertexArray;

public class CStaticMesh implements Component {
    private OglVertexArray vao;
    private OglProgram program;

    public OglVertexArray getVao() {
        return vao;
    }

    public void setVao(OglVertexArray vao) {
        this.vao = vao;
    }

    public OglProgram getProgram() {
        return program;
    }

    public void setProgram(OglProgram program) {
        this.program = program;
    }
}
