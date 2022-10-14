package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.ProgramBuilder;

public class Example003DrawCommands {

    public static void main(String[] args) {
        var machine = new SimpleMachine();
        machine.setInitCallback(Example003DrawCommands::init);
        machine.setLoopCallback(Example003DrawCommands::loop);
        machine.run();
    }

    static OglProgram program;

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

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();


    }


    public static void loop() {

    }
}
