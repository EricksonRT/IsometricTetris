package nico.tetris.engine.gamestates;

import nico.tetris.engine.rendering.Renderer;

import nico.tetris.game.states.MainMenu;
//import nico.tetris.game.states.PlayingState;

public class EngineTester extends GameState {

//    private final Grid grid;
//    private final TetrominoQueue queue;

    public EngineTester(GameStateManager stateManager) {
        super(stateManager);
//        this.grid = new Grid();
//        this.queue = new TetrominoQueue();
    }

    @Override
    public void init() {
//        this.grid.place(queue.next());
//        super.stateManager.changeState(new PlayingState(stateManager));
        super.stateManager.changeState(new MainMenu(stateManager));
    }

    @Override
    public void update() {
//        this.grid.movePiecesDown();
//        this.grid.fixCubes(queue);
    }

    @Override
    public void render(Renderer renderer) {
//        renderer.renderBatch.addAll(grid.getFallingCubes());
//        renderer.renderBatch.addAll(grid.getFixedCubes());
    }

    @Override
    public void keyPressed(int key) {
//        if(key == GLFW.GLFW_KEY_RIGHT) {
//            this.grid.movePieceRight();
//        } else if(key == GLFW.GLFW_KEY_LEFT) {
//            this.grid.movePieceLeft();
//        } else if(key == GLFW.GLFW_KEY_DOWN) {
//            this.grid.setSoftDrop(true);
//        }
    }

    @Override
    public void keyReleased(int key) {
//        if(key == GLFW.GLFW_KEY_DOWN) {
//            this.grid.setSoftDrop(false);
//        }
    }
}
