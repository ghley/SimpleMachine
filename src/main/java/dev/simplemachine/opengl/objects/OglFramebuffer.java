package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;

public class OglFramebuffer extends AbstractOglObject{
    public OglFramebuffer() {
        super(GL45.glCreateFramebuffers());
    }
}
