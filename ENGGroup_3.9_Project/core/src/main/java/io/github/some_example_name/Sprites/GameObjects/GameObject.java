package io.github.some_example_name.Sprites.GameObjects;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject extends Sprite {
    protected static final int SPRITE_SIZE = 20; // Assuming a fixed sprite size for simplicity
    protected final Game game;

    /**
     * Creates a new GameObject with the specified texture and position.
     *
     * @param texture The texture for the sprite.
     * @param x       The x-coordinate of the sprite.
     * @param y       The y-coordinate of the sprite.
     * @param width   The width of the sprite.
     * @param height  The height of the sprite.
     * @param position The position of the sprite.
     * @author Oliver Quantrill-Scott
     */
    public GameObject(Texture texture, int x, int y, int width, int height, Game game) {
        super(texture, x, y, width, height);
        this.game = game;
    }
    
     /* 
     * Creates a new GameObject with the specified texture and position.
     * 
     * @param texture The texture for the sprite.
     * @param x       The x-coordinate of the sprite.
     * @param y       The y-coordinate of the sprite.
     * @author Oliver Quantrill-Scott
     */
    public GameObject(Texture texture, int x, int y, Game game) {
        this(texture, x, y, texture.getWidth(), texture.getHeight(), game);
    }

    /**
     * Draws the GameObject on the screen, adjusting for the camera position.
     *
     * @param batch          The batch used for drawing.
     * @param cameraPosition The position of the camera to adjust the drawing coordinates.
     * @author Oliver Quantrill-Scott
     */
    public void draw(Batch batch, Vector2 cameraPosition) {
        float[] trueVertices = getVertices();
        for (int i = 0; i < trueVertices.length; i += 2) {
            trueVertices[i] -= cameraPosition.x;
            trueVertices[i + 1] -= cameraPosition.y;
        }
        batch.draw(this.getTexture(), trueVertices, 0, SPRITE_SIZE);
    }

    public abstract void update(float deltaTime);
}