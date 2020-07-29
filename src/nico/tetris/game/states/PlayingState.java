package nico.tetris.game.states;

import nico.tetris.engine.gamestates.GameState;
import nico.tetris.engine.gamestates.GameStateManager;
import nico.tetris.engine.rendering.Renderer;

import nico.tetris.game.Grid;
import nico.tetris.game.Quad;
import nico.tetris.game.TetrominoQueue;

import org.lwjgl.glfw.GLFW;

public class PlayingState extends GameState {

    private final Grid grid;
    private final TetrominoQueue queue;

    private final Quad lostMessage;

    public PlayingState(GameStateManager stateManager) {
        super(stateManager);
        this.grid = new Grid();
        this.queue = new TetrominoQueue();
        this.lostMessage = new Quad("lost", 0.0f, 0.0f, 1.6f, 0.9f);
    }

    @Override
    public void init() {
        this.grid.place(queue.next());
    }

    @Override
    public void update() {
        if(!grid.playerHasLost()) {
            this.grid.movePiecesDown();
            this.grid.lookForFullLine();
            this.grid.fixCubes(queue);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.cubesBatch.addAll(grid.getFixedCubes());
        renderer.cubesBatch.addAll(grid.getFallingCubes());
        renderer.cubesBatch.addAll(queue.getCubesForRendering());

        if(grid.playerHasLost())
            renderer.quadsBatch.add(lostMessage);
    }

    @Override
    public void keyPressed(int key) {
        if(key == GLFW.GLFW_KEY_RIGHT){
            this.grid.movePieceRight();
        }
        else if(key == GLFW.GLFW_KEY_LEFT) {
            this.grid.movePieceLeft();
        }
        else if(key == GLFW.GLFW_KEY_DOWN) {
            this.grid.setSoftDrop(true);
        }
        else if(key == GLFW.GLFW_KEY_UP) {
            this.grid.rotateClockwise();
        }
        else if(key == GLFW.GLFW_KEY_RIGHT_CONTROL) {
            this.grid.rotateCounterClockwise();
        }
        else if(key == GLFW.GLFW_KEY_SPACE) {
            this.grid.hardDrop(queue);
        }
        else if(key == GLFW.GLFW_KEY_ESCAPE) {
            super.stateManager.backToPrevious();
        }
    }

    @Override
    public void keyReleased(int key) {
        if(key == GLFW.GLFW_KEY_DOWN) {
            this.grid.setSoftDrop(false);
        }
    }
}
