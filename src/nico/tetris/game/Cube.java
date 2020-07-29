package nico.tetris.game;

import nico.tetris.engine.math.Vector3f;

public class Cube {

    private final Vector3f position;

    private final Vector3f color;

    public Cube(float x, float y, float z) {
        this(new Vector3f(x, y, z));
    }

    public Cube(float x, float y, float z, Vector3f color) {
        this.position = new Vector3f(x, y, z);
        this.color = color;
    }

    public Cube(Vector3f position) {
        this(position, new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public Cube(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }
}
