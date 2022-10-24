package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL45.glEnableVertexArrayAttrib;

public class GLTFBuilder {

    private Map<Integer, Integer> indexToBuffer = new HashMap<>();

    private int vao;

    private Integer elementBuffer = null;

    private GLTFBuilder() {
        vao = GL45.glCreateVertexArrays();

    }

    public static GLTFBuilder newInstance() {
        return new GLTFBuilder();
    }

    public GLTFBuilder addBuffer(int index, byte[] data) {
        int buffer = GL45.glCreateBuffers();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            var bb = stack.malloc(data.length);
            bb.put(data);
            GL45.glNamedBufferStorage(buffer,bb, BufferStorageType.DYNAMIC_STORAGE.constant);
        }
        indexToBuffer.put(index, buffer);
        return this;
    }

    public GLTFBuilder bind(int binding, int bufferIndex, int offset, int stride, int size, int dataType) {
        var buffer = indexToBuffer.get(bufferIndex);
        glVertexArrayVertexBuffer(vao, binding, buffer, offset, stride);
        glVertexArrayAttribFormat(vao, binding, size, dataType, false, offset);
        glVertexArrayAttribBinding(vao, binding, binding);
        glEnableVertexArrayAttrib(vao, binding);
        return this;
    }

    public GLTFBuilder bindElementBuffer(int bufferIndex) {
        glVertexArrayElementBuffer(vao, indexToBuffer.get(bufferIndex));
        return this;
    }




}
