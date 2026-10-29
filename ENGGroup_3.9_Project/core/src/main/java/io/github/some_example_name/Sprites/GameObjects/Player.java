package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
//import libraries to allow detection of key inputs and should make movement speed independent of framerate (fingers crossed)
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
//allows player to be rendered
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends GameObject {

    public Player(Texture texture, int x, int y, int width, int height, Game game) {
        super(texture, x, y, width, height, null); //constructer - initialises a new instance of player at specified coords using GameObject
    }

    public void Move(float deltaTime) {
        //calculate speed from delta time which makes it independent of frame rate (so no movement lag, yay)
        float speed = 100 * deltaTime;

        //W move player up
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            setY(getY() + Math.round(speed));
        }

        //S moves player down
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            setY(getY() - Math.round(speed));
        }

        //A moves player left
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            setX(getX() - Math.round(speed));
        }

        //D moves player right
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            setX(getX() + Math.round(speed));
        }
    }

    public void Interact() {

    }
    public void UseItem() {
        
    }

    public void TakeDamage() {

    }

    public void update(float deltaTime) {
        Move(deltaTime);
    }
}