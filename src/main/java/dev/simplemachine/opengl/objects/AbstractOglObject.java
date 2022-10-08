package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL43;


/**
 * Object wrappers for all OpenGL Objects, low level
 *
 * Every Object has a unique ID, so we can implement hash and equals easily
 */
public abstract class AbstractOglObject {
    protected final int id;

    public AbstractOglObject(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractOglObject that = (AbstractOglObject) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
