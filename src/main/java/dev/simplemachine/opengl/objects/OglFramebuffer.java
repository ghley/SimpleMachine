package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL30;

public class OglFramebuffer extends AbstractOglObject{
    public OglFramebuffer() {
        super(GL30.glGenFramebuffers());
    }
}
