package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL20;

public class OglShader extends AbstractOglObject {
    enum ShaderType {
        VERTEX(GL20.GL_VERTEX_SHADER), FRAGMENT(GL20.GL_FRAGMENT_SHADER);

        public final int glEnum;

        ShaderType(int id) {
            this.glEnum = id;
        }
    }

    public OglShader(ShaderType shaderType) {
        super(GL20.glCreateShader(shaderType.glEnum));
    }

}
