package io.github.some_example_name.Sprites.GameObjects.MovingObjects;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Sprites.GameObjects.GameObject;

public class Goose extends MovingObject {
    public static final float RETREAT_TIME = 5f;
    public static final float ACTIVE_RANGE = 200f;
    public static final float ATTACK_RANGE = 5f;
    public static final float SPEED = 100f;
    public static final float RANDOMNESS_FACTOR = 1/64f;

    private float retreatTime = 0f;
    private final Player player;

    private Vector2 direction = new Vector2(0, 0);
    private final Random random = new Random();

    /** direction decision*/
    private enum Direction { DOWN, UP, LEFT, RIGHT }
    /** how long animation shoud play */
    private static final float FRAME_DURATION = 0.12f;
    /** walk animation map*/
    private Map<Direction, Animation<TextureRegion>> walkAnimations = new EnumMap<>(Direction.class);
    /** value for facing direction */
    private Direction facing = Direction.DOWN;
    /** time value for direction time */
    private float stateTime = 0f;
    /** value for frame when idle */
    private TextureRegion idleFrame;

    /** manually boxing frames */
    private static final int[][] GOOSE_DOWN  = {{0,0,167,93}, {167,0,167,93}, {334,0,167,93}, {501,0,167,93}};
    private static final int[][] GOOSE_UP    = {{0,93,167,93}, {167,93,167,93}, {334,93,167,93}, {501,93,167,93}};
    private static final int[][] GOOSE_LEFT  = {{0,186,167,93}, {167,186,167,93}, {334,186,167,93}, {501,186,167,93}};



    private static final int[][] GOOSE_RIGHT = {{0,279,167,93}, {167,279,167,93}, {334,279,167,93}, {501,279,167,93}};

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
    public Goose(Texture texture, int x, int y, int width, int height, Player player, Game game, Viewport gameViewport, ArrayList<GameObject> solidObjects) {
        super(texture, x, y, width, height, game, gameViewport, solidObjects);
        this.player = player;
        idleFrame = new TextureRegion(texture); //builds idle frame and walk animations in constructor
        buildWalkAnimations();
    }

    /** cuts the walk animations only 4 as opposed to players 8 */
    private void buildWalkAnimations() {
        Texture walkSheet = new Texture(Gdx.files.internal("goose_walk.png"));
        walkAnimations.put(Direction.DOWN, walkAnimation(walkSheet, GOOSE_DOWN));
        walkAnimations.put(Direction.UP, walkAnimation(walkSheet, GOOSE_UP));
        walkAnimations.put(Direction.LEFT, walkAnimation(walkSheet, GOOSE_LEFT));
        walkAnimations.put(Direction.RIGHT, walkAnimation(walkSheet, GOOSE_RIGHT));
    }

    /** slices animation out of a sheet from list of pixel boxes*/
    private Animation<TextureRegion> walkAnimation(Texture sheet, int[][] frameBoxes) {
        TextureRegion[] frames = new TextureRegion[frameBoxes.length];
        for (int i = 0; i < frameBoxes.length; i++) {
            int[] box = frameBoxes[i];
            frames[i] = new TextureRegion(sheet, box[0], box[1], box[2], box[3]);
        }
        return new Animation<>(FRAME_DURATION, frames);
    }

    /** workout out which direction the duck faces in and advances time while the duck moves to play animation */
    private void updateWalkAnimation(float deltaTime) {
        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            facing = direction.x > 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (direction.y != 0) {
            facing = direction.y > 0 ? Direction.UP : Direction.DOWN;
        }

        if (direction.len2() > 0.0001f) {
            stateTime += deltaTime;
        }
    }

    /** plays walk animation for current direction or idle frame if not moving */
    @Override
    public void draw(Batch batch, Vector2 cameraPosition) {
        boolean moving = direction.len2() > 0.0001f;
        TextureRegion frame = moving ? walkAnimations.get(facing).getKeyFrame(stateTime, true) : idleFrame;
        batch.draw(frame, getX() - cameraPosition.x, getY() - cameraPosition.y, getWidth(), getHeight());
    }

    /**
     * Updates the goose and selects which movenment type is appropriate.
     *
     * @see io.github.some_example_name.Sprites.GameObjects.GameObject#update(float)
     * @author Oliver Quantrill-Scott
     */
    @Override
    public void update(float deltaTime) {
        float relativeDistance = getRelativePosition().dst(player.getRelativePosition());

        if(retreatTime > 0f) {
            retreatTime -= deltaTime;
            retreat();
        } else if (relativeDistance <= ATTACK_RANGE) {
            attack();
        } else if (relativeDistance <= ACTIVE_RANGE) {
            moveActive();
        } else { movePassive(); }

        updateWalkAnimation(deltaTime); //updated walk animation once per frame

        Vector2 newPosition = new Vector2(getX(), getY()).add(direction.scl(SPEED * deltaTime));

        if(collidesAt(newPosition.x, newPosition.y)) {
            if (collidesAt(getX(), newPosition.y)) {
                newPosition.y = getY();
            }
            if (collidesAt(newPosition.x, getY())) {
                newPosition.x = getX();
            }
        }

        setPosition(newPosition.x, newPosition.y);

    }

    /**
     * Moves the goose in a random direction.
     *
     * @param deltaTime independent time from libgdx
     * @author Oliver Quantrill-Scott
     */
    private void movePassive() {
        direction = direction.add(new Vector2(random.nextFloat() - 0.5f, random.nextFloat() - 0.5f).nor().scl(RANDOMNESS_FACTOR)).nor();
    }

    /**
     * Moves the goose towards the player.
     *
     * @param deltaTime independent time from libgdx
     * @author Oliver Quantrill-Scott
     */
    private void moveActive() {
        direction = player.getRelativePosition().sub(getRelativePosition()).nor();
    }

    /**
     * Causes damage to the player and makes the goose retreat.
     * @author Oliver Quantrill-Scott
     */
    private void attack() {
        player.TakeDamage(1);
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
    public void retreat() {
        direction = getRelativePosition().sub(player.getRelativePosition()).nor();
    }
}
