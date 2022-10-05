package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL20;

public class OglQuery extends AbstractOglObject{

    public OglQuery() {
        super(GL20.glGenQueries());
    }
}
