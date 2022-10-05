package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL20;

public class OglBuffer extends AbstractOglObject {

    public OglBuffer() {
        super(GL20.glGenBuffers());

    }

}
