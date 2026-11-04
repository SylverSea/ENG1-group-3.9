package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject {

    private final Viewport gameViewport;
    /**
     * Creates a new player with specified texture and position.
     * 
     * @param texture The texture for the player.
     * @param x       The x-coordinate of the player.
     * @param y       The y-coordinate of the player.
     * @param width   The width of the player.
     * @param height  The height of the player.
     * @param game    The game the player exists within.
     * @author James Garfield
     */

    public Player(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport); //constructer - initialises a new instance of player at specified coords using GameObject

        this.gameViewport = gameViewport;
    }
    /**
     * 
     * @param deltaTime independent time from libgdx
     * @author James Garfield
     */
    public void Move(float deltaTime) {
        //calculate speed from delta time which makes it independent of frame rate (so no movement lag)
        float speed = 200 * deltaTime;

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

    @Override
    public void update(float deltaTime) {
        Move(deltaTime);
    }
}