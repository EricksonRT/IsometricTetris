package nico.tetris.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class TetrominoQueue {

    private static final ArrayList<Tetromino> ALL_POSSIBLES = new ArrayList<>();

    static {
        ALL_POSSIBLES.add(Tetromino.I);
        ALL_POSSIBLES.add(Tetromino.J);
        ALL_POSSIBLES.add(Tetromino.L);
        ALL_POSSIBLES.add(Tetromino.O);
        ALL_POSSIBLES.add(Tetromino.S);
        ALL_POSSIBLES.add(Tetromino.Z);
        ALL_POSSIBLES.add(Tetromino.T);
    }

    private final ArrayBlockingQueue<Tetromino> queue;
    private final Random random = new Random();

    public TetrominoQueue() {
        this.queue = new ArrayBlockingQueue<>(4);
        this.queue.add(getRandomTetromino());
        this.queue.add(getRandomTetromino());
        this.queue.add(getRandomTetromino());
        this.queue.add(getRandomTetromino());
    }

    private Tetromino getRandomTetromino() {
        return ALL_POSSIBLES.get(random.nextInt(ALL_POSSIBLES.size()));
    }

    public Tetromino next() {
        Tetromino next = this.queue.poll();
        this.queue.add(getRandomTetromino());
        return next;
    }

    public ArrayList<Cube> getCubesForRendering() {
        ArrayList<Cube> cubes = new ArrayList<>();
        int index = 0;
        for(Tetromino tetromino : queue) {
            cubes.addAll(tetromino.createCubes(0.5f * 15, 6.0f - (2.2f * index)));
            index++;
        }
        return cubes;
    }
}
