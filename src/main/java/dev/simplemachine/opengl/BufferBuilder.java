package dev.simplemachine.opengl;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import dev.simplemachine.opengl.objects.OglBuffer;

public final class BufferBuilder {

    private BufferType bufferType;
    private int flag = 0;
    private int size = 0;

    private BufferBuilder() {

    }

    public static BufferBuilder newInstance() {
        return new BufferBuilder();
    }

    public BufferBuilder type(BufferType type) {
        this.bufferType = bufferType;
        return this;
    }

    public BufferBuilder flag(BufferStorageType... storageType) {
        for (var flag : storageType) {
            this.flag |= flag.constant;
        }
        return this;
    }

    public BufferBuilder size(DataType type, int num) {
        this.size = size * type.bitSize / 8;
        return this;
    }

    public OglBuffer build() {
        var buffer = new OglBuffer(bufferType, size, flag);
        return buffer;
    }
}
