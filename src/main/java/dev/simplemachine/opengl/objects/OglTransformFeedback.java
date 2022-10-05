package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL41;

public class OglTransformFeedback extends AbstractOglObject{

    public OglTransformFeedback() {
        super(GL41.glGenTransformFeedbacks());
    }
}
