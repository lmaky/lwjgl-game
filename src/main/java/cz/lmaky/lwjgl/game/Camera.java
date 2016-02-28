package cz.lmaky.lwjgl.game;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 * Created by lmaky
 */
public class Camera {

    private int width;
    private int height;

    private Vector3f position = new Vector3f(0.0f, 0.0f, 2.0f);
    private Vector3f direction = new Vector3f();
    private Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);

    // horizontal angle : toward -Z
    private float horizontalAngle = 180f;

    // vertical angle : 0, look at the horizon
    private float verticalAngle = 0.0f;

    // Initial Field of View
    private float initialFoV = 45.0f;

    private float speed = 3.0f; // 3 units / second

    private float mouseSpeedX = 0.1f;
    private float mouseSpeedY = 0.05f;
//    private float mouseSpeed = 0.005f;

    private Matrix4f view;

    private Matrix4f projection;

    double lastTime;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        this.view = new Matrix4f();
        this.projection = new Matrix4f();
        lastTime = GLFW.glfwGetTime();
    }

    public Matrix4f getViewMatrix() {
        double time = GLFW.glfwGetTime();
        float deltaTime = (float)(time - lastTime);

        float x = (float) Math.sin(Math.toRadians(horizontalAngle)) * (float) Math.cos(Math.toRadians(verticalAngle));
        float y = (float) Math.sin(Math.toRadians(verticalAngle));
        float z = (float) Math.cos(Math.toRadians(horizontalAngle)) * (float) Math.cos(Math.toRadians(verticalAngle));
        direction.set(x, y, z);
        direction.add(position);
        view.identity();
        view.setLookAt(position, direction, up);

        lastTime = time;
        return view;
    }

    public Matrix4f getProjectionMatrix() {
        projection.identity();
        projection.perspective(initialFoV, (float)width/height, 0.1f, 100f);
        return projection;
    }

    public void updateMousePos(double mouseX, double mouseY) {
        // Compute new orientation
        horizontalAngle += mouseSpeedX * (float)( width/2 - mouseX);
        verticalAngle   += mouseSpeedY * (float)(height/2 - mouseY);
    }
}
