package cz.lmaky.lwjgl.game;


import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import cz.lmaky.lwjgl.engine.AbstractWindow;
import cz.lmaky.lwjgl.engine.Camera;
import cz.lmaky.lwjgl.engine.Shader;
import cz.lmaky.lwjgl.engine.ShaderProgram;
import cz.lmaky.lwjgl.engine.GameFont;
import cz.lmaky.lwjgl.engine.objects.GraphicsObject;
import cz.lmaky.lwjgl.engine.objects.impl.Text;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import static java.awt.Font.PLAIN;
import static java.awt.Font.SANS_SERIF;
import static java.awt.Font.TRUETYPE_FONT;
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

//    protected ShaderProgram program;

    protected ShaderProgram hudProgram;

//    private List<GraphicsObject> objects = new ArrayList<>();
    private List<GraphicsObject> hudObjects = new ArrayList<>();

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
//        Shader vertexShader = new Shader("shader.vert", GL_VERTEX_SHADER);
//        Shader fragmentShader = new Shader("shader.frag", GL_FRAGMENT_SHADER);
//        vertexShader.loadShader();
//        fragmentShader.loadShader();
//
//        program = new ShaderProgram();
//        program.createProgram();
//        program.addShader(vertexShader);
//        program.addShader(fragmentShader);
//        program.linkProgram();
//        program.bindProgram();

        // Load shaders and create shader program
        Shader hudVertexShader = new Shader("hud-shader.vert", GL_VERTEX_SHADER);
        Shader hudFragmentShader = new Shader("hud-shader.frag", GL_FRAGMENT_SHADER);
        hudVertexShader.loadShader();
        hudFragmentShader.loadShader();

        hudProgram = new ShaderProgram();
        hudProgram.createProgram();
        hudProgram.addShader(hudVertexShader);
        hudProgram.addShader(hudFragmentShader);
        hudProgram.linkProgram();
        hudProgram.bindProgram();

//        objects.add(new Triangle());
//        objects.add(new Cube());

        GameFont gameFont = null;
//        gameFont = new GameFont(new Font(SANS_SERIF, PLAIN, 62));
        try {
            String fontName = "font/kenpixel.ttf";
//            String fontName = "font/SnackerComic_PerosnalUseOnly.ttf";
            gameFont = new GameFont(Font.createFont(TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream(fontName)).deriveFont(52.0f));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        hudObjects.add(new Text(gameFont, "Test!"));

//        for (GraphicsObject graphicsObject : objects) {
//            graphicsObject.init();
//        }

        for (GraphicsObject graphicsObject : hudObjects) {
            graphicsObject.init();
        }

//        camera = new Camera(width, height);


        /* Enable blending */
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glEnable(GL_DEPTH_TEST);
        glClearDepth(1.0);
    }

    @Override
    protected void loop(long window) {
//        double lastTime = glfwGetTime();
//        int nbFrames = 0;
        while (glfwWindowShouldClose(window) == GL_FALSE) {

//            double currentTime = glfwGetTime();
//            nbFrames++;
//            if (currentTime - lastTime >= 1.0 ) {
//                System.out.println(nbFrames);
//                nbFrames = 0;
//                lastTime = currentTime;
//            }
            // Clear the screen
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//            int uniModel = glGetUniformLocation(program.getProgramID(), "modelMatrix");
//            int uniView = glGetUniformLocation(program.getProgramID(), "viewMatrix");
//            int uniProjection = glGetUniformLocation(program.getProgramID(), "projectionMatrix");
//
//            Matrix4f view = camera.getViewMatrix();
//            glUniformMatrix4fv(uniView, false, view.get(fb));
//
//            Matrix4f projection = camera.getProjectionMatrix();
//            glUniformMatrix4fv(uniProjection, false, projection.get(fb));
//
//
//            int textureSampler = glGetUniformLocation(program.getProgramID(), "texture_sampler");
//            glUniform1i(textureSampler, 0);
//
//
//            for (GraphicsObject graphicsObject : objects) {
//                graphicsObject.render(uniModel);
//            }


            //hud


            /////////////////
//            Matrix4f ortho = transformation.getOrthoProjectionMatrix(0, window.getWidth(), window.getHeight(), 0);
//            for (GameItem gameItem : hud.getGameItems()) {
//                Mesh mesh = gameItem.getMesh();
//                // Set ortohtaphic and model matrix for this HUD item
//                Matrix4f projModelMatrix = transformation.getOrtoProjModelMatrix(gameItem, ortho);
//                hudShaderProgram.setUniform("projModelMatrix", projModelMatrix);
//                hudShaderProgram.setUniform("colour", gameItem.getMesh().getMaterial().getColour());
//
//                // Render the mesh for this HUD item
//                mesh.render();
//            }
            /////////////////

            int projModel = glGetUniformLocation(hudProgram.getProgramID(), "projModelMatrix");

            Matrix4f projModelMatrix = new Matrix4f();
//            projModelMatrix = projModelMatrix.ortho2D(-10, 10, -10, 10);
//            projModelMatrix = projModelMatrix.ortho2D(0.0f , width, height, 0.0f);
            projModelMatrix = projModelMatrix.ortho2D(0.0f , width, height, 0.0f);
//            projModelMatrix = projModelMatrix.ortdho2D(10, 10, 10, 10);
//            projModelMatrix = projModelMatrix.ortho(10, 10, 10, 10, -100, 100);
//            projModelMatrix.perspective(45f, (float)width/height, 0.1f, 100f);
            glUniformMatrix4fv(projModel, false, projModelMatrix.get(fb));

            int color = glGetUniformLocation(hudProgram.getProgramID(), "colour");
            glUniform3f(color, 0.0f, 1.0f, 0.0f);

            int hudTextureSampler = glGetUniformLocation(hudProgram.getProgramID(), "texture_sampler");
            glUniform1i(hudTextureSampler, 0);


            for (GraphicsObject graphicsObject : hudObjects) {
                graphicsObject.render(projModel);
            }


            glfwSwapBuffers(window);
            // Poll the events and swap the buffers
            glfwPollEvents();
        }
        // Dispose the game
//        program.deleteProgram();
        hudProgram.deleteProgram();
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
//                camera.updateMousePos(xpos, ypos);
//                glfwSetCursorPos(window, width/2, height/2);
            }
        };
    }

    public static void main(String[] args) {
        (new Main()).run();
//        (new Main2()).run();
//        (new Main3()).run();
//        new FontDemo(36, "asdas dasd as").run();
    }
}
