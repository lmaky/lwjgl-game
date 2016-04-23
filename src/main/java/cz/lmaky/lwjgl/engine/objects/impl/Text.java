package cz.lmaky.lwjgl.engine.objects.impl;

import cz.lmaky.lwjgl.engine.dto.Glyph;
import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import cz.lmaky.lwjgl.engine.GameFont;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author lukas.marek
 */
public class Text implements GraphicsObject {

    private int uiVAO;
    private int uiVBO[] = new int[4];

    private int numberOfIndices;

    private final String text;
    private final GameFont gameFont;

    public Text(GameFont gameFont, String text) {
        this.text = text;
        this.gameFont = gameFont;
    }

    @Override
    public void init() {
        int len = text.length();

        float[] gTextCoords = new float[8 * len];
        float[] gVerticles = new float[12 * len];
        int[] gIndices = new int[6 * len];

        float textureWidth = gameFont.getFontTextureWidth();

        int tcPos = 0;
        int indcPos = 0;
        int vertPos = 0;

        float position = 0.0f;

        for (int i=0; i<len; i++) {
            Glyph glyph = gameFont.getGlyphs().get(text.charAt(i));

            gTextCoords[tcPos++] = glyph.getX() / textureWidth;
            gTextCoords[tcPos++] = 0.0f;
            gTextCoords[tcPos++] = glyph.getX() / textureWidth;
            gTextCoords[tcPos++] = 1.0f;
            gTextCoords[tcPos++] = (glyph.getX() + glyph.getWidth()) / textureWidth;
            gTextCoords[tcPos++] = 1.0f;
            gTextCoords[tcPos++] = (glyph.getX() + glyph.getWidth()) / textureWidth;
            gTextCoords[tcPos++] = 0.0f;

            gIndices[indcPos++] = i*4 + 0;
            gIndices[indcPos++] = i*4 + 1;
            gIndices[indcPos++] = i*4 + 3;
            gIndices[indcPos++] = i*4 + 3;
            gIndices[indcPos++] = i*4 + 1;
            gIndices[indcPos++] = i*4 + 2;

            gVerticles[vertPos++] = 0.0f + position;
            gVerticles[vertPos++] = 0.0f;
            gVerticles[vertPos++] = 0.0f;
            gVerticles[vertPos++] = 0.0f + position;
            gVerticles[vertPos++] = glyph.getHeight();
            gVerticles[vertPos++] = 0.0f;
            gVerticles[vertPos++] = glyph.getWidth() + position;
            gVerticles[vertPos++] = glyph.getHeight();
            gVerticles[vertPos++] = 0.0f;
            gVerticles[vertPos++] = glyph.getWidth() + position;
            gVerticles[vertPos++] = 0.0f;
            gVerticles[vertPos++] = 0.0f;
            position += glyph.getWidth();
        }

        uiVAO = glGenVertexArrays();

        glBindVertexArray(uiVAO);

        uiVBO[0] = glGenBuffers();

        float[] verticles = gVerticles;
        FloatBuffer triangleVertsBuffer = BufferUtils.createFloatBuffer(verticles.length);
        triangleVertsBuffer.put(verticles).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[0]);
        glBufferData(GL_ARRAY_BUFFER, triangleVertsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        //Texture VBO
        uiVBO[2] = glGenBuffers();
        float[] textCoords = gTextCoords;
        FloatBuffer textCoordsBuffer = BufferUtils.createFloatBuffer(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[2]);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        // Index VBO
        uiVBO[3] = glGenBuffers();
        int[] indices = gIndices;
        numberOfIndices = indices.length;
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, uiVBO[3]);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
    }

    @Override
    public void render(int uniModel) {
        glBindVertexArray(uiVAO);

        // Activate first texture unit
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, gameFont.getFontTextureId());

//        Matrix4f model = new Matrix4f();
//        model.translation(0.0f, 0.0f, -3.0f);
//
//        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
//
//        glUniformMatrix4fv(uniModel, false, model.get(fb));

        glEnableVertexAttribArray(0);
//        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, numberOfIndices, GL_UNSIGNED_INT, 0);

        glDisable(GL_TEXTURE_2D);
    }
}
