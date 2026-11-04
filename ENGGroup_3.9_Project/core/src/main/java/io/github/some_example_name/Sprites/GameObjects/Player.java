package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject {

    private int maxHealth;
    private int currentHealth;

    /**
     * constructer - creates a new instance of player at the starting coordinates, texture and dimentions
     * 
     * @param texture The texture for the player.
     * @param x       The x-coordinate of the player.
     * @param y       The y-coordinate of the player.
     * @param width   The width of the player.
     * @param height  The height of the player.
     * @param game    The game the player exists within.
     * @param maxHealth The max health the player starts with
     * @author James Garfield
     */

    public Player(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport, int maxHealth) {
        super(texture, x, y, width, height, game, gameViewport); 
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }
    /**
     * moves the player using delta time which makes it independent of the frame rate so that we hopefully have no movement lag
     * 
     * @param deltaTime independent time from libgdx
     * @author James Garfield
     */
    public void Move(float deltaTime) {
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
     
    /**
     * makes the player take damage/ lose health
     * 
     * @param amount The amount of damage taken by player
     * @author James Garfield
     */
    public void TakeDamage(int amount) {
        currentHealth = Math.max(0, currentHealth - amount);

        if (currentHealth == 0) {
            //I need to add what happens when player "dies" 
        }
    }

    /**
     * Heals the player up to max health but not beyond it
     * 
     * @param amount The amount the player will be healed by
     * @author James Garfield
     */
    public void Heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void update(float deltaTime) {
        Move(deltaTime);
    }
}