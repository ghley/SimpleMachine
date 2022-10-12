package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL43;

import java.util.logging.Logger;


/**
 * Object wrappers for all OpenGL Objects, low level
 */
public abstract class AbstractOglObject {
    protected final int id;

    public AbstractOglObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void destroy() {
    }
}
