package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL20;

public enum ShaderType implements GLConstant {
    VERTEX_SHADER(GL20.GL_VERTEX_SHADER),
    FRAGMENT_SHADER(GL20.GL_FRAGMENT_SHADER);

    public int value;
    ShaderType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
