package nico.tetris.engine.rendering;

import nico.tetris.engine.math.Vector3f;

public class Light {

    private final Vector3f position;
    private final float intensity;

    public Light(float x, float y, float z, float intensity) {
        this.position = new Vector3f(x, y, z);
        this.intensity = intensity;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getIntensity() {
        return intensity;
    }
}
