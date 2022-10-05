package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL20;

public enum BufferType implements GLConstant{
    ARRAY_BUFFER(GL20.GL_ARRAY_BUFFER);

    private int value;

    BufferType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
