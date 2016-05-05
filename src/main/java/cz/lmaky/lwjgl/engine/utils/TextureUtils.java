package cz.lmaky.lwjgl.engine.utils;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * @author lukas.marek
 */
public class TextureUtils {

    public static int loadTexture(InputStream inputStream) {
        int newTextureId;
        PNGDecoder decoder;
        ByteBuffer buf;
        try {
            decoder = new PNGDecoder(inputStream);
            buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        // Create a new OpenGL texture
        newTextureId = glGenTextures();
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, newTextureId);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

        glGenerateMipmap(GL_TEXTURE_2D);

        return newTextureId;
    }

    public static int loadTexture(String name) {
        return loadTexture(Thread.currentThread().getContextClassLoader().getResourceAsStream(name));
    }
}
