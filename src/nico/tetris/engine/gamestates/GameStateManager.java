package nico.tetris.engine.gamestates;

import nico.tetris.engine.rendering.Renderer;

import java.util.Stack;

public class GameStateManager {

    private final Stack<GameState> stateStack;

    private boolean shouldClose;

    public GameStateManager() {
        this.stateStack = new Stack<>();
        this.changeState(new EngineTester(this));
    }

    public void changeState(GameState state) {
        this.stateStack.add(state);
        state.init();
    }

    public void backToPrevious() {
        if(stateStack.size() > 1)
            this.stateStack.pop();
    }

    public void update() {
        this.stateStack.peek().update();
    }

    public void render(Renderer renderer) {
        this.stateStack.peek().render(renderer);
    }

    public void keyPressed(int key) {
        this.stateStack.peek().keyPressed(key);
    }

    public void keyReleased(int key) {
        this.stateStack.peek().keyReleased(key);
    }

    public void close() {
        this.shouldClose = true;
    }

    public boolean shouldClose() {
        return shouldClose;
    }
}
