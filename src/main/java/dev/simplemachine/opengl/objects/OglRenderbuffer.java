package dev.simplemachine.opengl.objects;


import org.lwjgl.opengl.GL45;

public class OglRenderbuffer extends AbstractOglObject{

    public OglRenderbuffer() {
        super(GL45.glCreateRenderbuffers());
    }
}
