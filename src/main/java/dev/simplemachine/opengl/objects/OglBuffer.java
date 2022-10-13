package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import org.lwjgl.opengl.GL45;

import java.util.Arrays;

public class OglBuffer extends AbstractOglObject {

    private final int num;
    private final int[] sizes;
    private final int[] offset;
    private final int stride;
    private final DataType dataType;
    private final BufferType bufferType;

    public OglBuffer(BufferType bufferType, DataType dataType, int num, int[] sizes, int flags) {
        super(GL45.glCreateBuffers());
        this.bufferType = bufferType;
        this.num = num;
        this.sizes = sizes;
        this.dataType = dataType;
        this.offset = new int[sizes.length];
        for (int q = 0; q < offset.length - 1; q++) {
            offset[q+1] = offset[q]+sizes[q];
        }
        stride = Arrays.stream(sizes).sum();

        GL45.glNamedBufferStorage(id, num * stride * dataType.bitSize / 8, flags);
        System.out.println(toString());
    }

    public void setData(float[] data) {
        System.out.println("Setting: "+Arrays.toString(data)+" for "+id);
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public void setData(int[] data) {
        System.out.println("Setting: "+Arrays.toString(data)+" for "+id);
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

    @Override
    public String toString() {
        return "OglBuffer{" +
                "id=" + id +
                ", num=" + num +
                ", sizes=" + Arrays.toString(sizes) +
                ", offset=" + Arrays.toString(offset) +
                ", stride=" + stride +
                ", dataType=" + dataType +
                ", bufferType=" + bufferType +
                '}';
    }
}
