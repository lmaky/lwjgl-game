package cz.lmaky.lwjgl.engine;

import cz.lmaky.lwjgl.engine.utils.ResourceUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

/**
 *
 * @author Lmaky
 */
public class Shader {

    private final int shaderType;
    private final String resource;
    
    private int shaderID;
    private boolean loaded = false;
    
    public Shader(String resource, int shaderType) {
        this.shaderType = shaderType;
        this.resource = resource;
    }
    
    public boolean isLoaded() {
        return loaded;
    }
    
    public int getShaderID() {
        return shaderID;
    }
    
    public void deleteShader() {
        if (loaded) {
            loaded = false;
            GL20.glDeleteShader(shaderID);
        }
    }
    
    public boolean loadShader() {
        String resourceAsString;
        try {
            resourceAsString = ResourceUtils.getResourceAsString(resource);
        } catch (IOException ex) {
            System.err.println("Error while read file " + resource);
            return false;
        }

        shaderID = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shaderID, resourceAsString);
        GL20.glCompileShader(shaderID);
        
        int result = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
        if (result == GL11.GL_FALSE) {
            return false;
        }
        loaded = true;
        return true;
    }
}
