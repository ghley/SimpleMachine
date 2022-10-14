package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class VertexArrayBuilder {
    private VertexArrayBuilder() {

    }
    private List<VertexAttribute> staticFields = new ArrayList<>();
    private List<VertexAttribute> variableFields = new ArrayList<>();

    private int num;

    private int elementArraySize = 0;

    private PrimitiveType type;

    public static VertexArrayBuilder newInstance() {
        return new VertexArrayBuilder();
    }

    public VertexArrayBuilder numVertices(int num) {
        this.num = num;
        return this;
    }

    public VertexArrayBuilder addStaticField(VertexAttribute attribute) {
        staticFields.add(attribute);
        return this;
    }

    public VertexArrayBuilder addVariableField(VertexAttribute attribute) {
        variableFields.add(attribute);
        return this;
    }

    public VertexArrayBuilder elementArray(int size) {
        elementArraySize = size;
        return this;
    }

    public VertexArrayBuilder primitiveType(PrimitiveType type) {
        this.type = type;
        return this;
    }

    public OglVertexArray build() {
        Logger.getAnonymousLogger().info("Creating new VertexArray: "+toString());
        var vao = new OglVertexArray();

        var staticMap = staticFields.stream()
                .collect(Collectors.groupingBy(VertexAttribute::type));
        for (var entry : staticMap.entrySet()) {
            var builder = BufferBuilder.newInstance()
                    .bufferType(BufferType.ARRAY_BUFFER)
                    .dataType(entry.getKey())
                    .numberOfEntries(num)
                    .flag(BufferStorageType.DYNAMIC_STORAGE);
            for (var subEntry : entry.getValue()) {
                builder.addVertexSubSize(subEntry.size());
            }
            vao.addBuffer(builder.build(), entry.getValue().stream().mapToInt(e->e.binding()).toArray());
        }

        var variableMap = variableFields.stream()
                .collect(Collectors.groupingBy(VertexAttribute::type));
        for (var entry : variableMap.entrySet()) {
            for (var subEntry : entry.getValue()) {
                var builder = BufferBuilder.newInstance()
                        .bufferType(BufferType.ARRAY_BUFFER)
                        .dataType(entry.getKey())
                        .numberOfEntries(num)
                        .flag(BufferStorageType.DYNAMIC_STORAGE);
                builder.addVertexSubSize(subEntry.size());
                vao.addBuffer(builder.build(), subEntry.binding());
            }
        }

        if (elementArraySize > 0) {
            var elemBuffer = BufferBuilder.newInstance()
                    .bufferType(BufferType.ELEMENT_ARRAY_BUFFER)
                    .dataType(DataType.U_INT)
                    .flag(BufferStorageType.DYNAMIC_STORAGE)
                    .numberOfEntries(elementArraySize)
                    .elementArray()
                    .build();
            vao.addElementBuffer(elemBuffer);
        }

        vao.setPrimitiveType(type);
        return vao;
    }

    @Override
    public String toString() {
        return "VertexArrayBuilder{" +
                "staticFields=" + staticFields +
                ", variableFields=" + variableFields +
                ", num=" + num +
                ", elementArraySize=" + elementArraySize +
                '}';
    }
}
