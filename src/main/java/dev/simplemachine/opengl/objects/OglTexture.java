package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.TextureType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;

public class OglTexture extends AbstractOglObject{

    private TextureType type;

    public OglTexture(TextureType type) {
        super(GL45.glCreateTextures(type.constant));
        this.type = type;
    }

    public void load(BufferedImage image) {
        GL45.glTextureStorage2D(id, 2, GL11.GL_RGBA16, image.getWidth(), image.getHeight());
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        GL45.glTextureSubImage2D(id, 0, 0, 0, image.getWidth(), image.getHeight(), GL_RGBA, GL_INT, pixels);
        GL45.glGenerateTextureMipmap(id);
    }

    public void bind() {
        glBindTexture(type.constant, id);
    }
}
