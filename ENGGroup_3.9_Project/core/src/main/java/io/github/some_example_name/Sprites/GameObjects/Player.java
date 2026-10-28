package io.github.some_example_name.Sprites.GameObjects;

//import libraries to allow detection of key inputs and should make movement speed independent of framerate (fingers crossed)
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends GameObject {

    public Player(float startX, float startY) {
        super(startX, startY);//constructer - initialises a new instance of player at specified coords using GameObject
    }

    public void Move() {
        //calculate speed from delta time which makes it independent of frame rate (so no movement lag, yay)
        float speed = 100f * Gdx.graphics.getDeltaTime();

        //W move player up
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            y += speed;
        }

        //S moves player down
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= speed;
        }

        //A moves player left
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= speed;
        }

        //D moves player right
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            x += speed;
        }


    }
    public void Interact() {

    }
    public void UseItem() {
        
    }

    public void TakeDamage() {

    }
}