package io.github.some_example_name.Sprites.GameObjects;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Goose extends GameObject {
    public static final float RETREAT_TIME = 5f;
    public static final float ACTIVE_RANGE = 100f;
    public static final float ATTACK_RANGE = 5f;
    public static final float SPEED = 100f;
    public static final float RANDOMNESS_FACTOR = 1/64f;

    private float retreatTime = 0f;
    private final Player player;

    private Vector2 direction = new Vector2(0, 0);
    private final Random random = new Random();

    /**
     * Creates a new Goose with specified texture and position.
     * 
     * @param texture The texture for the goose.
     * @param x       The x-coordinate of the goose.
     * @param y       The y-coordinate of the goose.
     * @param width   The width of the goose.
     * @param height  The height of the goose.
     * @param player  The player that the goose will interact with.
     * @param game    The game the goose exists within.
     * @author Oliver Quantrill-Scott
     */
    public Goose(Texture texture, int x, int y, int width, int height, Player player, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
        this.player = player;
    }

    /**
     * Updates the goose and selects which movenment type is appropriate.
     * 
     * @see io.github.some_example_name.Sprites.GameObjects.GameObject#update(float)
     * @author Oliver Quantrill-Scott
     */
    @Override
    public void update(float deltaTime) {
        float relativeDistance = getPosition().dst(player.getPosition());

        if(retreatTime > 0f) {
            retreatTime -= deltaTime;
            retreat(deltaTime);
        } else if (relativeDistance <= ATTACK_RANGE) {
            attack();
        } else if (relativeDistance <= ACTIVE_RANGE) {
            moveActive(deltaTime);
        } else { movePassive(deltaTime); }

        setPosition(getX() + direction.x, getY() + direction.y);

    }

    /**
     * Moves the goose in a random direction.
     * 
     * @param deltaTime independent time from libgdx
     * @author Oliver Quantrill-Scott
     */
    private void movePassive(float deltaTime) {
        direction = direction.add(new Vector2(random.nextFloat() - 0.5f, random.nextFloat() - 0.5f)
                .nor().scl(SPEED * deltaTime * RANDOMNESS_FACTOR));
    }

    /**
     * Moves the goose towards the player.
     * 
     * @param deltaTime independent time from libgdx
     * @author Oliver Quantrill-Scott
     */
    private void moveActive(float deltaTime) {
        direction = player.getPosition().sub(getPosition()).nor().scl(SPEED * deltaTime);
    }

    /**
     * Causes damage to the player and makes the goose retreat.
     * @author Oliver Quantrill-Scott
     */
    private void attack() {
        player.TakeDamage();
        resetRetreat();
    }

    /**
     * Makes the goose retreat.
     * 
     * @author Oliver Quantrill-Scott
     */
    public void resetRetreat() {
        retreatTime = RETREAT_TIME;
    }

    /**
     * Moves the goose away from the player.
     * 
     * @param deltaTime independent time from libgdx
     * @author Oliver Quantrill-Scott
     */
    public void retreat(float deltaTime) {
        direction = getPosition().sub(player.getPosition()).nor().scl(SPEED * deltaTime);
    }
}