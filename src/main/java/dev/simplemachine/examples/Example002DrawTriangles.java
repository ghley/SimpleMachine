package dev.simplemachine.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.BufferBuilder;
import dev.simplemachine.opengl.ProgramBuilder;
import dev.simplemachine.opengl.glenum.*;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.OglVertexArray;
import org.lwjgl.opengl.GL11;

public class Example002DrawTriangles {
    public static void main(String[] args) {
        var machine = new SimpleMachine();
        machine.setInitCallback(Example002DrawTriangles::init);
        machine.setLoopCallback(Example002DrawTriangles::loop);
        machine.run();
    }

    static OglProgram program;

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
                .addVertexSubSize(2)
                .bufferType(BufferType.ARRAY_BUFFER)
                .numberOfEntries(6)
                .dataType(DataType.FLOAT)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();
        buffer.bind();
        buffer.setData(vertex);


        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();

    }

    public static void loop() {
        program.use();
    }
}
