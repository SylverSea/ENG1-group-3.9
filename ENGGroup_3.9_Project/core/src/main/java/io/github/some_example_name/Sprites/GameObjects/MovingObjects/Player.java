package io.github.some_example_name.Sprites.GameObjects.MovingObjects;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Pannels.Menus.Inventory;
import io.github.some_example_name.Sprites.GameObjects.GameObject;

public class Player extends MovingObject {

    /** establishing all 8 directions for animations*/
    public enum Direction { DOWN, UP, LEFT, RIGHT, DOWN_LEFT, DOWN_RIGHT, UP_LEFT, UP_RIGHT }

    private int maxHealth;
    private int currentHealth;
    /** hold whatever player has picked up */
    private Inventory inventory = new Inventory();
    /** implemented to make it so player cant walk through the statue/nest, undecided -adding  */

    /** animation frame duration*/
    private static final float FRAME_DURATION = 0.12f;
    /**maps each direction to its built animation */
    private Map<Direction, Animation<TextureRegion>> walkAnimations = new EnumMap<>(Direction.class);
    /** declares player direction*/
    private Direction facing = Direction.DOWN;
    /** time walking calculated to pick right walking frame*/
    private float stateTime = 0f;
    /** checks if the player has moved in the last frame so we can change sprite to idle frame*/
    private boolean moving = false;
    /** idle frame declared */
    private TextureRegion idleFrame;

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

    public Player(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport, int maxHealth, ArrayList<GameObject> solidObjects) {
        super(texture, x, y, width, height, game, gameViewport, solidObjects);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        idleFrame = new TextureRegion(texture); //builds idle frame from default texture
        buildWalkAnimations();
    }

    /** super awkwar to figure out this was
     *measured walking framed from each direction
     *cut from sprite sheet to get each animation frame
     *
     * @author Erikas Jovaisa
     */

    private static final int[][] CARDINAL_DOWN  = {{23,15,54,83}, {137,15,55,83}, {253,15,56,83}, {376,15,55,83}};
    private static final int[][] CARDINAL_UP    = {{20,107,57,83}, {134,107,58,83}, {257,107,57,83}, {376,107,58,83}};
    private static final int[][] CARDINAL_LEFT  = {{23,199,51,83}, {136,199,53,83}, {258,199,53,83}, {376,199,54,83}, {482,199,52,83}};

    private static final int[][] DIAGONAL_DOWN_LEFT = {{23,15,54,83}, {137,15,55,83}, {253,15,56,83}, {376,15,55,83}};
    private static final int[][] DIAGONAL_UP_LEFT    = {{25,199,52,83}, {136,199,53,83}, {259,199,52,83}, {376,199,52,83}, {482,199,52,83}};

    /**
     *builds all animations from both the sheets
     *
     * @author Erikas Jovaisa
     */
    private void buildWalkAnimations() {
        Texture cardinalSheet = new Texture(Gdx.files.internal("player_walk_cardinal.png"));
        Texture diagonalSheet = new Texture(Gdx.files.internal("player_walk_diagonal.png"));

        // build up down animation
        walkAnimations.put(Direction.DOWN, walkAnimation(cardinalSheet, CARDINAL_DOWN));
        walkAnimations.put(Direction.UP, walkAnimation(cardinalSheet, CARDINAL_UP));

        //Build right from sheet then mirrors for left
        Animation<TextureRegion> cardinalRawAnim = walkAnimation(cardinalSheet, CARDINAL_LEFT);
        walkAnimations.put(Direction.RIGHT, cardinalRawAnim);
        walkAnimations.put(Direction.LEFT, mirroredAnimation(cardinalRawAnim));

        //same mirror for down diagonal
        Animation<TextureRegion> downRawAnim = walkAnimation(diagonalSheet, DIAGONAL_DOWN_LEFT);
        walkAnimations.put(Direction.DOWN_RIGHT, downRawAnim);
        walkAnimations.put(Direction.DOWN_LEFT, mirroredAnimation(downRawAnim));

        //same mirror for up diagonal
        Animation<TextureRegion> upRawAnim = walkAnimation(diagonalSheet, DIAGONAL_UP_LEFT);
        walkAnimations.put(Direction.UP_RIGHT, upRawAnim);
        walkAnimations.put(Direction.UP_LEFT, mirroredAnimation(upRawAnim));
    }

