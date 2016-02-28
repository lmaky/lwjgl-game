package cz.lmaky.lwjgl.game;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import cz.lmaky.lwjgl.game.objects.GraphicsObject;
import cz.lmaky.lwjgl.game.objects.Triangle;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author Lmaky
 */
public class Main extends AbstractWindow {

    // Mouse positions
    private int mouseOldX = 0;
    private int mouseOldY = 0;
    private int mouseX = 90;
    private int mouseY = 0;

    private List<GraphicsObject> objects = new ArrayList<>();

    private Camera camera;

    private FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    
    public Main() {
        width = 1600;
        height = 900;
        title = "Game";
    }

    @Override
    protected void initContent() {
        // Set the clear color
        glClearColor(0.6f, 0.7f, 0.8f, 1.0f);
        
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
        program.bindProgram();

        objects.add(new Triangle());

        for (GraphicsObject graphicsObject : objects) {
            graphicsObject.init();
        }

        camera = new Camera(width, height);


        /* Enable blending */
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glEnable(GL_DEPTH_TEST);
        glClearDepth(1.0);
    }

    @Override
    protected void loop(long window) {
        while (glfwWindowShouldClose(window) == GL_FALSE) {
            // Clear the screen
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            int uniModel = glGetUniformLocation(program.getProgramID(), "modelMatrix");
            int uniView = glGetUniformLocation(program.getProgramID(), "viewMatrix");
            int uniProjection = glGetUniformLocation(program.getProgramID(), "projectionMatrix");

            Matrix4f view = camera.getViewMatrix();
            glUniformMatrix4fv(uniView, false, view.get(fb));

            Matrix4f projection = camera.getProjectionMatrix();
            glUniformMatrix4fv(uniProjection, false, projection.get(fb));

            for (GraphicsObject graphicsObject : objects) {
                graphicsObject.render(uniModel);
            }

            glfwSwapBuffers(window);
            // Poll the events and swap the buffers
            glfwPollEvents();
        }
        // Dispose the game
        program.deleteProgram();
    }
    
    @Override
    protected GLFWKeyCallback createKeyCallback() {
        return new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                    GLFW.glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
                }
            }
        };
    }

    @Override
    protected GLFWCursorPosCallback createCursorPosCallback() {
        return new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                camera.updateMousePos(xpos, ypos);
                glfwSetCursorPos(window, width/2, height/2);
            }
        };
    }

    public static void main(String[] args) {
        (new Main()).run();
    }
}
