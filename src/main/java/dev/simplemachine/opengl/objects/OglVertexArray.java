package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.DataType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.glNamedBufferSubData;
import static org.lwjgl.opengl.GL45.glVertexArrayElementBuffer;

public class OglVertexArray extends AbstractOglObject {

    record BufferSubEntryPair(OglBuffer buffer, int subEntry) {

    }

    private Map<Integer, BufferSubEntryPair> map = new HashMap<>();

    private OglBuffer elementBuffer;

    public OglVertexArray() {
        super(GL45.glCreateVertexArrays());
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    void addBuffer(OglBuffer buffer) {
        for (int q = 0; q < buffer.getSizes().length; q++) {
            map.put(buffer.getBindingIndices()[q], new BufferSubEntryPair(buffer, q));
        }
    }

    void addElementBuffer(OglBuffer elementBuffer) {
        this.elementBuffer = elementBuffer;
        glVertexArrayElementBuffer(id, elementBuffer.id);
    }

    public void setData(int index, float[] data) {
        map.get(index).buffer.setData(data);
    }

    public void setData(int index, int[] data) {
        map.get(index).buffer.setData(data);
    }


    public void draw() {
        // FIXME should be way prettier
        if (elementBuffer != null) {
            glDrawElements(GL_TRIANGLES, elementBuffer.getNum(), GL_UNSIGNED_INT, 0);
        }else {
            glDrawArrays(GL_TRIANGLES, 0, map.values().stream().findFirst().get().buffer.getNum());
        }
    }


}
