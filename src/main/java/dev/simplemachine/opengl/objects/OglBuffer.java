package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferType;
import org.lwjgl.opengl.GL20;

public class OglBuffer extends AbstractOglObject {

    private BufferType type;

    public OglBuffer(BufferType type) {
        super(GL20.glGenBuffers());
        this.type = type;
    }

    public void bind() {
        GL20.glBindBuffer(type.constant, id);
    }

}
