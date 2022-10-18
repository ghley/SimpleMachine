package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.BufferBuilder;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.ProgramBuilder;

public class Example004Instancing {

    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example004Instancing::init);
        machine.setLoopCallback(Example004Instancing::loop);
        machine.run();
    }

    static OglProgram program;

    static int INSTANCE_COUNT = 200;

    public static void init() {
        var vert = """
                #version 450
                
                uniform mat4 model_matrix[4];
                uniform mat4 projection_matrix;
                
                layout (location = 0) in vec4 position;
                layout (location = 1) in vec3 normal;
                
                layout (location = 3) in vec4 instance_weights;
                layout (location = 4) in vec4 instance_color;
                
                out vec3 vs_fs_normal;
                out vec4 vs_fs_color;
                
                void main() {
                    int n;
                    mat4 m = mat4(0.0);
                    vec4 pos = position;
                    
                    vec4 weights = normalize(instance_weights);
                    for (n = 0; n < 4; n++) {
                        m += (model_matrix[n] * weights[n]);
                    }
                    
                    vs_fs_normal = normalize((m * vec4(normal, 0.0)).xyz);
                    vs_fs_color = instance_color;
                    gl_Position = projection_matrix * (m * pos);
                }
                """;

        var frag = """
                #version 450
                
                layout (location = 0) out vec4 color;
                
                in vec3 vs_fs_normal;
                in vec4 vs_fs_color;
                
                void main() {
                    color = vs_fs_color * (0.1 + abs(vs_fs_normal.z)) + vec4(0.8, 0.9, 0.7, 1.0) * pow(abs(vs_fs_normal.z), 40.0);
                }
                """;

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();

        var buffer = BufferBuilder.newInstance()
                .bufferType(BufferType.ARRAY_BUFFER)
                .flag(BufferStorageType.DYNAMIC_STORAGE);


    }

    public static void loop() {

    }


}
