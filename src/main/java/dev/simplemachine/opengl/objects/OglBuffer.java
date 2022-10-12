package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL45;

import java.util.Arrays;

public class OglBuffer extends AbstractOglObject {

    private final int num;
    private final int[] sizes;
    private final int[] offset;
    private final int[] bindingIndices;
    private final int stride;
    private final DataType dataType;
    private final BufferType bufferType;

    public OglBuffer(BufferType bufferType, DataType dataType, int num, int[] sizes, int[] indices, int flags) {
        super(GL45.glCreateBuffers());
        this.bufferType = bufferType;
        this.num = num;
        this.sizes = sizes;
        this.bindingIndices = indices;
        this.dataType = dataType;
        this.offset = new int[sizes.length];
        for (int q = 0; q < offset.length - 1; q++) {
            offset[q+1] = offset[q]+sizes[q];
        }
        stride = Arrays.stream(sizes).sum();

        GL45.glNamedBufferStorage(id, num * stride * dataType.bitSize / 8, flags);
    }

    public void setData(float[] data) {
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public void setData(int[] data) {
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public int getNum() {
        return num;
    }

    public int getStride() {
        return stride;
    }

    public DataType getDataType() {
        return dataType;
    }

    public BufferType getBufferType() {
        return bufferType;
    }

    public int[] getSizes() {
        return sizes;
    }

    public int getOffset(int subEntry) {
        return offset[subEntry];
    }

    public int[] getBindingIndices() {
        return bindingIndices;
    }
}
