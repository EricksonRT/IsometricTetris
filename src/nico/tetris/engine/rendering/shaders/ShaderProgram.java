package nico.tetris.engine.rendering.shaders;

import nico.tetris.engine.math.Matrix4f;
import nico.tetris.engine.math.Vector2f;
import nico.tetris.engine.math.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private final int vertex;
    private final int fragment;

    private final int program;

    public ShaderProgram(String vertexFile, String fragmentFile) {
        this.vertex = this.loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        this.fragment = this.loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        this.program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vertex);
        GL20.glAttachShader(program, fragment);
        this.bindAttributes();
        GL20.glLinkProgram(program);
        GL20.glValidateProgram(program);
        this.getAllUniformLocations();
        System.out.println("Created shader with id " + program + " with vertex shader " + vertex + " and fragment shader " + fragment);
    }

    //Bind data stored in attribute list to an 'in' variable in the vertex shader
    protected abstract void bindAttributes();

    //Load uniform variables
    protected abstract void getAllUniformLocations();

    public void start() {
        GL20.glUseProgram(program);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        System.out.println("Cleaning up shader with id " + program + " with vertex shader " + vertex + " and fragment shader " + fragment);
        this.stop();
        GL20.glDetachShader(program, vertex);
        GL20.glDetachShader(program, fragment);
        GL20.glDeleteShader(vertex);
        GL20.glDeleteShader(fragment);
        GL20.glDeleteProgram(program);
    }

    //These are the 'in' variables in the vertex shader
    protected void bindAttribute(int list, String variable) {
        GL20.glBindAttribLocation(program, list, variable);
    }

    //Get the location of a uniform variable, needed to load data in it
    protected int getUniformLocation(String variable) {
        return GL20.glGetUniformLocation(program, variable);
    }

    //Load a uniform variable
    protected void loadMatrix(int location, Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.store(buffer);
        buffer.flip();
        GL20.glUniformMatrix4fv(location, false, buffer);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadVector(int location, Vector2f vector) {
        GL20.glUniform2f(location, vector.x, vector.y);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    private int loadShader(String file, int type) {
        StringBuilder shaderCode = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/shaders/" + file));
            String line = reader.readLine();
            while(line != null) {
                shaderCode.append(line).append("\n");
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not read file res/shaders/" + file);
        } catch (IOException e) {
            System.err.println("Could not find file res/shaders/" + file);
        }
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, shaderCode);
        GL20.glCompileShader(shader);
        if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Could not compile shader " + file + "\n");
            System.out.println(GL20.glGetShaderInfoLog(shader, 500));
            System.exit(-1);
        }
        return shader;
    }
}
