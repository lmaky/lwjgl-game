package cz.lmaky.lwjgl.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glShaderSource;

/**
 *
 * @author Lmaky
 */
public class Shader {

    private int shaderID;
    private boolean loaded = false;
    
    public boolean isLoaded() {
        return loaded;
    }
    
    public int getShaderID() {
        return shaderID;
    }
    
    public void deleteShader() {
        if (loaded) {
            loaded = false;
            glDeleteShader(shaderID);
        }
    }
    
    public boolean loadShader(String sFile, int shaderType) {
        File shaderFile = new File(sFile);

        // Get all lines from a file
        StringBuilder shaderCode = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(shaderFile))) {
            String line;
            while ((line = br.readLine()) != null) {
               shaderCode.append(line).append("\n");
            }
        } catch (IOException ex) {
            System.err.println("error: " + ex.getMessage());
            return false;
        }
        
        shaderID = glCreateShader(shaderType);
        
        glShaderSource(shaderID, shaderCode);
        glCompileShader(shaderID);
        
        int result = glGetShaderi(shaderID, GL_COMPILE_STATUS);

        if (result == GL_FALSE) {
            return false;
        }
        loaded = true;
        return true;
    }
}
