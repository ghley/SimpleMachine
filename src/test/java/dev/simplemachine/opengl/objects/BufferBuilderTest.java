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
                .byteLength(10*4)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();

        var a2 = new float[]{0, 1, 0, 2};
        var a3 = new float[]{0, 1, 2, 0, 2, 3};

        buffer.setData(0, a2);
        buffer.setData(4*4, a3);

        var readArray = buffer.getDataFv();

        Assertions.assertArrayEquals(a2, Arrays.copyOf(readArray, 4));
        Assertions.assertArrayEquals(a3, Arrays.copyOfRange(readArray, 4, 10));

        GL.destroy();
    }
}