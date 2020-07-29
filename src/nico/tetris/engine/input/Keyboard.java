package nico.tetris.engine.input;

import nico.tetris.engine.gamestates.GameStateManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {

    private final GameStateManager gameStateManagerReference;

    public Keyboard(GameStateManager gameStateManagerReference) {
        this.gameStateManagerReference = gameStateManagerReference;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW.GLFW_PRESS)
            this.gameStateManagerReference.keyPressed(key);
        else if(action == GLFW.GLFW_RELEASE)
            this.gameStateManagerReference.keyReleased(key);
    }
}
