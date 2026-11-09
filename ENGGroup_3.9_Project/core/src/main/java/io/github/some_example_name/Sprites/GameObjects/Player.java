package io.github.some_example_name.Sprites.GameObjects;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Pannels.Menus.Inventory;

public class Player extends GameObject {

    private int maxHealth;
    private int currentHealth;
    /** hold whatever player has picked up */
    private Inventory inventory = new Inventory();
    /** implemented to make it so player cant walk through the statue/nest, undecided -adding  */
    private ArrayList<InteractableObject> solidObjects = new ArrayList<>();

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
            float newY = getY() + Math.round(speed);
            if (!collidesAt(getX(), newY)) {
                setY(newY);
            }
        }

        //S moves player down
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            float newY = getY() - Math.round(speed);
            if (!collidesAt(getX(), newY)) {
                setY(newY);
            }
        }

        //A moves player left
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            float newX = getX() - Math.round(speed);
            if (!collidesAt(newX, getY())) {
                setX(newX);
            }
        }

        //D moves player right
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            float newX = getX() + Math.round(speed);
            if (!collidesAt(newX, getY())) {
                setX(newX);
            }
        }
    }

    /**list of objects the player cant walk through -decding
     *
     * @param solidObjects
     * @author Erikas
     */
    public void setSolidObjects(ArrayList<InteractableObject> solidObjects) {
        this.solidObjects = solidObjects;
    }

    /**checks wether position would overlap object
     *
     * @param x
     * @param y
     * @return
     * @author Erikas
     */
    private boolean collidesAt(float x, float y) {
        Rectangle testBounds = new Rectangle(x, y, getWidth(), getHeight());
        for (InteractableObject obj : solidObjects) {
            if (testBounds.overlaps(obj.getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }

    public void Interact() {

    }

    /**
     * uses an item from inventory by itemID
     *
     * @param itemID
     * @author Erikas
     */
    public void UseItem(String itemID) {
        if (!inventory.useItem(itemID)) return; 

        if (itemID.equals("bandage")) {
            Heal(25);
        }
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

    /**
     * returns players inventory to check if item is held
     * 
     * @return
     * @author Erikas
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     *updates player position for this frame
     * 
     * @param deltaTime
     * @author Erikas
     */
    @Override
    public void update(float deltaTime) {
        Move(deltaTime);
    }
}
