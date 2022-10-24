package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.PrimitiveType;
import java.util.HashMap;
import java.util.Map;

public class VertexArrayBuilder {
    private VertexArrayBuilder() {

    }

    private PrimitiveType mode;
    private Map<Integer, VertexArrayAccessor> accessors = new HashMap<>();
    private VertexArrayAccessor elementBuffer;

    public static VertexArrayBuilder newInstance() {
        return new VertexArrayBuilder();
    }

    public VertexArrayBuilder addAccessor(int bindingIndex, VertexArrayAccessor accessor) {
        accessors.put(bindingIndex, accessor);
        return this;
    }

    public VertexArrayBuilder addElementBuffer(VertexArrayAccessor accessor) {
        elementBuffer = accessor;
        return this;
    }

    public VertexArrayBuilder primitiveMode(PrimitiveType type) {
        mode = type;
        return this;
    }

    public OglVertexArray build() {
        var vao = new OglVertexArray();
        vao.setPrimitiveType(mode);
        for (var pair : accessors.entrySet()) {
            vao.addAccessor(pair.getKey(), pair.getValue());
        }
        if (elementBuffer != null) {
            vao.addElementBuffer(elementBuffer.buffer(), elementBuffer.dataType());
        }
        return vao;
    }
}
