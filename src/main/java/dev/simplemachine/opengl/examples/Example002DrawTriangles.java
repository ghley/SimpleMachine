package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.objects.*;
import dev.simplemachine.opengl.glenum.*;

import java.util.Arrays;

public class Example002DrawTriangles {
    public static void main(String[] args) {
        var machine = new SimpleMachine();
        machine.setInitCallback(Example002DrawTriangles::init);
        machine.setLoopCallback(Example002DrawTriangles::loop);
        machine.run();
    }

    static OglProgram program;
    static OglVertexArray vao;

    public static void init() {
        String vert = """
                #version 450 core
                layout (location = 0) in vec2 pos;
                
                void main() {
                    gl_Position = vec4(pos.x, pos.y, 0.5f,  1.0f);
                }
                """;
        String frag = """
                #version 450 core
                out vec4 color;
                
                void main() {
                    color = vec4(0.1f, 0.2f, 0.1f, 1.0f);
                }
                """;

        var vertex = new float[] {
                -0.9f, -0.9f,
                0.85f, -0.9f,
                -0.9f, 0.85f,
                0.9f, -0.85f,
                0.9f, 0.9f,
                -0.85f, 0.9f
        };

        var buffer = BufferBuilder.newInstance()
                .byteLength(vertex.length * 4)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();
        buffer.setData(vertex);

        var elementBuffer = BufferBuilder.newInstance()
                .byteLength(6 * 4)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();
        elementBuffer.setData(new int[] {0, 1, 2, 3, 4, 5});

        vao = VertexArrayBuilder.newInstance()
                .primitiveMode(PrimitiveType.TRIANGLES)
                .addAccessor(0,
                        new VertexArrayAccessor(buffer, 0, 8,  DataType.FLOAT, 2, false)
                )
                .addElementBuffer(
                        new VertexArrayAccessor(elementBuffer, 0, 4, DataType.U_INT, 1, false)
                )
                .build();

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();
    }

    public static void loop() {
        program.use();
        vao.drawElements();
    }
}
