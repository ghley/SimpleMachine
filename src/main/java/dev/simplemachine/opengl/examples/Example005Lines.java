package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Example005Lines {

    public static void main(String[] args) {
        var machine = new SimpleMachine();
        new Example005Lines(machine);
        machine.run();
    }

    private SimpleMachine machine;

    public Example005Lines(SimpleMachine machine) {
        this.machine = machine;
        machine.setInitCallback(this::init);
        machine.setLoopCallback(this::loop);
    }

    OglProgram program;
    OglVertexArray vao;

    public void init() {
        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, """
                        #version 450 core
                                                
                        layout (location = 0) in vec3 aPos;
                                                
                        uniform mat4 model;
                        uniform mat4 view;
                        uniform mat4 projection;
                        
                        out vec3 worldPosition;
                                                
                        void main()
                        {
                            worldPosition = vec4(model * vec4(aPos, 1.0)).xyz;
                            gl_Position = projection * view * model * vec4(aPos, 1.0);
                        }
                        """)
                .attach(ShaderType.FRAGMENT_SHADER, """
                        #version 450 core
                        out vec4 FragColor;
                                                
                        uniform vec4 color;
                        
                        in vec3 worldPosition;
                                                
                        void main()
                        {
                            if (int(worldPosition.x * 10) % 2 == 0) {
                                discard;
                            }
                            FragColor = color;
                        }
                        """).build();


        float[] line = new float[]{
                0, 0, 0,
                1, 1, 1,
                0, 0, 1,
                0, 1, 0,
                1, 0, 0,
                1, 1, 0,
                0, 1, 1
        };

        var buffer = BufferBuilder.newInstance()
                .byteLength(line.length * 4)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();
        buffer.setData(line);

        vao = VertexArrayBuilder.newInstance()
                .primitiveMode(PrimitiveType.LINE_STRIP)
                .addAccessor(0,
                        new VertexArrayAccessor(buffer, 0, 0, 4 * 3, DataType.FLOAT, 3, false))
                .build();
    }

    float t = 0.0f;

    public void loop() {
        t += 0.01f;
        program.use();
        program.setUniform("color",new Vector4f(1, 1, 1, 1));
        program.setUniform("model", new Matrix4f().identity());
        program.setUniform("view", new Matrix4f().lookAt(new Vector3f(-2 * (float)Math.sin(t), 1, -2 * (float)Math.cos(t)), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0, 1, 0)));
        program.setUniform("projection", new Matrix4f().perspective((float)Math.toRadians(80), 1, 0.01f, 1000f));

//        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glLineWidth(3.f);

        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        vao.bind();
        vao.drawArrays(7);
    }


}
