package dev.simplemachine.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.ecs.AbstractSystem;
import dev.simplemachine.ecs.Component;
import dev.simplemachine.opengl.glenum.BufferType;
import dev.simplemachine.opengl.objects.OglBuffer;

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicReference;

public class Example002DrawTriangle {
    public static void main(String[] args) {
        var machine = new SimpleMachine();

        AtomicReference<OglBuffer> vao = new AtomicReference<>();

        machine.setInitCallback(()->{
            vao.set(new OglBuffer(BufferType.ARRAY_BUFFER));
        });
        machine.setLoopCallback(()->{

        });
        machine.run();
    }
}
