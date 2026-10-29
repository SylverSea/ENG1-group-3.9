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
        super(texture, x, y, width, height, game); //constructer - initialises a new instance of player at specified coords using GameObject
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
    //draw meathod allows player to draw itself
    public void draw(SpriteBatch batch) {
        batch.draw(playerTexture, x, y);
    }
    //deletes player texture when game closes to save memory
    public void dispose() {
        playerTexture.dispose();
    }

    public void Interact() {

    }
    public void UseItem() {
        
    }

    public void TakeDamage() {

    }

    @Override
    public void update(float deltaTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}