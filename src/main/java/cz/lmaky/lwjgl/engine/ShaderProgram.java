package cz.lmaky.lwjgl.engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Lmaky
 */
public class ShaderProgram {
    private boolean linked = false;
    private int programID;
    private List<Shader> shaders;
    
    public void createProgram() {
        programID = GL20.glCreateProgram();
        shaders = new ArrayList<>();
    }

    public int getProgramID() {
        return programID;
    }
    
    public boolean addShader(Shader shader) {
        if(shader.isLoaded()) {
            GL20.glAttachShader(programID, shader.getShaderID());
            shaders.add(shader);
            return true;
        }
        return false;
    }
    
    public boolean linkProgram() {
        GL20.glLinkProgram(programID);
        int linkStatus = GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS);
        if (linkStatus == GL11.GL_TRUE) {
            linked = true;
        }
        return linked;
    }

    public void deleteProgram() {
        if (linked) {
            linked = false;
            unbind();
            for (Shader shader : shaders) {
                GL20.glDetachShader(programID, shader.getShaderID());
                shader.deleteShader();
            }
            GL20.glDeleteProgram(programID);
        }
    }
    
    public void bindProgram() {
        if (linked) {
            GL20.glUseProgram(programID);
        }
    }
    
    public void unbind() {
        GL20.glUseProgram(0);
    }
}
