package nico.tetris.engine.rendering;

import nico.tetris.engine.rendering.shaders.CubeShader;

import nico.tetris.engine.rendering.shaders.QuadShader;
import nico.tetris.engine.rendering.textures.TextureManager;

import nico.tetris.game.Cube;
import nico.tetris.game.Quad;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class Renderer {

    private final Model cubeModel;
    private final Model quadModel;

    private final CubeShader cubeShader;
    private final QuadShader quadShader;

    private final Camera fixedCamera = new Camera(3.0f, 8.0f, 11.0f, 0.4f, 0.2f);
    private final Light fixedLight = new Light(6.0f, 12.0f, 11.0f, 1.5f);

    public final ArrayList<Cube> cubesBatch;
    public final ArrayList<Quad> quadsBatch;

    public Renderer() {
        this.cubeModel = new Model(Model.CUBE_VERTICES, Model.CUBE_NORMALS, Model.CUBE_INDICES);
        this.quadModel = new Model(Model.QUAD_VERTICES, Model.QUAD_INDICES);
        this.cubeShader = new CubeShader();
        this.quadShader = new QuadShader();
        this.cubesBatch = new ArrayList<>();
        this.quadsBatch = new ArrayList<>();
        this.cubeShader.start();
        this.cubeShader.loadProjectionMatrix(70.0f, 0.1f, 50.0f);
        this.cubeShader.stop();
    }

    public void prepare() {
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); //Set background color
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT); //Clear previous frame
        GL11.glEnable(GL11.GL_DEPTH_TEST); //Render cube faces correctly
    }

    public void render() {
        this.renderCubes();
        this.renderQuads();
    }

    private void renderCubes() {
        this.cubeShader.start();

        //Prepare model
        GL30.glBindVertexArray(cubeModel.getVao()); //Bind VAO
        GL20.glEnableVertexAttribArray(Model.VERTICES); //Enable attribute list containing vertices
        GL20.glEnableVertexAttribArray(Model.NORMALS); //Enable attribute list containing normals

        //Load view matrix (camera calculations)
        this.cubeShader.loadViewMatrix(fixedCamera.getPosition(), fixedCamera.getYaw(), fixedCamera.getPitch());

        this.cubeShader.loadLight(fixedLight.getPosition(), fixedLight.getIntensity());

        //This is faster than rendering every cube individually
        for(Cube cube : cubesBatch) {
            this.cubeShader.loadCubeColor(cube.getColor());
            this.cubeShader.loadTransformationMatrix(cube.getPosition()); //Load cube position
            GL20.glDrawElements(GL11.GL_TRIANGLES, cubeModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draw model
        }

        //Finish frame
        GL20.glDisableVertexAttribArray(Model.VERTICES); //Disable attribute list containing vertices
        GL20.glDisableVertexAttribArray(Model.NORMALS); //Disable attribute list containing normals
        GL30.glBindVertexArray(0); //Unbind VAO

        this.cubesBatch.clear();
        this.cubeShader.stop();
    }

    private void renderQuads() {
        this.quadShader.start();

        //Prepare model
        GL30.glBindVertexArray(quadModel.getVao()); //Bind VAO
        GL20.glEnableVertexAttribArray(Model.VERTICES); //Enable attribute list containing vertices

        for(Quad quad : quadsBatch) {
            TextureManager.get(quad.getTexture()).bind();
            this.quadShader.loadTransformationMatrix(quad.getPosition(), quad.getScale());
            GL20.glDrawElements(GL11.GL_TRIANGLES, quadModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0); //Draw model
        }

        //Finish frame
        GL20.glDisableVertexAttribArray(Model.VERTICES); //Disable attribute list containing vertices
        GL30.glBindVertexArray(0); //Unbind VAO

        this.quadsBatch.clear();
        this.quadShader.stop();
    }

    public void cleanUp() {
        System.out.println("Cleaning up..."); //This is very important
        TextureManager.deleteTextures();
        this.cubeModel.cleanUp();
        this.quadModel.cleanUp();
        this.cubeShader.cleanUp();
        this.quadShader.cleanUp();
    }
}
