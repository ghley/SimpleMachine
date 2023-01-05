package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL31.GL_PRIMITIVE_RESTART;
import static org.lwjgl.opengl.GL31.glPrimitiveRestartIndex;

public class Example004PrimitiveRestart {
    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example004PrimitiveRestart::init);
        machine.setLoopCallback(Example004PrimitiveRestart::loop);
        machine.run();
    }

    static OglVertexArray vao;
    static OglProgram program;

    private static void init() {
        var vertexShader = """
                #version 450 core
                                
                uniform mat4 model_matrix;
                uniform mat4 projection_matrix;
                                
                layout (location = 0) in vec4 position;
                layout (location = 1) in vec4 color;
                                
                out vec4 vs_fs_color;
                                
                void main(void)
                {
                    const vec4 pos[3] = vec4[3](vec4(-0.3, -0.3, 0.0, 1.0), vec4(0.3, -0.3, 0.0, 1.0), vec4(-0.3, 0.3, 0.0, 1.0) );
                    vs_fs_color = color;
                    gl_Position = projection_matrix * (model_matrix * position);
                }
                """;

        var fragmentShader = """
                #version 450 core
                                
                in vec4 vs_fs_color;
                                
                layout (location = 0) out vec4 color;
                                
                void main(void)
                {
                    color = vs_fs_color;
                }
                """;

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vertexShader)
                .attach(ShaderType.FRAGMENT_SHADER, fragmentShader)
                .build();

        var cubePositions = new float[]{
                -1.0f, -1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f
        };

        var cubeColors = new float[]{
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 0.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f, 1.0f,
                0.5f, 0.5f, 0.5f, 1.0f
        };

        var indices = new int[]{
                0, 1, 2, 3, 6, 7, 4, 5,
                0xffff,
                2, 6, 0, 4, 1, 5, 3, 7
        };

        var ebo = BufferBuilder.newInstance()
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .byteLength(indices.length * 4)
                .build();
        ebo.setData(indices);

        var vbo = BufferBuilder.newInstance()
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .byteLength((cubeColors.length + cubePositions.length) * 4)
                .build();
        vbo.setData(0, cubePositions);
        vbo.setData(cubePositions.length * 4, cubeColors);

        vao = VertexArrayBuilder.newInstance()
                .primitiveMode(PrimitiveType.TRIANGLE_STRIP)
                .addElementBuffer(
                        new VertexArrayAccessor(ebo, 0, 0, 4, DataType.U_INT, 1, false))
                .addAccessor(0,
                        new VertexArrayAccessor(vbo, 0, 0, 4 * 4, DataType.FLOAT, 4, false))
                .addAccessor(1,
                        new VertexArrayAccessor(vbo, cubePositions.length * 4, 0, 4 * 4, DataType.FLOAT, 4, false))
                .build();

        GL11.glEnable(GL_PRIMITIVE_RESTART);
        glPrimitiveRestartIndex(0xffff);
    }

    static float t = 0;

    private static void loop() {
        t += 0.01f;

        program.use();

        Matrix4f model = new Matrix4f()
                .translate(new Vector3f(0, 0, -5))
                .rotate(t * 2 * (float) Math.PI, new Vector3f(0, 0, 1))
                .rotate(t * 2 * (float) Math.PI, new Vector3f(0, 1, 0));
        Matrix4f proj = new Matrix4f()
                .frustum(-1f, 1f, -1, 1, 1f, 500f);

        program.setUniform("model_matrix", model);
        program.setUniform("projection_matrix", proj);

        vao.drawElements();
    }


}
