package dev.simplemachine.opengl.objects;

import org.joml.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL45;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class OglProgram extends AbstractOglObject {
    private Map<String, Integer> locations = new HashMap<>();

    public OglProgram() {
        super(GL20.glCreateProgram());
    }

    public void use() {
        GL20.glUseProgram(id);
    }

    public void delete() {
        GL20.glDeleteProgram(id);
    }

    public void addShader(OglShader shader) {
        GL20.glAttachShader(id, shader.id);
    }

    public void removeShader(OglShader shader) {
        GL20.glDetachShader(id, shader.id);
    }

    public boolean link() {
        GL20.glLinkProgram(id);
        return isLinkingSuccessful();
    }

    private boolean isLinkingSuccessful() {
        return GL20.glGetProgrami(id, GL20.GL_LINK_STATUS) == GL11.GL_TRUE;
    }

    public String getInfoLog() {
        return GL20.glGetProgramInfoLog(id);
    }

    int getLocation(String name) {
        if (!locations.containsKey(name)) {
            int location = GL20.glGetUniformLocation(id, name);
            locations.put(name, location);
            if (location == -1) {
                Logger.getAnonymousLogger().info(name+" location not found.");
            }
        }
        return locations.get(name);
    }

    public void setUniform(String name, float scalar) {
        GL20.glUniform1f(getLocation(name), scalar);
    }

    public void setUniform(String name, Vector3f vec3) {
        GL20.glUniform3f(getLocation(name), vec3.x, vec3.y, vec3.z);
    }

    public void setUniform(String name, Vector4f vec4) {
        GL20.glUniform4f(getLocation(name), vec4.x, vec4.y, vec4.z, vec4.w);
    }

    public void setUniform(String name, Vector2f vec2) {
        GL20.glUniform2f(getLocation(name), vec2.x, vec2.y);
    }

    public void setUniform(String name, Matrix3f matrix) {
        GL20.glUniformMatrix3fv(getLocation(name), false, matrix.get(new float[9])); // FIXME GC might not like this
    }

    public void setUniform(String name, Matrix4f matrix) {
        GL20.glUniformMatrix4fv(getLocation(name), false, matrix.get(new float[16])); // FIXME GC might not like this
    }

    public void setUniform(String name, Matrix4f[] matrices) {
        float[] data = new float[matrices.length*16];
        for (int q = 0; q < matrices.length; q++) {
            matrices[q].get(data, q * 16);
        }
        GL20.glUniformMatrix4fv(getLocation(name), false, data);
    }

    public void setUniformBlockBinding(String location, int uniformBufferIndex) {
        int locationIndex = GL45.glGetUniformBlockIndex(id, location);
        GL45.glUniformBlockBinding(id, locationIndex, uniformBufferIndex);
    }
}
