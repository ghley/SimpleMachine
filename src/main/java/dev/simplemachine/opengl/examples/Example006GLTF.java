package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.gltf.GLBLoader;
import dev.simplemachine.opengl.objects.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.nio.file.Files;
import java.nio.file.Path;


public class Example006GLTF {


    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example006GLTF::init);
        machine.setLoopCallback(Example006GLTF::loop);
        machine.run();
    }

    static OglVertexArray vao;
    static OglProgram program;

    private static void init() {
        var vertexShader = """
                #version 450 core
                                
                uniform mat4 model_matrix;
                uniform mat4 projection_matrix;
                                
                layout (location = 0) in vec3 position;
                layout (location = 3) in vec4 color;
                                
                out vec4 vs_fs_color;
                                
                void main(void)
                {
                    vs_fs_color = color;
                    gl_Position = projection_matrix * (model_matrix * vec4(position,1));
                }
                """;

        var fragmentShader = """
                #version 450 core
                                
                in vec4 vs_fs_color;
                                
                layout (location = 0) out vec4 color;
                                
                void main(void)
                {
                    color = vec4(1,1,vs_fs_color.x,1);
                }
                """;

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vertexShader)
                .attach(ShaderType.FRAGMENT_SHADER, fragmentShader)
                .build();

        program.use();

        byte[] data = null;
        try {
            data = Files.readAllBytes(Path.of("cube.glb"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        var map = GLBLoader.getInstance().load(data);

        vao = map.values().iterator().next().getVao();

        vao.bind();
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
