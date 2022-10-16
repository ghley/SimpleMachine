package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.junit.jupiter.api.Assertions;


import java.util.Arrays;

class BufferBuilderTest {

    @Test
    public void testBuffer() {
        GLFW.glfwInit();
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        long window = GLFW.glfwCreateWindow(1, 1, "Test", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        var buffer = BufferBuilder.newInstance()
                .dataType(DataType.FLOAT)
                .bufferType(BufferType.ARRAY_BUFFER)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .numberOfEntries(2)
                .addVertexSubSize(2)
                .addVertexSubSize(3)
                .build();

        var a2 = new float[]{0, 1, 0, 2};
        var a3 = new float[]{0, 1, 2, 0, 2, 3};

        buffer.setSubData(0, a2);
        buffer.setSubData(1, a3);

        var readArray = buffer.getDataFv();

        Assertions.assertArrayEquals(a2, Arrays.copyOf(readArray, 4));
        Assertions.assertArrayEquals(a3, Arrays.copyOfRange(readArray, 4, 10));

        GL.destroy();
    }

    @Test
    public void testInterleavedBuffer() {
        GLFW.glfwInit();
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        long window = GLFW.glfwCreateWindow(1, 1, "Test", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        var buffer = BufferBuilder.newInstance()
                .interleaved()
                .dataType(DataType.FLOAT)
                .bufferType(BufferType.ARRAY_BUFFER)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .numberOfEntries(2)
                .addVertexSubSize(2)
                .addVertexSubSize(3)
                .build();

        var a2 = new float[]{0, 1, 2, 3};
        var a3 = new float[]{0, -1, -2, -3, -4, -5};

        System.out.println(Arrays.toString(buffer.getDataFv()));
        buffer.setSubData(0, a2);
        buffer.setSubData(1, a3);

        var readArray = buffer.getDataFv();

        Assertions.assertArrayEquals(new float[] {
                0, 1, 0, -1, -2, 2, 3, -3, -4, -5
        }, buffer.getDataFv());

        GL.destroy();
    }

}