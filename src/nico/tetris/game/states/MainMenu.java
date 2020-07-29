package nico.tetris.game.states;

import nico.tetris.engine.gamestates.GameState;
import nico.tetris.engine.gamestates.GameStateManager;
import nico.tetris.engine.rendering.Renderer;
import nico.tetris.game.Quad;
import org.lwjgl.glfw.GLFW;

public class MainMenu extends GameState {

    private final Quad title;
    private final Quad playButton;
    private final Quad quitButton;

    private final Quad selectedL1;
    private final Quad selectedR1;
    private final Quad selectedL2;
    private final Quad selectedR2;

    //Act as selected 0 or selected 1
    private boolean selection = true;

    public MainMenu(GameStateManager stateManager) {
        super(stateManager);
        this.title = new Quad("logo", 0.0f, 0.7f, 0.7f, 0.7f);
        this.playButton = new Quad("play_button", 0.0f, 0.0f, 0.6f, 0.2f);
        this.quitButton = new Quad("quit_button", 0.0f, -0.4f, 0.6f, 0.2f);
        this.selectedL1 = new Quad("selected_l", 0.5f, 0.0f, 0.12f, 0.2f);
        this.selectedR1 = new Quad("selected_r", -0.5f, 0.0f, 0.12f, 0.2f);
        this.selectedL2 = new Quad("selected_l", 0.5f, -0.4f, 0.12f, 0.2f);
        this.selectedR2 = new Quad("selected_r", -0.5f, -0.4f, 0.12f, 0.2f);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Renderer renderer) {
        renderer.quadsBatch.add(title);
        renderer.quadsBatch.add(playButton);
        renderer.quadsBatch.add(quitButton);

        if(selection) {
            renderer.quadsBatch.add(selectedL1);
            renderer.quadsBatch.add(selectedR1);
        } else {
            renderer.quadsBatch.add(selectedL2);
            renderer.quadsBatch.add(selectedR2);
        }
    }

    @Override
    public void keyPressed(int key) {
        if(key == GLFW.GLFW_KEY_UP || key == GLFW.GLFW_KEY_DOWN) {
            this.selection = !selection;
        }
        else if(key == GLFW.GLFW_KEY_SPACE || key == GLFW.GLFW_KEY_ENTER) {
            if(selection)
                super.stateManager.changeState(new PlayingState(stateManager));
            else
                super.stateManager.close();
        }
    }

    @Override
    public void keyReleased(int key) {

    }
}
