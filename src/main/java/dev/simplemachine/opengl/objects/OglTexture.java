package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL20;

public class OglTexture extends AbstractOglObject{

    public OglTexture() {
        super(GL20.glGenTextures());
    }
}
