package nico.tetris.engine.gamestates;

import nico.tetris.engine.rendering.Renderer;

public abstract class GameState {

    protected GameStateManager stateManager;

    public GameState(GameStateManager stateManager) {
        this.stateManager = stateManager;
    }

    public abstract void init();

    public abstract void update();

    public abstract void render(Renderer renderer);

    public abstract void keyPressed(int key);

    public abstract void keyReleased(int key);
}
