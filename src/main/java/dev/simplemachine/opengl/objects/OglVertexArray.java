package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.DataType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

public class OglVertexArray extends AbstractOglObject {

    public OglVertexArray() {
        super(GL45.glCreateVertexArrays());
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void bind(int bindingIndex, OglBuffer buffer, int subEntry) {

    }


}
