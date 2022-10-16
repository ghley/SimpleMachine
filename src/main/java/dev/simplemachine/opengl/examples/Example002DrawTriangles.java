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

        vao = VertexArrayBuilder.newInstance()
                .elementArray(6)
                .addStaticField(new VertexAttribute(DataType.FLOAT, 2, 0))
                .numVertices(6)
                .primitiveType(PrimitiveType.TRIANGLES)
                .build();
        vao.setData(0, vertex);
        vao.getElementBuffer().setData(new int[]{0, 1, 2, 3, 4, 5});

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();
    }

    public static void loop() {
        program.use();
        vao.drawArrays(6);
    }
}
