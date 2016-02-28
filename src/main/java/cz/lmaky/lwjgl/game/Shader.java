package cz.lmaky.lwjgl.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


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
        
        InputStream is = this.getClass().getResourceAsStream(resource);

        // Get all lines from a file
        StringBuilder shaderCode = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
               shaderCode.append(line).append("\n");
            }
        } catch (IOException ex) {
            System.err.println("error: " + ex.getMessage());
            return false;
        }
        
        shaderID = GL20.glCreateShader(shaderType);

        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);
        
        int result = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);

        if (result == GL11.GL_FALSE) {
            return false;
        }
        loaded = true;
        return true;
    }
}
