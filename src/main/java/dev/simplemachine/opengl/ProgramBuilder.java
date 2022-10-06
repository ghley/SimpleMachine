package dev.simplemachine.opengl;

import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.OglShader;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ProgramBuilder {

    private OglProgram oglProgram;

    private Set<OglShader> shaders = new HashSet<>();

    private ProgramBuilder() {
    }

    public static ProgramBuilder newInstance() {
        return new ProgramBuilder();
    }

    public ProgramBuilder useProgram(OglProgram oglProgram) {
        this.oglProgram = oglProgram;
        return this;
    }

    public ProgramBuilder newProgram() {
        return useProgram(new OglProgram());
    }

    public ProgramBuilder attach(ShaderType type, String shader) {
        var obj = new OglShader(type);
        if (obj.compile(shader)) {
            oglProgram.addShader(obj);
            shaders.add(obj);
        }else {
            Logger.getGlobal()
                    .severe("Failed to compile: "+shader+"\n\nWith error: "+obj.getInfoLog());
            obj.delete();
        }
        return this;
    }

    public ProgramBuilder link() {
        if (oglProgram.link()) {

        }
        return this;
    }

    public Program build() {
        var program = new Program();
        program.setOglProgram(oglProgram);
        return program;
    }
}
