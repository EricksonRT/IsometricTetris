package nico.tetris.engine.rendering;

import nico.tetris.engine.utils.BufferConverter;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;

public class Model {

    public static final int VERTICES = 0;
    public static final int NORMALS = 1;

    private int vao;
    private final Stack<Integer> vbo = new Stack<>();

    private int vertexCount;

    public Model(float[] vertices, float[] normals, int[] indices) {
        this.createVAO();
        this.storeDataInAttributeList(vertices, VERTICES);
        this.storeDataInAttributeList(normals, NORMALS);
        this.bindIndicesBuffer(indices);
        this.unbindVAO();
        System.out.println("Created model with VAO " + vao);
    }

    public Model(float[] vertices, int[] indices) {
        this.createVAO();
        this.storeDataInAttributeList(vertices, VERTICES);
        this.bindIndicesBuffer(indices);
        this.unbindVAO();
        System.out.println("Created model with VAO " + vao);
    }

    public int getVao() {
        return vao;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    private void createVAO() {
        this.vao = GL30.glGenVertexArrays(); //Create VAO
        GL30.glBindVertexArray(vao); //Bind VAO
    }

    private void storeDataInAttributeList(float[] data, int list) {
        this.vbo.add(GL15.glGenBuffers()); //Create VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.peek()); //Bind VBO
        FloatBuffer buffer = BufferConverter.toFloat(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Store data in VBO
        GL20.glVertexAttribPointer(list, 3, GL11.GL_FLOAT, false, 0, 0); //Store data in attribute list
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbind VBO
    }

    private void bindIndicesBuffer(int[] indices) {
        this.vbo.add(GL15.glGenBuffers());
        this.vertexCount = indices.length;
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo.peek());
        IntBuffer buffer = BufferConverter.toInt(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    public void cleanUp() {
        System.out.println("Deleting VAO " + vao);
        GL30.glDeleteVertexArrays(vao);
        for(int vbo : vbo) {
            GL15.glDeleteBuffers(vbo);
        }
    }

    public static final float[] QUAD_VERTICES = new float[] {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
    };

    public static final int[] QUAD_INDICES = new int[] {
            0, 1, 3,
            3, 1, 2
    };

    public static final float[] CUBE_VERTICES = new float[] {
            -0.25f,0.25f,-0.25f,
            -0.25f,-0.25f,-0.25f,
            0.25f,-0.25f,-0.25f,
            0.25f,0.25f,-0.25f,

            -0.25f,0.25f,0.25f,
            -0.25f,-0.25f,0.25f,
            0.25f,-0.25f,0.25f,
            0.25f,0.25f,0.25f,

            0.25f,0.25f,-0.25f,
            0.25f,-0.25f,-0.25f,
            0.25f,-0.25f,0.25f,
            0.25f,0.25f,0.25f,

            -0.25f,0.25f,-0.25f,
            -0.25f,-0.25f,-0.25f,
            -0.25f,-0.25f,0.25f,
            -0.25f,0.25f,0.25f,

            -0.25f,0.25f,0.25f,
            -0.25f,0.25f,-0.25f,
            0.25f,0.25f,-0.25f,
            0.25f,0.25f,0.25f,

            -0.25f,-0.25f,0.25f,
            -0.25f,-0.25f,-0.25f,
            0.25f,-0.25f,-0.25f,
            0.25f,-0.25f,0.25f

    };

    public static final int[] CUBE_INDICES = new int[] {
            0,1,3,
            3,1,2,
            4,5,7,
            7,5,6,
            8,9,11,
            11,9,10,
            12,13,15,
            15,13,14,
            16,17,19,
            19,17,18,
            20,21,23,
            23,21,22

    };

    public static final float[] CUBE_NORMALS = new float[] {
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,

            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,

            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
    };
}
