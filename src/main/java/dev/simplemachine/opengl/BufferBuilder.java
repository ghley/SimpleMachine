package dev.simplemachine.opengl;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.objects.OglBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class BufferBuilder {

    private BufferType bufferType;
    private DataType dataType;
    private int flags = 0;
    private int num = 0;

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

    public OglBuffer build() {
        Logger.getAnonymousLogger().info("Creating new Buffer: "+toString());
        var buffer = new OglBuffer(bufferType, dataType, num, sizes.stream().mapToInt(i->i).toArray(), flags);
        return buffer;
    }

    @Override
    public String toString() {
        return "BufferBuilder{" +
                "bufferType=" + bufferType +
                ", dataType=" + dataType +
                ", flags=" + flags +
                ", num=" + num +
                ", sizes=" + sizes +
                '}';
    }
}
