package io.github.some_example_name.Sprites.GameObjects;
import io.github.some_example_name.Sprites.Sprite;

public class GameObject extends Sprite {

    //declares X and Y coordinates for all game objects
    protected float x;
    protected float y;

    //GameObject constructer, takes the starting x and y coords as peramiters and spawns a new game object at those coords
    public GameObject(float StartX, float StartY) {
        this.x = StartX;
        this.y = StartY;
    }

    //Default constructer in case we want to spawn a new game object without knowing its coords
    public GameObject() {
        this.x = 0;
        this.y = 0;
    }

    //getters and setters for GameObject so other classes can access x and y coords
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float gety() {
        return y;
    }
    public void sety(float y) {
        this.y = y;
    }
}