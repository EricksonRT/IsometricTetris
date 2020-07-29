package nico.tetris.game;

import nico.tetris.engine.math.Vector2f;
import nico.tetris.engine.math.Vector3f;

public class Quad {

    private final Vector2f position;
    private final Vector2f scale;
    private final String texture;

    public Quad(String texture, float x, float y, float w, float h) {
        this(texture, new Vector2f(x, y), new Vector2f(w, h));
    }

    public Quad(String texture, Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
        this.texture = texture;
    }

    public Vector3f getPosition() {
        return new Vector3f(position, 0.0f);
    }

    public Vector2f getScale() {
        return scale;
    }

    public String getTexture() {
        return texture;
    }
}
