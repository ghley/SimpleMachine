package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.ShaderType;
import org.lwjgl.opengl.GL20;

public class OglShader extends AbstractOglObject {

    public OglShader(ShaderType shaderType) {
        super(GL20.glCreateShader(shaderType.getValue()));
    }

}
