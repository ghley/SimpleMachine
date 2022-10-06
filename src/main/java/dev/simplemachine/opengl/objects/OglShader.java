package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.ShaderType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class OglShader extends AbstractOglObject {

    public OglShader(ShaderType shaderType) {
        super(GL20.glCreateShader(shaderType.constant));
    }

    public void delete() {
        GL20.glDeleteShader(id);
    }

    public boolean compile(String src) {
        GL20.glShaderSource(id, src);
        GL20.glCompileShader(id);
        return isCompilationSuccessful();
    }

    public String getInfoLog() {
        return GL20.glGetShaderInfoLog(id);
    }

    private boolean isCompilationSuccessful() {
        return GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_TRUE;
    }
}
