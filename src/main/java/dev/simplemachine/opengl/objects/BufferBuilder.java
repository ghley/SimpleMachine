package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class BufferBuilder {

    private record SizeIndexPair(int size, int index) {

    }

    private DataType dataType;
    private int flags = 0;
    private int num = 0;
    private BufferType bufferType;
    private List<Integer> sizes = new ArrayList<>();

    private BufferBuilder() {

    }

    public static BufferBuilder newInstance() {
        return new BufferBuilder();
    }

    public BufferBuilder bufferType(BufferType bufferType) {
        this.bufferType = bufferType;
        return this;
    }

    public BufferBuilder dataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public BufferBuilder flag(BufferStorageType... storageType) {
        for (var flag : storageType) {
            this.flags |= flag.constant;
        }
        return this;
    }

    public BufferBuilder numberOfEntries(int num) {
        this.num = num;
        return this;
    }

    public BufferBuilder addVertexSubSize(int num) {
        sizes.add(num);
        return this;
    }

    public BufferBuilder elementArray() {
        bufferType = BufferType.ELEMENT_ARRAY_BUFFER;
        return addVertexSubSize(1);
    }

    public OglBuffer build() {
        boolean allSet = dataType != null && num > 0 && !sizes.isEmpty();
        if (!allSet) {
            throw new RuntimeException("Missing params.");
        }

        var sizeArray = sizes.stream().mapToInt(i->i).toArray();
        var buffer = new OglBuffer(bufferType, dataType, num, sizeArray, flags);
        return buffer;
    }

    @Override
    public String toString() {
        return "BufferBuilder{" +
                "dataType=" + dataType +
                ", flags=" + flags +
                ", num=" + num +
                ", bufferType=" + bufferType +
                ", sizes=" + sizes +
                '}';
    }
}
