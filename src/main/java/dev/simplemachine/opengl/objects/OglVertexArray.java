package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;

public class OglVertexArray extends AbstractOglObject {

    private PrimitiveType primitiveType;
    private Map<Integer, VertexArrayAccessor> map = new HashMap<>();
    private OglBuffer elementBuffer;
    private DataType elementBufferType;

    public OglVertexArray() {
        super(GL45.glCreateVertexArrays());
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    void addAccessor(int bindingIndex, VertexArrayAccessor accessor) {
        map.put(bindingIndex, accessor);
        var buffer = accessor.buffer();
        var byteOffset = accessor.byteOffset();
        var count = accessor.count();
        var type = accessor.dataType();
        var normalize = accessor.normalize();
        var byteStride = accessor.byteStride();
        glVertexArrayVertexBuffer(id, bindingIndex, buffer.id, byteOffset, byteStride);
        glVertexArrayAttribFormat(id, bindingIndex, count, type.constant, normalize, byteOffset);
        glVertexArrayAttribBinding(id, bindingIndex, bindingIndex);
        glEnableVertexArrayAttrib(id, bindingIndex);
    }

    void addElementBuffer(OglBuffer elementBuffer, DataType elementBufferType) {
        this.elementBufferType = elementBufferType;
        this.elementBuffer = elementBuffer;
        glVertexArrayElementBuffer(id, elementBuffer.id);
    }

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
    }

    public void drawElements() {
        bind();
        glDrawElements(primitiveType.constant, elementBuffer.getByteLength() / DataType.U_INT.bitSize * 8, GL_UNSIGNED_INT, 0);
    }

    public void drawElementsBaseVertex(int num, int baseVertex) {
        bind();
        GL45.glDrawElementsBaseVertex(primitiveType.constant, num, GL_UNSIGNED_INT, 0, baseVertex);
    }

    public void drawArraysInstanced(int first, int num, int numInstances) {
        bind();
        GL45.glDrawArraysInstanced(primitiveType.constant, first, num, numInstances);
    }

    public OglBuffer getElementBuffer() {
        return elementBuffer;
    }
}
