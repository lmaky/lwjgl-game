package cz.lmaky.lwjgl.engine;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

/**
 * @author lukas.marek
 */
public class Texture {

    int textureId;
    int textureWidth;
    int textureHeight;

    public Texture(int textureId, int textureWidth, int textureHeight) {
        this.textureId = textureId;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void delete() {
        glDeleteTextures(textureId);
    }

    public int getTextureId() {
        return textureId;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }
}
