package cz.lmaky.lwjgl.game;

import cz.lmaky.lwjgl.engine.Mesh;
import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by lmaky
 */
public class Triangle extends GraphicsObject {

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

    @Override
    public void init() {

        mesh = new Mesh(TRIANGLE_VERTS, TRIANGLE_COLOR, null);
//        uiVBO[0] = GL15.glGenBuffers();
//        uiVBO[1] = GL15.glGenBuffers();
////        uiVBO[2] = glGenBuffers();
////        uiVBO[3] = glGenBuffers();
//
//        uiVAO[0] = glGenVertexArrays();
////        uiVAO[1] = glGenVertexArrays();
//
//        // Setup whole triangle
//        glBindVertexArray(uiVAO[0]);
//
//        FloatBuffer triangleVertsBuffer = BufferUtils.createFloatBuffer(TRIANGLE_VERTS.length);
//        triangleVertsBuffer.put(TRIANGLE_VERTS).flip();
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uiVBO[0]);
//        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, triangleVertsBuffer, GL15.GL_DYNAMIC_DRAW);
//        glEnableVertexAttribArray(0);
//        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
//
//        FloatBuffer triangleColorBuffer = BufferUtils.createFloatBuffer(TRIANGLE_COLOR.length);
//        triangleColorBuffer.put(TRIANGLE_COLOR).flip();
//        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uiVBO[1]);
//        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, triangleColorBuffer, GL15.GL_DYNAMIC_DRAW);
//        glEnableVertexAttribArray(1);
//        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
    }

    @Override
    public Matrix4f getModelMatrix() {
        return new Matrix4f();
    }

//    @Override
//    public void render() {
//        glBindVertexArray(uiVAO[0]);
//
////        Matrix4f model = new Matrix4f();
////        model.scale(0.5f, 0.5f, 0.5f);
////
////        glUniformMatrix4fv(uniModel, false, model.get(fb));
//
//        glDrawArrays(GL_TRIANGLES, 0, 3);
//    }
}
