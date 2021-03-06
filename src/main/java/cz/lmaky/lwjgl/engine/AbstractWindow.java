package cz.lmaky.lwjgl.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *
 * @author Lmaky
 */
public abstract class AbstractWindow {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final String TITLE = "Window";
    
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;

     // The window handle
    private long window;

    protected int width = WIDTH;
    protected int height = HEIGHT;
    protected String title = TITLE;
    
    protected abstract void initContent();
    protected abstract void loop(long window);
    protected abstract GLFWKeyCallback createKeyCallback();
    protected abstract GLFWCursorPosCallback createCursorPosCallback();

    public void run() {
        try {
            initWindow();
            initContent();
            loop(window);
 
            // Release window and window callbacks
            GLFW.glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            GLFW.glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void initWindow() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        errorCallback = GLFWErrorCallback.createPrint(System.err);
        GLFW.glfwSetErrorCallback(errorCallback);
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (GLFW.glfwInit() != GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Window Hints for OpenGL context
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        
        // Create the window
        window = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        keyCallback = createKeyCallback();
             
        GLFW.glfwSetKeyCallback(window, keyCallback);

        cursorPosCallback = createCursorPosCallback();
        GLFW.glfwSetCursorPosCallback(window, cursorPosCallback);

        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // Center our window
        GLFW.glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height()- height) / 2
        );

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        // Enable v-sync
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
    }
}
