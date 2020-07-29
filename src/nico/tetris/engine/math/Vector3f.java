package nico.tetris.engine.math;

public class Vector3f extends Vector2f {

    public float z;

    public Vector3f(float x, float y, float z) {
        super(x, y);
        this.z = z;
    }

    public Vector3f(Vector2f v, float z) {
        this(v.x, v.y, z);
    }

    public Vector3f translated(float tx, float ty, float tz) {
        return new Vector3f(x + tx, y + ty, z + tz);
    }

    public Vector3f negated() {
        return new Vector3f(-x, -y, -z);
    }

    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector3f scaled(float dx, float dy, float dz) {
        return new Vector3f(x * dx, y * dy, z * dz);
    }

    public Vector2f to2D() {
        return new Vector2f(x, y);
    }

    @Override
    public String toString() {
        return "Vector3f{" + x + ";" + y + ";" + z + "}";
    }
}
