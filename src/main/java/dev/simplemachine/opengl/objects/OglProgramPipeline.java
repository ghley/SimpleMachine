package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL44;

public class OglProgramPipeline extends AbstractOglObject{

    public OglProgramPipeline() {
        super(GL44.glGenProgramPipelines());
    }
}
