package nico.tetris.game;

import nico.tetris.engine.math.Vector2f;
import nico.tetris.engine.math.Vector3f;

import java.util.ArrayList;

public class Tetromino {

    public static final Tetromino I = new Tetromino(Colors.LIGHT_BLUE, 1,0, 1,1, 1,2, 1,3);
    public static final Tetromino J = new Tetromino(Colors.BLUE, 0,0, 1,0, 1,1, 1,2);
    public static final Tetromino L = new Tetromino(Colors.ORANGE, 0,0, 1,0, 0,1, 0,2);
    public static final Tetromino O = new Tetromino(Colors.YELLOW, 0,0, 1,0, 0,1, 1,1);
    public static final Tetromino S = new Tetromino(Colors.RED, 0,0, 1,0, 1,1, 2,1);
    public static final Tetromino Z = new Tetromino(Colors.GREEN, 0,1, 1,1, 1,0, 2,0);
    public static final Tetromino T = new Tetromino(Colors.PURPLE, 0,1, 1,1, 1,0, 2,1);

    private final ArrayList<Vector2f> cubes;
    private final Vector3f color;

    public Tetromino(Vector3f color, int...cubesPosition) {
        this.cubes = new ArrayList<>();
        for(int i = 0; i < cubesPosition.length; i += 2) {
            this.cubes.add(new Vector2f(cubesPosition[i], cubesPosition[i + 1]));
        }
        this.color = color;
    }

    public ArrayList<Cube> createCubes(float fromX, float fromY) {
        ArrayList<Cube> createdCubes = new ArrayList<>();
        for(Vector2f pos : cubes) {
            createdCubes.add(new Cube(new Vector3f(pos.scaled(0.5f, 0.5f).translated(fromX, fromY), 1.0f), color));
        }
        return createdCubes;
    }
}
