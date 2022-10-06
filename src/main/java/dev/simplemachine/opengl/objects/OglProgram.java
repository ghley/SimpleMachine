package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class OglProgram extends AbstractOglObject {

    public OglProgram() {
        super(GL20.glCreateProgram());
    }

    public void use() {
        GL20.glUseProgram(id);
    }

    public void delete() {
        GL20.glDeleteProgram(id);
    }

    public void addShader(OglShader shader) {
        GL20.glAttachShader(id, shader.id);
    }

    public void removeShader(OglShader shader) {
        GL20.glDetachShader(id, shader.id);
    }

    public boolean link() {
        GL20.glLinkProgram(id);
        return isLinkingSuccessful();
    }

    private boolean isLinkingSuccessful() {
        return GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL11.GL_TRUE;
    }

    public String getInfoLog() {
        return GL20.glGetProgramInfoLog(id);
    }
}
