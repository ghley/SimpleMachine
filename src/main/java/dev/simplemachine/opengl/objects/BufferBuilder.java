package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;

public final class BufferBuilder {

    private int flags = 0;
    private int byteLength;

    private BufferBuilder() {

    }

    public static BufferBuilder newInstance() {
        return new BufferBuilder();
    }

    public BufferBuilder flag(BufferStorageType... storageType) {
        for (var flag : storageType) {
            this.flags |= flag.constant;
        }
        return this;
    }

    public BufferBuilder byteLength(int byteLength) {
        this.byteLength = byteLength;
        return this;
    }

    public OglBuffer build() {
        var buffer = new OglBuffer(byteLength, flags);
        return buffer;
    }
}
