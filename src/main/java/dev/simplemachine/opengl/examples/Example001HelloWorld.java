package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;

/**
 * All examples are implementations of the red book examples (with some variation to fit our test cases)
 * These are primarily to test the OpenGL "mid"-level implementations.
 */
public class Example001HelloWorld {
    public static void main(String[] args) {
        var machine = new SimpleMachine();
        machine.setInitCallback(()->{
            System.out.println("Hello World!");
        });
        machine.run();
    }
}
