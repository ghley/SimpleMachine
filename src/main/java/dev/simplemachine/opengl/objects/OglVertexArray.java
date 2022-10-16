package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.PrimitiveType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;

public class OglVertexArray extends AbstractOglObject {

    record BufferSubEntryPair(OglBuffer buffer, int subEntry) {

    }

    private Map<Integer, BufferSubEntryPair> map = new HashMap<>();

    private OglBuffer elementBuffer;

    private PrimitiveType primitiveType;

    public OglVertexArray() {
        super(GL45.glCreateVertexArrays());
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    void addBuffer(OglBuffer buffer, int... bindingIndices) {
        int stride = buffer.getStride() * buffer.getDataType().bitSize / 8;
        for (int q = 0; q < buffer.getSizes().length; q++) {
            if (!buffer.isInterleaved()) {
                stride = buffer.getSizes()[q] * buffer.getDataType().bitSize / 8;
            }
            int binding = bindingIndices[q];
            map.put(binding, new BufferSubEntryPair(buffer, q));
            glVertexArrayVertexBuffer(id, binding, buffer.id, buffer.getOffset(q), stride);
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

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
    }

    public void drawArrays(int num) {
        bind();
        glDrawArrays(primitiveType.constant, 0, map.values().stream().findFirst().get().buffer.getNum());
    }

    public void drawElements(int num) {
        bind();
        glDrawElements(primitiveType.constant, elementBuffer.getNum(), GL_UNSIGNED_INT, 0);
    }

    public List<OglBuffer> getBuffers() {
        return map.values().stream().map(BufferSubEntryPair::buffer).collect(Collectors.toList());
    }

    public void setData(int binding, float[] data) {
        map.get(binding).buffer.setData(data);
    }

    public void setData(int binding, int[] data) {
        map.get(binding).buffer.setData(data);
    }

    public void setSubData(int binding, float[] data) {
        var pair = map.get(binding);
        pair.buffer.setSubData(pair.subEntry, data);
    }

    public void setSubData(int binding, int[] data) {
        var pair = map.get(binding);
        pair.buffer.setSubData(pair.subEntry, data);
    }

    public OglBuffer getElementBuffer() {
        return elementBuffer;
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
