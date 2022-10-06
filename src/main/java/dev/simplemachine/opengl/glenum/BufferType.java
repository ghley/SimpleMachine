package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL20;

public enum BufferType {
    ARRAY_BUFFER(GL20.GL_ARRAY_BUFFER);

    public int constant;

    BufferType(int constant) {
        this.constant = constant;
    }
}
