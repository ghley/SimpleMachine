package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

public enum ShaderType {
    VERTEX_SHADER(GL20.GL_VERTEX_SHADER),
    TESSELATION_CONTROL_SHADER(GL40.GL_TESS_CONTROL_SHADER),
    TESSELATION_EVALUATION_SHADER(GL40.GL_TESS_EVALUATION_SHADER),
    GEOMETRY_SHADER(GL32.GL_GEOMETRY_SHADER),
    FRAGMENT_SHADER(GL20.GL_FRAGMENT_SHADER),
    COMPUTE_SHADER(GL43.GL_COMPUTE_SHADER);

    public int constant;
    ShaderType(int constant) {
        this.constant = constant;
    }
}
