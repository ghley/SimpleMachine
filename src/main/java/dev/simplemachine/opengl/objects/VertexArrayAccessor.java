package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.DataType;

public record VertexArrayAccessor(OglBuffer buffer, int byteOffset, int byteStride, DataType dataType, int count, boolean normalize) {
}
