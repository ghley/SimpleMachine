package dev.simplemachine.gltf;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.gltf.GLBLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class GLBLoaderTest {

    @Test
    public void test() {
        SimpleMachine machine = new SimpleMachine();
        machine.setInitCallback(this::load);
        machine.setLoopCallback(this::draw);
        machine.run();
    }


    public void load() {
        byte[] data = null;
        try {
            data = Files.readAllBytes(Path.of("test.glb"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw() {

    }

}