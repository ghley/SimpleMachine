package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import org.lwjgl.opengl.GL45;

import java.util.Arrays;

public class OglBuffer extends AbstractOglObject {

    private int size = -1;
    private BufferType bufferType;

    public OglBuffer(BufferType type, int size, BufferStorageType... flags) {
        super(GL45.glCreateBuffers());
        this.size = size;
        this.bufferType = type;
        GL45.glNamedBufferStorage(type.constant, size, Arrays.stream(flags)
                .mapToInt(BufferStorageType::getConstant)
                .reduce(0, (a,b)->a|b));
    }

    public OglBuffer(BufferType type, int size, int flags) {
        super(GL45.glCreateBuffers());
        this.bufferType = type;
        this.size = size;
        GL45.glNamedBufferStorage(type.constant, size, flags);
    }

    public int getSize() {
        return size;
    }

    public BufferType getBufferType() {
        return bufferType;
    }
}
