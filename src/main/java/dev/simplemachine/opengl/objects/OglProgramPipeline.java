package dev.simplemachine.opengl.objects;

import org.lwjgl.opengl.GL45;

public class OglProgramPipeline extends AbstractOglObject{

    public OglProgramPipeline() {
        super(GL45.glCreateProgramPipelines());
    }
}
