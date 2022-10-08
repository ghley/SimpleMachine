package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;

public class OglTransformFeedback extends AbstractOglObject{

    public OglTransformFeedback() {
        super(GL45.glCreateTransformFeedbacks());
    }
}
