package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

public class Example003DrawCommands {

    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example003DrawCommands::init);
        machine.setLoopCallback(Example003DrawCommands::loop);
        machine.run();
    }

    static OglProgram program;
    static OglVertexArray vao;
    static Matrix4f model;

    public static void init() {
        String vert = """
                #version 450
                                
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
        String frag = """
                #version 450
                                
                in vec4 vs_fs_color;
                                
                layout (location = 0) out vec4 color;
                                
                void main(void)
                {
                    color = vs_fs_color;
                }
                """;

        float[] vertices = new float[]{
                -1.0f, -1.0f, 0.0f, 1.0f,
                1.0f, -1.0f, 0.0f, 1.0f,
                -1.0f, 1.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, 0.0f, 1.0f
        };

        float[] colors = new float[]{
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 1.0f,
                0.0f, 1.0f, 1.0f, 1.0f
        };

        int[] indices = new int[]{
                0, 1, 2
        };

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();


        var buffer = BufferBuilder.newInstance()
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .byteLength((vertices.length + colors.length) * 4)
                .build();
        buffer.setData(0, vertices);
        buffer.setData(4 * 4 * 4, colors);

        var eleBuffer = BufferBuilder.newInstance()
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .byteLength(indices.length * 4)
                .build();
        eleBuffer.setData(indices);

        vao = VertexArrayBuilder.newInstance()
                .addElementBuffer(new VertexArrayAccessor(eleBuffer, 0, 0, 4, DataType.U_INT, 1, false))
                .addAccessor(0,
                        new VertexArrayAccessor(buffer, 0, 0, 4 * 4, DataType.FLOAT, 4, false))
                .addAccessor(1,
                        new VertexArrayAccessor(buffer, 4 * 4 * 4, 0, 4 * 4, DataType.FLOAT, 4, false))
                .primitiveMode(PrimitiveType.TRIANGLES)
                .build();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        float aspect = machine.getDimension().y / (float) machine.getDimension().x;

        var proj = new Matrix4f().frustum(-1, 1, -aspect, aspect, 1.f, 500f);

        program.setUniform("projection_matrix", proj);
    }


    public static void loop() {
        program.use();

        model = new Matrix4f().translate(-3, 0, -5);
        program.setUniform("model_matrix", model);
        vao.drawArrays(3);

        model = new Matrix4f().translate(-1, 0, -5);
        program.setUniform("model_matrix", model);
        vao.drawElements();

        model = new Matrix4f().translate(1, 0, -5);
        program.setUniform("model_matrix", model);
        vao.drawElementsBaseVertex(3, 1);

        model = new Matrix4f().translate(3, 0, -5);
        program.setUniform("model_matrix", model);
        vao.drawArraysInstanced(0, 3, 1);
    }
}
