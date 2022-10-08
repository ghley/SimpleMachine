package dev.simplemachine.opengl;

import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.OglShader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public final class ProgramBuilder {
    private OglProgram program;
    private Map<ShaderType, String> shaderSrc = new HashMap<>();

    private ProgramBuilder() {
    }

    public static ProgramBuilder newInstance() {
        return new ProgramBuilder();
    }

    public ProgramBuilder useProgram(OglProgram program) {
        this.program = program;
        return this;
    }

    public ProgramBuilder attach(ShaderType type, String shader) {
        shaderSrc.put(type, shader);
        return this;
    }

    public OglProgram build() {
        OglProgram oglProgram = program;
        if (program == null) {
            oglProgram = new OglProgram();
        }

        Set<OglShader> shadersToDelete = new HashSet<>();
        for (var entry : shaderSrc.entrySet()) {
            var shader = new OglShader(entry.getKey());
            if (!shader.compile(entry.getValue())) {
                Logger.getAnonymousLogger().info("Compilation failed: "+shader.getInfoLog());
                shader.delete();
            }else {
                shadersToDelete.add(shader);
                oglProgram.addShader(shader);
            }
        }

        if (!oglProgram.link()) {
            Logger.getAnonymousLogger().info("Linking failed: "+oglProgram.getInfoLog());
            shadersToDelete.forEach(OglShader::delete);
            if (program != null) {
                return program;
            }else {
                oglProgram.delete();
                return null;
            }
        }else {
            shadersToDelete.forEach(OglShader::delete);
            return oglProgram;
        }
    }
}
