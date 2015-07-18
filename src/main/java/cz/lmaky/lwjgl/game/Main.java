package cz.lmaky.lwjgl.game;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author Lmaky
 */
public class Main extends AbstractWindow {
    
    int uiVBO[] = new int[4];
    int uiVAO[] = new int[2];
    
    static final float[] TRIANGLE_VERTS = {
        -0.4f, 0.1f, 0.0f,
        0.4f, 0.1f, 0.0f,
        0.0f, 0.7f, 0.0f
    };
    static final float[] TRIANGLE_COLOR = {
        1.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 1.0f
    };

    static final float[] QUAD_VERTS = {
        -0.2f, -0.1f, 0.0f,
        -0.2f, -0.6f, 0.0f,
        0.2f, -0.1f, 0.0f,
        0.2f, -0.6f, 0.0f
    };
    
    static final float[] QUAD_COLOR = {
        1.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 1.0f,
        1.0f, 1.0f, 0.0f
    };
    
    public Main() {
        width = 800;
        height = 600;
        title = "Game";
    }

    @Override
    protected void initContent() {
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        // Load shaders and create shader program
        Shader vertexShader = new Shader("/shader.vert", GL_VERTEX_SHADER);
        Shader fragmentShader = new Shader("/shader.frag", GL_FRAGMENT_SHADER);
        vertexShader.loadShader();
        fragmentShader.loadShader();
        
        program = new ShaderProgram();
        program.createProgram();
        program.addShader(vertexShader);
        program.addShader(fragmentShader);
        program.linkProgram();

        uiVBO[0] = glGenBuffers();
        uiVBO[1] = glGenBuffers();
        uiVBO[2] = glGenBuffers();
        uiVBO[3] = glGenBuffers();
        
        uiVAO[0] = glGenVertexArrays();
        uiVAO[1] = glGenVertexArrays();
        
        // Setup whole triangle
        glBindVertexArray(uiVAO[0]);

        FloatBuffer triangleVertsBuffer = BufferUtils.createFloatBuffer(TRIANGLE_VERTS.length);
        triangleVertsBuffer.put(TRIANGLE_VERTS).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[0]);
        glBufferData(GL_ARRAY_BUFFER, triangleVertsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        FloatBuffer triangleColorBuffer = BufferUtils.createFloatBuffer(TRIANGLE_COLOR.length);
        triangleColorBuffer.put(TRIANGLE_COLOR).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[1]); 
        glBufferData(GL_ARRAY_BUFFER, triangleColorBuffer, GL_STATIC_DRAW); 
        glEnableVertexAttribArray(1); 
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0); 
        
        // Setup whole quad
        glBindVertexArray(uiVAO[1]);
        
        FloatBuffer quadVertsBuffer = BufferUtils.createFloatBuffer(QUAD_VERTS.length);
        quadVertsBuffer.put(QUAD_VERTS).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[2]); 
        glBufferData(GL_ARRAY_BUFFER, quadVertsBuffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0); 
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); 
        
        
        FloatBuffer quadColorBuffer = BufferUtils.createFloatBuffer(QUAD_COLOR.length);
        quadColorBuffer.put(QUAD_COLOR).flip();
        glBindBuffer(GL_ARRAY_BUFFER, uiVBO[3]); 
        glBufferData(GL_ARRAY_BUFFER, quadColorBuffer, GL_STATIC_DRAW); 
        glEnableVertexAttribArray(1); 
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
    }
    
    @Override
    protected void render() {
        // Clear the screen
        glClear(GL_COLOR_BUFFER_BIT);
        // Use our program
        program.bindProgram();
        
        glBindVertexArray(uiVAO[0]);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        glBindVertexArray(uiVAO[1]);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
        // Un-bind our program
        ShaderProgram.unbind();
    }
    
    @Override
    protected GLFWKeyCallback createKeyCallback() {
        return new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
               }
            }
        };
    }
    
    public static void main(String[] args) {
        new Main().run();
    }

    
}
