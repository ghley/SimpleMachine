package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL33;

public class OglSampler extends AbstractOglObject {

    public OglSampler() {
        super(GL33.glGenSamplers());
    }
}
