package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

public class Goose extends GameObject {
    public static final float RETREAT_TIME = 5f;
    public static final float ACTIVE_RANGE = 10f;
    public static final float ATTACK_RANGE = 1f;

    private float retreatTime = 0f;
    private final Player player;

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
    public Goose(Texture texture, int x, int y, int width, int height, Player player, Game game) {
        super(texture, x, y, width, height, game);
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
            retreat();
        } else if (relativeDistance <= ATTACK_RANGE) {
            attack();
        } else if (relativeDistance <= ACTIVE_RANGE) {
            moveActive();
        } else { movePassive(); }
    }

    private void movePassive() {
    }

    private void moveActive() {
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
     * @author Oliver Quantrill-Scott
     */
    public void resetRetreat() {
        retreatTime = RETREAT_TIME;
    }

    public void retreat() {
    }
}