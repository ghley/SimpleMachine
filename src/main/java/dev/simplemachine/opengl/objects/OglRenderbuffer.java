package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL30;

public class OglRenderbuffer extends AbstractOglObject{

    public OglRenderbuffer() {
        super(GL30.glGenRenderbuffers());
    }
}
