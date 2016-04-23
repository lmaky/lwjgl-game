package cz.lmaky.lwjgl.engine.objects.impl;

import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import cz.lmaky.lwjgl.engine.utils.TextureUtils;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * @author lukas.marek
 */
public class Cube implements GraphicsObject {


    int uiVAO[] = new int[1];
    int uiVBO[] = new int[3];
    int textureId;
    float x = 0.0f;
    float y = 0.0f;
    float z = 0.0f;
//    float x = 0.5f;
//    float y = -1.0f;
//    float z = 0.0f;

    static final float[] VERTICLES = {
            //V0
            -0.5f, 0.5f, 0.5f,
            //V1
            -0.5f, -0.5f, 0.5f,
            //V2
            0.5f, -0.5f, 0.5f,
            //V3
            0.5f, 0.5f, 0.5f,

            //V4
            -0.5f, 0.5f, -0.5f,
            //V5
            -0.5f, -0.5f, -0.5f,
            //V6
            0.5f, -0.5f, -0.5f,
            //V7
            0.5f, 0.5f, -0.5f,


            //V3 - 8
            0.5f, 0.5f, 0.5f,
            //V2 - 9
            0.5f, -0.5f, 0.5f,
            //V6 - 10
            0.5f, -0.5f, -0.5f,
            //V7 - 11
            0.5f, 0.5f, -0.5f,

            //V4 - 12
            -0.5f, 0.5f, -0.5f,
            //V5 - 13
            -0.5f, -0.5f, -0.5f,
            //V1 - 14
            -0.5f, -0.5f, 0.5f,
            //V0 - 15
            -0.5f, 0.5f, 0.5f,

            //V4 - 16
            -0.5f, 0.5f, -0.5f,
            //V0 - 17
            -0.5f, 0.5f, 0.5f,
            //V3 - 18
            0.5f, 0.5f, 0.5f,
            //V7 - 19
            0.5f, 0.5f, -0.5f,

            //V1 - 20
            -0.5f, -0.5f, 0.5f,
            //V5 - 21
            -0.5f, -0.5f, -0.5f,
            //V6 - 22
            0.5f, -0.5f, -0.5f,
            //V2 - 23
            0.5f, -0.5f, 0.5f,
    };

    float[] textCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,

            0.0f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            0.5f, 0.5f,

            0.5f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
            1.0f, 0.0f,
    };

    static final int[] indices = {
            //front
            0, 1, 3,
            3, 1, 2,

            //top
            16, 17, 19,
            19, 17, 18,

            //right
            8, 9, 11,
            11, 9, 10,

            //left
            12, 13, 15,
            15, 13, 14,

            //back
            7, 6, 4,
            4, 6, 5,

            //bottom
            20, 21, 23,
            23, 21, 22,
    };

    @Override
    public void init() {
        uiVAO[0] = glGenVertexArrays();

        glBindVertexArray(uiVAO[0]);

        uiVBO[0] = glGenBuffers();
        FloatBuffer triangleVertsBuffer = BufferUtils.createFloatBuffer(VERTICLES.length);
        triangleVertsBuffer.put(VERTICLES).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[0]);
        glBufferData(GL_ARRAY_BUFFER, triangleVertsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Colour VBO
//        uiVBO[1] = glGenBuffers();
//        FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(colours.length);
//        colourBuffer.put(colours).flip();
//        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[1]);
//        glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
//        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        //Texture VBO
        uiVBO[1] = glGenBuffers();
        FloatBuffer textCoordsBuffer = BufferUtils.createFloatBuffer(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[1]);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        // Index VBO
        uiVBO[2] = glGenBuffers();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, uiVBO[2]);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        textureId = loadTextureFromResource("image/grassblock.png");
    }

    private int loadTextureFromResource(String name) {
        //Texture.class.getResourceAsStream(fileName)
        return TextureUtils.loadTexture(Thread.currentThread().getContextClassLoader().getResourceAsStream(name));
    }

    @Override
    public void render(int uniModel) {
        glBindVertexArray(uiVAO[0]);

        // Activate first texture unit
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        Matrix4f model = new Matrix4f();
        model.translation(0.0f, 0.0f, -3.0f);
        x += 0.02f;
        y += 0.02f;
        z += 0.02f;
        model.rotateXYZ(x, y, z);
//        model.scale(0.1f, 0.1f, 0.1f);
//        model.scale(10.0f);

        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        glUniformMatrix4fv(uniModel, false, model.get(fb));

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }
}
