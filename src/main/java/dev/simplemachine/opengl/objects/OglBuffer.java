package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import org.lwjgl.opengl.GL45;

import java.util.Arrays;
import java.util.logging.Logger;

public class OglBuffer extends AbstractOglObject {

    private final int num;
    private final int[] sizes;
    private final int[] offset;
    private final int stride;
    private final DataType dataType;
    private final BufferType bufferType;
    private final boolean interleaved;

    public OglBuffer(BufferType bufferType, DataType dataType, int num, int[] sizes, int flags, boolean interleaved) {
        super(GL45.glCreateBuffers());
        this.bufferType = bufferType;
        this.num = num;
        this.sizes = sizes;
        this.dataType = dataType;
        this.interleaved = interleaved;
        this.offset = new int[sizes.length];
        if (interleaved) {
            for (int q = 0; q < offset.length - 1; q++) {
                offset[q + 1] = offset[q] + sizes[q];
            }
            stride = Arrays.stream(sizes).sum();
        } else {
            for (int q = 0; q < offset.length - 1; q++) {
                offset[q + 1] = offset[q] + sizes[q] * num;
            }
            stride = -1;
        }
        GL45.glNamedBufferStorage(id, num * Arrays.stream(sizes).sum() * dataType.bitSize / 8, flags);
    }

    public float[] getDataFv() {
        var arr = new float[getArraySize()];
        GL45.glGetNamedBufferSubData(id, 0, arr);
        return arr;
    }

    public int[] getDataIv() {
        var arr = new int[getArraySize()];
        GL45.glGetNamedBufferSubData(id, 0, arr);
        return arr;
    }

    public void setData(float[] data) {
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public void setData(int[] data) {
        GL45.glNamedBufferSubData(id, 0, data);
    }

    public void setSubData(int subEntry, float[] data) {
        if (interleaved) {
            Logger.getAnonymousLogger().info("Updating entries in interleaved buffers is slow!");
            var fullArray = getDataFv();
            int offset = getOffset(subEntry);
            int size = sizes[subEntry];
            for (int i = 0; i < num; i++) {
                System.arraycopy(data, i * size, fullArray, stride * i + offset, size);
            }
            GL45.glNamedBufferSubData(id, 0, fullArray);
        }else {
            GL45.glNamedBufferSubData(id, getOffset(subEntry) * dataType.bitSize / 8, data);
        }
    }

    public void setSubData(int subEntry, int[] data) {
        if (interleaved) {
            throw new RuntimeException("Can't set sub entry data for interleaved buffers.");
        } else {
            GL45.glNamedBufferSubData(id, getOffset(subEntry) * dataType.bitSize / 8, data);
        }
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

    public boolean isInterleaved() {
        return interleaved;
    }

    public int getArraySize() {
        return num * Arrays.stream(sizes).sum();
    }

    @Override
    public String toString() {
        return "OglBuffer{" +
                "num=" + num +
                ", sizes=" + Arrays.toString(sizes) +
                ", offset=" + Arrays.toString(offset) +
                ", stride=" + stride +
                ", dataType=" + dataType +
                ", bufferType=" + bufferType +
                ", interleaved=" + interleaved +
                ", id=" + id +
                '}';
    }
}
