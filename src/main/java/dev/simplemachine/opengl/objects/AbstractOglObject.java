package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL43;

public abstract class AbstractOglObject {
    protected final int id;

    public AbstractOglObject(int id) {
        this.id = id;
    }

}
