package nico.tetris;

import nico.tetris.engine.Engine;
import nico.tetris.engine.gamestates.GameStateManager;
import nico.tetris.engine.rendering.Renderer;

public class IsometricTetris {

    private final Engine engine;
    private final Renderer renderer;
    private final GameStateManager gameStateManager;

    private IsometricTetris() {
        this.engine = new Engine();
        this.renderer = new Renderer();
        this.gameStateManager = new GameStateManager();
    }

    private void run() {
        this.engine.setupCallbacks(gameStateManager);
        while(engine.isRunning() && !gameStateManager.shouldClose()) {
            this.update();
            this.render();
        }
        this.terminate();
    }

    private void update() {
        this.engine.update();
        this.gameStateManager.update();
    }

    private void render() {
        this.renderer.prepare();
        this.gameStateManager.render(renderer);
        this.renderer.render();
    }

    private void terminate() {
        this.renderer.cleanUp();
        this.engine.terminate();
    }

    public static void main(String[] args) {
        new IsometricTetris().run();
    }
}
