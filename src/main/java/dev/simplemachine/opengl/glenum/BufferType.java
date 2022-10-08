package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;

public enum BufferType {
    ARRAY_BUFFER(GL20.GL_ARRAY_BUFFER),
    COPY_READ_BUFFER(GL31.GL_COPY_READ_BUFFER),
    COPY_WRITE_BUFFER(GL31.GL_COPY_WRITE_BUFFER),
    DRAW_INDIRECT_BUFFER(GL40.GL_DRAW_INDIRECT_BUFFER),
    ELEMENT_ARRAY_BUFFER(GL20.GL_ELEMENT_ARRAY_BUFFER),
    PIXEL_PACK_BUFFER(GL21.GL_PIXEL_PACK_BUFFER),
    PIXEL_UNPACK_BUFFER(GL21.GL_PIXEL_UNPACK_BUFFER),
    TEXTURE_BUFFER(GL31.GL_TEXTURE_BUFFER),
    TRANSFORM_FEEDBACK_BUFFER(GL31.GL_TRANSFORM_FEEDBACK_BUFFER),
    UNIFORM_BUFFER(GL31.GL_UNIFORM_BUFFER);

    public int constant;

    BufferType(int constant) {
        this.constant = constant;
    }
}
