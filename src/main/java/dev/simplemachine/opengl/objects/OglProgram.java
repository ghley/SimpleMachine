package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL20;

public class OglProgram extends AbstractOglObject {

    public OglProgram() {
        super(GL20.glCreateProgram());
    }
}
