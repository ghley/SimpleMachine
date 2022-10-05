package dev.simplemachine.examples;

import dev.simplemachine.SimpleMachine;

public class Example001HelloWorld {
    public static void main(String[] args) {
        var machine = new SimpleMachine();
        machine.setInitCallback(()->{
            System.out.println("Hello World!");
        });
        machine.run();
    }
}
