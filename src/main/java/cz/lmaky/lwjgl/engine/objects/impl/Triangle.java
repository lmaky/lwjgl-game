package cz.lmaky.lwjgl.engine.objects.impl;

import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by lmaky
 */
public class Triangle implements GraphicsObject {

    int uiVAO[] = new int[1];
    int uiVBO[] = new int[2];

    private FloatBuffer fb = BufferUtils.createFloatBuffer(16);

    static final float[] TRIANGLE_VERTS = {
            -1.2f, -0.8f, 0.0f,
            1.2f, -0.8f, 0.0f,
            0.0f, 0.8f, 0.0f,
//
//        1.0f, -1.0f, -1.0f,
//        1.0f, -1.0f, 1.0f,
//        0.0f, 1.0f, 0.0f,
//
//        -1.0f, -1.0f, 1.0f,
//        1.0f, -1.0f, 1.0f,
//        0.0f, 1.0f, 0.0f,
//
//        -1.0f, -1.0f, -1.0f,
//        -1.0f, -1.0f, 1.0f,
//        0.0f, 1.0f, 0.0f
    };
    static final float[] TRIANGLE_COLOR = {
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

//        0.0f, 1.0f, 0.0f,
//        0.0f, 1.0f, 0.0f,
//        0.0f, 1.0f, 0.0f,
//
//        0.0f, 0.0f, 1.0f,
//        0.0f, 0.0f, 1.0f,
//        0.0f, 0.0f, 1.0f,
//
//        1.0f, 1.0f, 0.0f,
//        1.0f, 1.0f, 0.0f,
//        1.0f, 1.0f, 0.0f
    };

    public void init() {
        uiVBO[0] = glGenBuffers();
        uiVBO[1] = glGenBuffers();
//        uiVBO[2] = glGenBuffers();
//        uiVBO[3] = glGenBuffers();

        uiVAO[0] = glGenVertexArrays();
//        uiVAO[1] = glGenVertexArrays();

        // Setup whole triangle
        glBindVertexArray(uiVAO[0]);

        FloatBuffer triangleVertsBuffer = BufferUtils.createFloatBuffer(TRIANGLE_VERTS.length);
        triangleVertsBuffer.put(TRIANGLE_VERTS).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[0]);
        glBufferData(GL_ARRAY_BUFFER, triangleVertsBuffer, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        FloatBuffer triangleColorBuffer = BufferUtils.createFloatBuffer(TRIANGLE_COLOR.length);
        triangleColorBuffer.put(TRIANGLE_COLOR).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[1]);
        glBufferData(GL_ARRAY_BUFFER, triangleColorBuffer, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
    }

    public void render(int uniModel) {
        glBindVertexArray(uiVAO[0]);

        Matrix4f model = new Matrix4f();
        model.scale(0.5f, 0.5f, 0.5f);

        glUniformMatrix4fv(uniModel, false, model.get(fb));

        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
