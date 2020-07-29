package nico.tetris.engine.rendering.shaders;

import nico.tetris.engine.math.Matrix4f;
import nico.tetris.engine.math.Vector2f;
import nico.tetris.engine.math.Vector3f;
import nico.tetris.engine.rendering.Model;

public class CubeShader extends ShaderProgram {

    private int transformation_matrix;
    private int projection_matrix;
    private int view_matrix;

    private int light_position;
    private int light_intensity;

    private int cube_color;

    public CubeShader() {
        super("cube_vertex_shader.glsl", "cube_fragment_shader.glsl");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(Model.VERTICES, "vertex");
        super.bindAttribute(Model.NORMALS, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        this.transformation_matrix = super.getUniformLocation("transformation_matrix");
        this.projection_matrix = super.getUniformLocation("projection_matrix");
        this.view_matrix = super.getUniformLocation("view_matrix");

        this.light_position = super.getUniformLocation("light_position");
        this.light_intensity = super.getUniformLocation("light_intensity");

        this.cube_color = super.getUniformLocation("cube_color");
    }

    public void loadTransformationMatrix(Vector3f position) {
        super.loadMatrix(transformation_matrix, Matrix4f.createTransformationMatrix(position, new Vector2f(1.0f, 1.0f)));
    }

    public void loadProjectionMatrix(float fov, float zNear, float zFar) {
        super.loadMatrix(projection_matrix, Matrix4f.createProjectionMatrix(fov, zNear, zFar));
    }

    public void loadViewMatrix(Vector3f position, float yaw, float pitch) {
        super.loadMatrix(view_matrix, Matrix4f.createViewMatrix(position, yaw, pitch));
    }

    public void loadLight(Vector3f position, float intensity) {
        super.loadVector(light_position, position);
        super.loadFloat(light_intensity, intensity);
    }

    public void loadCubeColor(Vector3f color) {
        super.loadVector(cube_color, color);
    }
}
