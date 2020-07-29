package nico.tetris.engine.math;

import java.util.Objects;

public class Vector2f {

    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f translated(float tx, float ty) {
        return new Vector2f(x + tx, y + ty);
    }

    public Vector2f negated() {
        return new Vector2f(-x, -y);
    }

    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public Vector2f scaled(float dx, float dy) {
        return new Vector2f(x * dx, y * dy);
    }

    @Override
    public String toString() {
        return "Vector2f{" + x + ";" + y + "}";
    }
}
