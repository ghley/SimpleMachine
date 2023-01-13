package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import static org.lwjgl.opengl.GL31C.GL_UNIFORM_BUFFER;


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

    public short[] getDataSv() {
        var arr = new short[byteLength/2];
        GL45.glGetNamedBufferSubData(id, 0, arr);
        return arr;
    }

    public int[] getDataUSv() {
        var arr = getDataSv();
        var outArr = new int[arr.length];
        for (int q = 0; q < arr.length; q++) {
            outArr[q] = arr[q] & 0xffff;
        }
        return outArr;
    }

    public void setData(byte[] data) {
        try (var memoryStack = MemoryStack.stackPush()) {
            var bb = memoryStack.malloc(data.length);
            bb.put(data);
            GL45.glNamedBufferSubData(id, 0,  bb);
        }
    }

    public void bindUniformBufferBase(int bindingIndex) {
        GL45.glBindBufferBase(GL_UNIFORM_BUFFER, bindingIndex, id);
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

    @Override
    public String toString() {
        return "OglBuffer{" +
               "id=" + id +
               ", byteLength=" + byteLength +
               '}';
    }
}
