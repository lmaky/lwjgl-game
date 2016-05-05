package cz.lmaky.lwjgl.engine;

import cz.lmaky.lwjgl.engine.utils.ResourceUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

/**
 *
 * @author Lmaky
 */
public class ShaderProgram {

    private int programID;
    private int vertexShaderId;
    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram() {
        programID = GL20.glCreateProgram();
        uniforms = new HashMap<>();
    }

    public void createVertexShader(String shaderName) {
        vertexShaderId = addShader(shaderName, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderName) {
        fragmentShaderId = addShader(shaderName, GL_FRAGMENT_SHADER);
    }
    
    private int addShader(String shaderName, int shaderType) {
        String resourceAsString = ResourceUtils.getResourceAsString(shaderName);

        int shaderID = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shaderID, resourceAsString);
        GL20.glCompileShader(shaderID);

        int result = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
        if (result == GL11.GL_FALSE) {
            throw new IllegalStateException("Shader is not loaded.");
        }
        GL20.glAttachShader(programID, shaderID);
        return shaderID;
    }

    public void createUniform(String uniformName) {
        int unifotmId = GL20.glGetUniformLocation(programID, uniformName);
        uniforms.put(uniformName, unifotmId);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        value.get(fb);
        glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void linkProgram() {
        GL20.glLinkProgram(programID);
        int linkStatus = GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS);
        if (linkStatus != GL11.GL_TRUE) {
            throw new IllegalStateException("Program failed with link.");
        }
    }

    public void deleteProgram() {
        unbind();
        GL20.glDetachShader(programID, vertexShaderId);
        GL20.glDetachShader(programID, fragmentShaderId);
        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(programID);
    }
    
    public void bind() {
        GL20.glUseProgram(programID);
    }
    
    public void unbind() {
        GL20.glUseProgram(0);
    }
}
