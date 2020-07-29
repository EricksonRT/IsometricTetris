package nico.tetris.engine.rendering.shaders;

import nico.tetris.engine.math.Matrix4f;
import nico.tetris.engine.math.Vector2f;
import nico.tetris.engine.math.Vector3f;
import nico.tetris.engine.rendering.Model;

public class QuadShader extends ShaderProgram {

    private int transformation_matrix;

    public QuadShader() {
        super("quad_vertex_shader.glsl", "quad_fragment_shader.glsl");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(Model.VERTICES, "vertex");
    }

    @Override
    protected void getAllUniformLocations() {
        this.transformation_matrix = super.getUniformLocation("transformation_matrix");
    }

    public void loadTransformationMatrix(Vector3f position, Vector2f scale) {
        super.loadMatrix(transformation_matrix, Matrix4f.createTransformationMatrix(position, scale));
    }
}
