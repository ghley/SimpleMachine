package dev.simplemachine.model;

import dev.simplemachine.opengl.objects.OglProgram;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

/**
 * placeholder
 */
public class ProgramUniforms {
    private Map<String, Vector2f> vec2f = new HashMap<>();
    private Map<String, Vector3f> vec3f = new HashMap<>();
    private Map<String, Matrix4f> mat4f = new HashMap<>();

    public ProgramUniforms() {

    }

    public void apply(OglProgram program) {
        vec2f.entrySet().forEach(
                e->program.setUniform(e.getKey(), e.getValue()));
        vec3f.entrySet().forEach(
                e->program.setUniform(e.getKey(), e.getValue()));
        mat4f.entrySet().forEach(
                e->program.setUniform(e.getKey(), e.getValue()));
    }


}
