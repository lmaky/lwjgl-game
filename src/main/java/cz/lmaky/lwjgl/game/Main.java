package cz.lmaky.lwjgl.game;

import java.util.ArrayList;
import java.util.List;

import cz.lmaky.lwjgl.engine.AbstractWindow;
import cz.lmaky.lwjgl.engine.Camera;
import cz.lmaky.lwjgl.engine.ShaderProgram;
import cz.lmaky.lwjgl.engine.GameFont;
import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import cz.lmaky.lwjgl.engine.objects.impl.Text;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

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

    protected ShaderProgram program;
    protected ShaderProgram hudProgram;

    private List<GraphicsObject> objects = new ArrayList<>();
    private List<GraphicsObject> hudObjects = new ArrayList<>();

    private Camera camera;

    public Main() {
        width = 1600;
        height = 900;
        title = "Game";
    }

    @Override
    protected void initContent() {
        // Set the clear color
        GL11.glClearColor(0.6f, 0.7f, 0.8f, 1.0f);
        
        //Load shaders and create shader program
        program = new ShaderProgram();
        program.createVertexShader("shader.vert");
        program.createFragmentShader("shader.frag");
        program.linkProgram();

        program.createUniform("modelMatrix");
        program.createUniform("viewMatrix");
        program.createUniform("projectionMatrix");
        program.createUniform("texture_sampler");

        // Load shaders and create shader program
        hudProgram = new ShaderProgram();
        hudProgram.createVertexShader("hud-shader.vert");
        hudProgram.createFragmentShader("hud-shader.frag");
        hudProgram.linkProgram();

        hudProgram.createUniform("projectionMatrix");
        hudProgram.createUniform("modelMatrix");
        hudProgram.createUniform("colour");
        hudProgram.createUniform("texture_sampler");

//        objects.add(new Triangle());
        objects.add(new Cube());

        String fontName = "font/kenpixel.ttf";
//        String fontName = "font/SnackerComic_PerosnalUseOnly.ttf";

//        GameFont gameFont = new GameFont(new Font(SANS_SERIF, PLAIN, 62));
        GameFont gameFont = new GameFont(fontName, 52);
        hudObjects.add(new Text(gameFont, "Test!"));

        for (GraphicsObject graphicsObject : objects) {
            graphicsObject.init();
        }

        for (GraphicsObject graphicsObject : hudObjects) {
            graphicsObject.init();
        }

        camera = new Camera(width, height);

        /* Enable blending */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearDepth(1.0);
    }

    @Override
    protected void loop(long window) {
//        double lastTime = glfwGetTime();
//        int nbFrames = 0;
        while (GLFW.glfwWindowShouldClose(window) == GLFW.GLFW_FALSE) {

//            double currentTime = glfwGetTime();
//            nbFrames++;
//            if (currentTime - lastTime >= 1.0 ) {
//                System.out.println(nbFrames);
//                nbFrames = 0;
//                lastTime = currentTime;
//            }
            // Clear the screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            program.bind();

            program.setUniform("viewMatrix", camera.getViewMatrix());
            program.setUniform("projectionMatrix", camera.getProjectionMatrix());

            program.setUniform("texture_sampler", 0);

            for (GraphicsObject graphicsObject : objects) {
                program.setUniform("modelMatrix", graphicsObject.getModelMatrix());
                graphicsObject.render();
            }

            //HUD
            hudProgram.bind();

            hudProgram.setUniform("colour", new Vector3f(0.0f, 1.0f, 0.0f));
            hudProgram.setUniform("texture_sampler", 0);

            Matrix4f projectionMatrix = (new Matrix4f()).ortho2D(0.0f , width, height, 0.0f);
            hudProgram.setUniform("projectionMatrix", projectionMatrix);

            for (GraphicsObject graphicsObject : hudObjects) {
                hudProgram.setUniform("modelMatrix", graphicsObject.getModelMatrix());
                graphicsObject.render();
            }

            // Poll the events and swap the buffers
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        // Dispose the game
        program.deleteProgram();
        hudProgram.deleteProgram();
    }
    
    @Override
    protected GLFWKeyCallback createKeyCallback() {
        return new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                    GLFW.glfwSetWindowShouldClose(window, GL11.GL_TRUE); // We will detect this in our rendering loop
                }
            }
        };
    }

    @Override
    protected GLFWCursorPosCallback createCursorPosCallback() {
        return new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
//                camera.updateMousePos(xpos, ypos);
//                glfwSetCursorPos(window, width/2, height/2);
            }
        };
    }

    public static void main(String[] args) {
        (new Main()).run();
    }
}