    /**
     * flips every frame of a built anim horizontally to get mirror direction
     * 
     * @author Erikas Jovaisa
     */
    private Animation<TextureRegion> mirroredAnimation(Animation<TextureRegion> source) {
        TextureRegion[] originalFrames = source.getKeyFrames();
        TextureRegion[] flipped = new TextureRegion[originalFrames.length];
        for (int i = 0; i < originalFrames.length; i++) {
            flipped[i] = new TextureRegion(originalFrames[i]);
            flipped[i].flip(true, false); // flips one frame left-right
        }
        return new Animation<>(FRAME_DURATION, flipped);
    }

    /**
     * cuts one animation's frames out of a sheet using the pixel borders
     * @author Erikas Jovaisa
     */
    private Animation<TextureRegion> walkAnimation(Texture sheet, int[][] frameBoxes) {
        TextureRegion[] frames = new TextureRegion[frameBoxes.length];
        for (int i = 0; i < frameBoxes.length; i++) {
            int[] box = frameBoxes[i];
            frames[i] = new TextureRegion(sheet, box[0], box[1], box[2], box[3]);
        }
        return new Animation<>(FRAME_DURATION, frames);
    }

    /**
     * moves the player using delta time which makes it independent of the frame rate so that we hopefully have no movement lag
     *
     * @param deltaTime independent time from libgdx
     * @author James Garfield
     */
    public void Move(float deltaTime) {
        float speed = 200 * deltaTime;

        boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        //W move player up
        if (up){
            float newY = getY() + Math.round(speed);
            if (!collidesAt(getX(), newY)) {
                setY(newY);
            }
        }

        //S moves player down
        if (down){
            float newY = getY() - Math.round(speed);
            if (!collidesAt(getX(), newY)) {
                setY(newY);
            }
        }

        //A moves player left
        if (left){
            float newX = getX() - Math.round(speed);
            if (!collidesAt(newX, getY())) {
                setX(newX);
            }
        }

        //D moves player right
        if (right){
            float newX = getX() + Math.round(speed);
            if (!collidesAt(newX, getY())) {
                setX(newX);
            }
        }

        updateWalkAnimation(deltaTime, up, down, left, right);
    }

    /**
     * work out what dir player faces from last keystroke
     *  advances statetime while moving
     * @author Erikas Jovaisa
     */
    private void updateWalkAnimation(float deltaTime, boolean up, boolean down, boolean left, boolean right) {
        if (up && left) facing = Direction.UP_LEFT;
        else if (up && right) facing = Direction.UP_RIGHT;
        else if (down && left) facing = Direction.DOWN_LEFT;
        else if (down && right) facing = Direction.DOWN_RIGHT;
        else if (up) facing = Direction.UP;
        else if (down) facing = Direction.DOWN;
        else if (left) facing = Direction.LEFT;
        else if (right) facing = Direction.RIGHT;
        //essentially if else to pick diagonals before cardinal dir

        moving = up || down || left || right;
        if (moving) {
            stateTime += deltaTime;
        }
    }


    public void Interact() {

    }

    /**
     * uses an item from inventory by itemID
     *
     * @param itemID
     * @author Erikas Jovaisa
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
     * @author Erikas Jovaisa
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     *updates player position for this frame
     * 
     * @param deltaTime
     * @author Erikas Jovaisa
     */
    @Override
    public void update(float deltaTime) {
        Move(deltaTime);
    }

    /**
     *
     */
    @Override
    public void draw(Batch batch, Vector2 cameraPosition) {
        TextureRegion frame = moving ? walkAnimations.get(facing).getKeyFrame(stateTime, true) : idleFrame;
        batch.draw(frame, getX() - cameraPosition.x, getY() - cameraPosition.y, getWidth(), getHeight());
    }
}
