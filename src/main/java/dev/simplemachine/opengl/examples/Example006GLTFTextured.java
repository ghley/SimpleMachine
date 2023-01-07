package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.model.StaticMesh;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.gltf.GLBLoader;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.OglVertexArray;
import dev.simplemachine.opengl.objects.ProgramBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL45;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.glDisable;


public class Example006GLTFTextured {


    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example006GLTFTextured::init);
        machine.setLoopCallback(Example006GLTFTextured::loop);
        machine.run();
    }

    static StaticMesh mesh;

    private static void init() {
        var vertexShader = """
                #version 450 core
                                
                uniform mat4 model_matrix;
                uniform mat4 projection_matrix;
                                
                layout (location = 0) in vec3 position;
                layout (location = 2) in vec2 uv;
                                
                out vec2 vs_fs_uv;
                                
                void main(void)
                {
                    vs_fs_uv = vec2(uv.x, 31821 - uv.y);
                    gl_Position = projection_matrix * (model_matrix * vec4(position,1));
                }
                """;

        var fragmentShader = """
                #version 450 core
                                
                in vec2 vs_fs_uv;
                
                layout (binding = 0) uniform sampler2D tex;
                
                layout (location = 0) out vec4 color;
                                
                void main(void)
                {
                    color = texture2D(tex, vs_fs_uv);
                }
                """;

        var program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vertexShader)
                .attach(ShaderType.FRAGMENT_SHADER, fragmentShader)
                .build();

        program.use();



        byte[] data = null;
        try {
            data = Files.readAllBytes(Path.of("wall.glb"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        var map = GLBLoader.getInstance().load(data);

        mesh = map.get(0);
        mesh.setProgram(program);
    }

    static float t = 0;

    private static void loop() {
        t += 0.001f;

        mesh.getProgram().use();

        mesh.getTextures().entrySet().forEach((e)->{
            GL45.glBindTextureUnit(e.getKey(), e.getValue().getId());
        });


        Matrix4f model = new Matrix4f()
                .translate(new Vector3f(0, 0, -4))
                .rotate(t * 3 * (float) Math.PI, new Vector3f(0, 0.1f, 0.4f).normalize())
                .rotate(t * 2 * (float) Math.PI, new Vector3f(0.34f, 0.6f, 0).normalize());
        Matrix4f proj = new Matrix4f()
                .frustum(-1f, 1f, -1, 1, 1f, 500f);

        mesh.getProgram().setUniform("model_matrix", model);
        mesh.getProgram().setUniform("projection_matrix", proj);

        mesh.getVao().drawElements();
    }

}
