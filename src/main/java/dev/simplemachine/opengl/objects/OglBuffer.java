package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;


public class OglBuffer extends AbstractOglObject {
    private final int byteLength;

    public OglBuffer(int byteLength, int flags) {
        super(GL45.glCreateBuffers());
        this.byteLength = byteLength;
        GL45.glNamedBufferStorage(id, byteLength, flags);
    }

    public float[] getDataFv() {
        var arr = new float[byteLength/4];
        GL45.glGetNamedBufferSubData(id, 0, arr);
        return arr;
    }

    public int[] getDataIv() {
        var arr = new int[byteLength/4];
        GL45.glGetNamedBufferSubData(id, 0, arr);
        return arr;
    }

    public void setData(int byteOffset, byte[] data) {
        try (var memoryStack = MemoryStack.stackPush()) {
            var bb = memoryStack.malloc(data.length);
            bb.put(data);
            GL45.glNamedBufferSubData(id, byteOffset,  bb);
        }
    }

    public void setData(byte[] data) {
        try (var memoryStack = MemoryStack.stackPush()) {
            var bb = memoryStack.malloc(data.length);
            bb.put(data);
            GL45.glNamedBufferSubData(id, 0,  bb);
        }
    }

    public void setData(float[] data) {
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public void setData(int byteOffset, float[] data) {
        GL45.glNamedBufferSubData(id, byteOffset, data);
    }

    public void setData(int[] data) {
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public int getByteLength() {
        return byteLength;
    }
}
