package io.github.some_example_name.Sprites.GameObjects;

public class GameObject extends Sprite {

    //declares X and Y coordinates for all game objects
    protected float x;
    protected float y;

    //GameObject constructer, takes the starting x and y coords as peramiters and spawns a new game object at those coords
    public GameObject(float StartX, float StartY) {
        this.x = StartX;
        this.y = StartY;
    }

    //getters and setters for GameObject so other classes can access x and y coords
    public getX() {
        return x;
    }
    public setX(float x) {
        this.x = x;
    }

    public gety() {
        return y;
    }
    public sety(float y) {
        this.y = y;
    }
}