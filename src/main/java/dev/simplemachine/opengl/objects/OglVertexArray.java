package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL30;

public class OglVertexArray extends AbstractOglObject {

    public OglVertexArray() {
        super(GL30.glGenVertexArrays());
    }
}
