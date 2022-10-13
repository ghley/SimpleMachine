package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL45.glEnableVertexArrayAttrib;

public class OglVertexArray extends AbstractOglObject {

    record BufferSubEntryPair(OglBuffer buffer, int subEntry) {

    }

    private Map<Integer, BufferSubEntryPair> map = new HashMap<>();

    private OglBuffer elementBuffer;

    public OglVertexArray() {
        super(GL45.glCreateVertexArrays());
        System.out.println(this);
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    void addBuffer(OglBuffer buffer, int... bindingIndices) {
        int stride = buffer.getStride() * buffer.getDataType().bitSize / 8;
        for (int q = 0; q < buffer.getSizes().length; q++) {
            int binding = bindingIndices[q];
            System.out.println(String.format(
                    "Binding: %d to %d with offset %d, stride %d and size %d", buffer.id, binding,
                    buffer.getOffset(q), stride, buffer.getSizes()[q]));
            map.put(binding, new BufferSubEntryPair(buffer, q));
            glVertexArrayVertexBuffer(id, binding, buffer.id , buffer.getOffset(q), stride);
            glVertexArrayAttribFormat(id, binding, buffer.getSizes()[q], buffer.getDataType().constant,
                    false, buffer.getOffset(q));
            glVertexArrayAttribBinding(id, binding, binding);
            glEnableVertexArrayAttrib(id, binding);
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

    public void setElementArray(int[] data) {
        elementBuffer.setData(data);
    }


    public void draw() {
        bind();
        // FIXME should be way prettier
        if (elementBuffer != null) {
            glDrawElements(GL_TRIANGLES, elementBuffer.getNum(), GL_UNSIGNED_INT, 0);
        }else {
            glDrawArrays(GL_TRIANGLES, 0, map.values().stream().findFirst().get().buffer.getNum());
        }
    }

    @Override
    public String toString() {
        return "OglVertexArray{" +
                "id=" + id +
                ", map=" + map +
                ", elementBuffer=" + elementBuffer +
                '}';
    }
}
