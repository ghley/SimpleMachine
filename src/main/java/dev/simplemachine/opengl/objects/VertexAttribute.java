package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.DataType;

public record VertexAttribute(DataType type, int size, int binding) {
}
