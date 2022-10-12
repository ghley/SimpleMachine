package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.DataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VertexArrayBuilder {
    private VertexArrayBuilder() {

    }
    private List<VertexAttribute> staticFields = new ArrayList<>();
    private List<VertexAttribute> variableFields = new ArrayList<>();

    private int num;

    private int elementArraySize = 0;

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

    public VertexArrayBuilder setElementArraySize(int size) {
        elementArraySize = size;
        return this;
    }

    public OglVertexArray build() {
        var vao = new OglVertexArray();

        var staticMap = staticFields.stream()
                .collect(Collectors.groupingBy(VertexAttribute::type));
        for (var entry : staticMap.entrySet()) {
            var builder = BufferBuilder.newInstance()
                    .dataType(entry.getKey())
                    .numberOfEntries(num)
                    .flag(BufferStorageType.DYNAMIC_STORAGE);
            for (var subEntry : entry.getValue()) {
                builder.addVertexSubSize(subEntry.size(), subEntry.binding());
            }
            vao.addBuffer(builder.build());
        }

        var variableMap = variableFields.stream()
                .collect(Collectors.groupingBy(VertexAttribute::type));
        for (var entry : variableMap.entrySet()) {
            for (var subEntry : entry.getValue()) {
                var builder = BufferBuilder.newInstance()
                        .dataType(entry.getKey())
                        .numberOfEntries(num)
                        .flag(BufferStorageType.DYNAMIC_STORAGE);
                builder.addVertexSubSize(subEntry.size(), subEntry.binding());
                vao.addBuffer(builder.build());
            }
        }

        if (elementArraySize > 0) {
            var elemBuffer = BufferBuilder.newInstance()
                    .dataType(DataType.U_INT)
                    .numberOfEntries(elementArraySize)
                    .build();
            vao.addElementBuffer(elemBuffer);
        }

        return vao;
    }


}
