package dev.simplemachine.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.ecs.AbstractSystem;
import dev.simplemachine.ecs.Component;
import dev.simplemachine.opengl.BufferBuilder;
import dev.simplemachine.opengl.ProgramBuilder;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.OglBuffer;

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicReference;

public class Example002DrawTriangle {
    public static void main(String[] args) {
        var machine = new SimpleMachine();
        machine.setInitCallback(Example002DrawTriangle::init);
        machine.setLoopCallback(Example002DrawTriangle::loop);
        machine.run();
    }

    public static void init() {
        String vert = """
                #version 330 core
                layout (location = 0) in vec3 pos;
                
                void main() {
                    gl_Position = vec4(pos.x, pos.y, pos.z,  1.0f);
                }
                """;
        String frag = """
                #version 330 core
                out vec4 color;
                
                void main() {
                    color = vec4(1.0f, 0.5f, 0.8f, 1.0f);
                }
                """;

        var program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();

        var buffer = BufferBuilder.newInstance()
                .type(BufferType.ARRAY_BUFFER)
                .size(DataType.FLOAT, 6)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();



        if (program == null) {
            System.exit(1);
        }
    }

    public static void loop() {

    }
}
