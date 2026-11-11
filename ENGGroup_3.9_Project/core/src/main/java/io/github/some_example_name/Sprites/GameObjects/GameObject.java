package io.github.some_example_name.Sprites.GameObjects;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class GameObject extends Sprite {
    protected final Game game;
    protected final Viewport gameViewport;

    /**
     * Creates a new GameObject with the specified texture and position.
     *
     * @param texture The texture for the object.
     * @param x       The x-coordinate of the object.
     * @param y       The y-coordinate of the object.
     * @param width   The width of the object.
     * @param height  The height of the object.
     * @param game    The game the object exists within.
     * @author Oliver Quantrill-Scott
     * @author James Garfield
     * @author Sky Burrows
     */
    public GameObject(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height);

        this.setPosition(x, y);

        this.gameViewport = gameViewport;
        this.game = game;
    }

    /**
     * 
     * @param x The x-coordinate of the object.
     * @param y The y-coordinate of the object.
     * 
     * @author Sky Burrows
     */
    public void resetPosition(int x, int y) {
        this.setPosition(x, y);
    }
    
    /** 
     * Creates a new GameObject with the specified texture and position.
     * 
     * @param texture The texture for the object.
     * @param x       The x-coordinate of the object.
     * @param y       The y-coordinate of the object.
     * @author Oliver Quantrill-Scott
     */
    public GameObject(Texture texture, int x, int y, Game game, Viewport gameViewport) {
        this(texture, x, y, texture.getWidth(), texture.getHeight(), game, gameViewport);
    }

    /**
     * Returns the vector of the bottom centre of the object.
     * 
     * @return the vector of the bottom centre of the object.
     * @author Oliver Quantrill-Scott
     */
    public Vector2 getRelativePosition() {
        return new Vector2(getX() + getWidth() / 2, getY()); 
    }

    /**
     * Draws the GameObject on the screen, adjusting for the camera position.
     *
     * @param batch          The batch used for drawing.
     * @param cameraPosition The position of the camera to adjust the drawing coordinates.
     * @author Oliver Quantrill-Scott
     */
    public void draw(Batch batch, Vector2 cameraPosition) {
        batch.draw(this.getTexture(), getX() - cameraPosition.x, getY() - cameraPosition.y, getWidth(), getHeight());
    }

    /**
     * Updates the object
     * 
     * @param deltaTime the time between frames
     * @author Oliver Quantrill-Scott
     */
    public abstract void update(float deltaTime);

    /**
     * Disposes the object.
     * 
     * @author Oliver Quantrill-Scott
     */
    public void dispose() {
        getTexture().dispose();
    }
}