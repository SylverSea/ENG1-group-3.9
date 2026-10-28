package io.github.some_example_name.Sprites.GameObjects;
<<<<<<< Updated upstream
import io.github.some_example_name.Sprites.Sprite;
=======
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
>>>>>>> Stashed changes

public class GameObject extends Sprite {
    private static final int SPRITE_SIZE = 20; // Assuming a fixed sprite size for simplicity

<<<<<<< Updated upstream
    //declares X and Y coordinates for all game objects
    protected float x;
    protected float y;

    //GameObject constructer, takes the starting x and y coords as peramiters and spawns a new game object at those coords
    public GameObject(float StartX, float StartY) {
        this.x = StartX;
        this.y = StartY;
    }

    //Default constructer in case we want to spawn a new game object without knowing its coords
    public GameObject() {
        this.x = 0;
        this.y = 0;
    }

    //getters and setters for GameObject so other classes can access x and y coords
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
=======
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
    public GameObject(Texture texture, int x, int y, int width, int height, Vector2 position) {
        super(texture, x, y, width, height);
    }
    /* Creates a new GameObject with the specified texture and position.
     * @param texture The texture for the sprite.
     * @param x       The x-coordinate of the sprite.
     * @param y       The y-coordinate of the sprite.
     * @author Oliver Quantrill-Scott
     */
    public GameObject(Texture texture, int x, int y) {
        super(texture, x, y, texture.getWidth(), texture.getHeight());
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
>>>>>>> Stashed changes
    }
}