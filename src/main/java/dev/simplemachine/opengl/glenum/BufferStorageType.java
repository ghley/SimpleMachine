package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL44;

public enum BufferStorageType {
    DYNAMIC_STORAGE(GL44.GL_DYNAMIC_STORAGE_BIT),
    MAP_READ(GL44.GL_MAP_READ_BIT),
    MAP_WRITE(GL44.GL_MAP_WRITE_BIT),
    MAP_PERSISTENT(GL44.GL_MAP_PERSISTENT_BIT),
    MAP_COHERENT(GL44.GL_MAP_COHERENT_BIT),
    CLIENT_STORAGE(GL44.GL_CLIENT_STORAGE_BIT);

    public int constant;

    BufferStorageType(int constant) {
        this.constant = constant;
    }

    public int getConstant() {
        return constant;
    }
}
