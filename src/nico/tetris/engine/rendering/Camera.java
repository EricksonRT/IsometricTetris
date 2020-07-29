package nico.tetris.engine.rendering;

import nico.tetris.engine.math.Vector3f;

public class Camera {

    private final Vector3f position;
    private final float yaw;
    private final float pitch;

    public Camera(float x, float y, float z, float yaw, float pitch) {
        this.position = new Vector3f(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
