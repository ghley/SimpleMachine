package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.TextureType;
import org.lwjgl.opengl.GL45;

public class OglTexture extends AbstractOglObject{

    public OglTexture(TextureType type) {
        super(GL45.glCreateTextures(type.constant));
    }
}
