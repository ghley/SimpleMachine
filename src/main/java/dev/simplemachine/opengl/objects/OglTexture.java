package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.TextureType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class OglTexture extends AbstractOglObject{

    private TextureType type;

    public OglTexture(TextureType type) {
        super(GL45.glCreateTextures(type.constant));
        this.type = type;
    }

    public void load(BufferedImage image) {
        var buffer = imageToBuffer(image);
        GL45.glTextureParameteri(id, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GL45.glTextureParameteri(id, GL_TEXTURE_WRAP_T, GL_REPEAT);
        GL45.glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GL45.glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        GL45.glTextureStorage2D(id, 1, GL11.GL_RGBA8, image.getWidth(), image.getHeight());
        GL45.glTextureSubImage2D(id, 0, 0, 0,
                image.getWidth(), image.getHeight(), GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        GL45.glGenerateTextureMipmap(id);
    }

    private ByteBuffer imageToBuffer(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);
        for (int h = image.getHeight()-1; h >= 0; h--) {
            for (int w = 0; w < image.getWidth(); w++) {
                int pixel = pixels[h * image.getWidth() + w];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();
        return buffer;
    }

    public void bind() {
        glBindTexture(type.constant, id);
    }
}
