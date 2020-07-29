package nico.tetris.game;

import nico.tetris.engine.math.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid {

    private final ArrayList<Cube> fallingCubes;
    private final ArrayList<Cube> fixedCubes;

    private int moveDownDelay;
    private int moveDownDefaultDelay;
    private boolean softDrop;

    private int fixCubesDelay;

    private int lines;

    private final Vector2f rotationCenter;

    private boolean lost;

    public Grid() {
        this.fallingCubes = new ArrayList<>();
        this.fixedCubes = new ArrayList<>();
        this.moveDownDelay = 50;
        this.moveDownDefaultDelay = 50;
        this.softDrop = false;
        this.fixCubesDelay = 50;
        this.lines = 0;
        this.rotationCenter = new Vector2f(0.5f * 5, 0.5f * 17);
        this.lost = false;
    }

    public void place(Tetromino tetromino) {
        if(canBePlaced())
            this.fallingCubes.addAll(tetromino.createCubes(0.5f * 4, 0.5f * 16));
        else
            this.lost = true;
    }

    private boolean canBePlaced() {
        for(Cube cube : fixedCubes) {
            if(cube.getPosition().x == 0.5f * 4 && cube.getPosition().y == 0.5f * 16)
                return false;
        }
        return true;
    }

    public void movePiecesDown() {
        this.moveDownDelay--;
        //If the delay is 0 or if the player is holding the 'down' key...
        if(moveDownDelay == 0 || softDrop) {
            //Move down cubes if all cubes can go down
            if(pieceCanMoveDown()) {
                for(Cube cube : fallingCubes) {
                    cube.getPosition().y -= 0.5f;
                }
                this.rotationCenter.y -= 0.5f;
            }
            this.moveDownDelay = moveDownDefaultDelay;
        }
    }

    private boolean pieceCanMoveDown() {
        for(Cube fallingCube : fallingCubes) {
            //Check if cube landed on a fixed cube
            for(Cube fixedCube : fixedCubes) {
                if(fixedCube.getPosition().y == fallingCube.getPosition().y - 0.5f && fixedCube.getPosition().x == fallingCube.getPosition().x)
                    return false;
            }
            //Check if cube is at the bottom of the grid
            if(fallingCube.getPosition().y == 0.0f)
                return false;
        }
        return true;
    }

    public void movePieceLeft() {
        if(pieceCanMoveLeft()) {
            for(Cube cube : fallingCubes) {
                cube.getPosition().x -= 0.5f;
            }
            this.fixCubesDelay = 50;
            this.rotationCenter.x -= 0.5f;
        }
    }

    private boolean pieceCanMoveLeft() {
        for(Cube fallingCube : fallingCubes) {
            //Check if cube isn't next to a fixed cube
            for(Cube fixedCube : fixedCubes) {
                if(fixedCube.getPosition().y == fallingCube.getPosition().y && fixedCube.getPosition().x == fallingCube.getPosition().x - 0.5f)
                    return false;
            }
            //Check if cube is at the left of the grid
            if(fallingCube.getPosition().x == 0.0f)
                return false;
        }
        return true;
    }

    public void movePieceRight() {
        if(pieceCanMoveRight()) {
            for(Cube cube : fallingCubes) {
                cube.getPosition().x += 0.5f;
            }
            this.fixCubesDelay = 50;
            this.rotationCenter.x += 0.5f;
        }
    }

    private boolean pieceCanMoveRight() {
        for(Cube fallingCube : fallingCubes) {
            //Check if cube isn't next to a fixed cube
            for(Cube fixedCube : fixedCubes) {
                if(fixedCube.getPosition().y == fallingCube.getPosition().y && fixedCube.getPosition().x == fallingCube.getPosition().x + 0.5f)
                    return false;
            }
            //Check if cube is at the right of the grid
            if(fallingCube.getPosition().x == 4.5f)
                return false;
        }
        return true;
    }

    public void fixCubes(TetrominoQueue queue) {
        //If none of the cubes can move down it should be added to fix list
        if(!pieceCanMoveDown()) {
            this.fixCubesDelay--;
            if(fixCubesDelay == 0) {
                this.nextPiece(queue);
            }
        }
    }

    private void nextPiece(TetrominoQueue queue) {
        //Add falling cubes to fixed cubes list and place another piece
        this.fixedCubes.addAll(fallingCubes);
        this.fallingCubes.clear();
        this.fixCubesDelay = 50;
        this.place(queue.next());
        //Reset rotation center
        this.rotationCenter.x = 0.5f * 5;
        this.rotationCenter.y = 0.5f * 17;
    }

    public void rotateClockwise() {
        ArrayList<Cube> newCubes = new ArrayList<>();
        for(Cube fallingCube : fallingCubes) {
            //Calculate new coordinates
            float x1 = (fallingCube.getPosition().y - rotationCenter.y) + rotationCenter.x;
            float y1 = -(fallingCube.getPosition().x - rotationCenter.x) + rotationCenter.y;
            newCubes.add(new Cube(x1, y1, 1.0f, fallingCube.getColor()));
        }
        this.applyRotation(newCubes);
    }

    public void rotateCounterClockwise() {
        ArrayList<Cube> newCubes = new ArrayList<>();
        for(Cube fallingCube : fallingCubes) {
            //Calculate new coordinates
            float x1 = -(fallingCube.getPosition().y - rotationCenter.y) + rotationCenter.x;
            float y1 = (fallingCube.getPosition().x - rotationCenter.x) + rotationCenter.y;
            newCubes.add(new Cube(x1, y1, 1.0f, fallingCube.getColor()));
        }
        this.applyRotation(newCubes);
    }

    private void applyRotation(ArrayList<Cube> newCubes) {
        //Change cubes
        if(rotationSucceeded(newCubes)) {
            this.fixCubesDelay = 50;
            this.fallingCubes.clear();
            this.fallingCubes.addAll(newCubes);
        }
    }

    private boolean rotationSucceeded(ArrayList<Cube> newCubes) {
        //If one of the new cubes is outside the grid the piece cannot be rotated
        for(Cube cube : newCubes) {
            if(cube.getPosition().x < 0.0f || cube.getPosition().x > 4.5f)
                return false;
        }
        //If one of the new cubes intersects an old one the piece cannot be rotated
        for(Cube fixedCube : fixedCubes) {
            for(Cube newCube : newCubes) {
                if(fixedCube.getPosition().x == newCube.getPosition().x && fixedCube.getPosition().y == newCube.getPosition().y)
                    return false;
            }
        }
        return true;
    }

    public void lookForFullLine() {
        int lineCount = 0;
        for(float y = 0; y < 10.0f; y += 0.5f) {
            for(Cube cube : fixedCubes) {
                if(cube.getPosition().y == y)
                    lineCount++;
            }
            //If there are 10 cubes at the same height this line is full
            if(lineCount == 10) {
                //Remove the full line
                Iterator<Cube> iterator = this.fixedCubes.iterator();
                while(iterator.hasNext()) {
                    if(iterator.next().getPosition().y == y)
                        iterator.remove();
                }
                //Move down all cubes above
                for(Cube cube : fixedCubes) {
                    if(cube.getPosition().y > y)
                        cube.getPosition().y -= 0.5f;
                }
                //Every 5 lines the pieces fall faster
                this.lines++;
                if(lines % 5 == 0 && moveDownDefaultDelay > 5)
                    this.moveDownDefaultDelay -= 5;
            }
            lineCount = 0;
        }
    }

    public void hardDrop(TetrominoQueue queue) {
        //Move all cubes down instantly
        while(pieceCanMoveDown()) {
            for(Cube cube : fallingCubes) {
                cube.getPosition().y -= 0.5f;
            }
        }
        //Ignore fixCubeDelay
        this.nextPiece(queue);
    }

    public ArrayList<Cube> getFallingCubes() {
        return fallingCubes;
    }

    public ArrayList<Cube> getFixedCubes() {
        return fixedCubes;
    }

    public void setSoftDrop(boolean softDrop) {
        this.softDrop = softDrop;
    }

    public boolean playerHasLost() {
        return lost;
    }
}
