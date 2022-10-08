package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;

public class OglSampler extends AbstractOglObject {

    public OglSampler() {
        super(GL45.glCreateSamplers());
    }
}
