package cz.lmaky.lwjgl.game;

import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 *
 * @author Lmaky
 */
public class ShaderProgram {
    private boolean linked = false;
    private int programID;
    private List<Shader> shaders;
    
    public void createProgram() {
        programID = glCreateProgram();
        shaders = new ArrayList<>();
    }
    
    public boolean addShader(Shader shader) {
        if(shader.isLoaded()) {
            glAttachShader(programID, shader.getShaderID());
            shaders.add(shader);
            return true;
        }
        return false;
    }
    
    public boolean linkProgram() {
        glLinkProgram(programID);
        int linkStatus = glGetProgrami(programID, GL_LINK_STATUS);
        if (linkStatus == GL_TRUE) {
            linked = true;
        }
        return linked;
    }

    public void deleteProgram() {
        if (linked) {
            linked = false;
            unbind();
            for (Shader shader : shaders) {
                glDetachShader(programID, shader.getShaderID());
                shader.deleteShader();
            }
            glDeleteProgram(programID);
        }
    }
    
    public void bindProgram() {
        if (linked) {
            glUseProgram(programID);
        }
    }
    
    public static void unbind()
    {
        glUseProgram(0);
    }
}
