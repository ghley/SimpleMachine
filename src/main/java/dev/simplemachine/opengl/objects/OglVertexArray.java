package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;

public class OglVertexArray extends AbstractOglObject {

    public OglVertexArray() {
        super(GL45.glCreateVertexArrays());
    }
}
