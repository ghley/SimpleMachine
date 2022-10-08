package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;

public enum TextureType {
    TEXTURE_1D(GL20.GL_TEXTURE_1D),
    TEXTURE_2D(GL20.GL_TEXTURE_2D),
    TEXTURE_3D(GL20.GL_TEXTURE_3D),
    TEXTURE_1D_ARRAY(GL30.GL_TEXTURE_1D_ARRAY),
    TEXTURE_2D_ARRAY(GL30.GL_TEXTURE_2D_ARRAY),
    TEXTURE_RECTANGLE(GL31.GL_TEXTURE_RECTANGLE),
    TEXTURE_CUBE_MAP(GL30.GL_TEXTURE_CUBE_MAP),
    TEXTURE_CUBE_MAP_ARRAY(GL40.GL_TEXTURE_CUBE_MAP_ARRAY),
    TEXTURE_BUFFER(GL31.GL_TEXTURE_BUFFER),
    TEXTURE_2D_MULTISAMPLE(GL40.GL_TEXTURE_2D_MULTISAMPLE),
    TEXTURE_2D_MULTISAMPLE_ARRAY(GL40.GL_TEXTURE_2D_MULTISAMPLE_ARRAY);

    public int constant;

    TextureType(int constant) {
        this.constant = constant;
    }
}
