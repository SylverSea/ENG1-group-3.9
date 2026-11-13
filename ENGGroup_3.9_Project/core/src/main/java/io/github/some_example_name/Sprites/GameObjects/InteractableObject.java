package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Sprites.GameObjects.MovingObjects.Player;

/**
 *base for anything interacted with by E keystroke
 * 
 * @author Erikas Jovaisa
 */
public abstract class InteractableObject extends GameObject {

    public static final int MAX_USES = 3;

    /** protected (not private) so a subclass like Nest can override interact() and still track its own use count */
    protected int useCount = 0;

    /**
     *constructor with most detail that can be added
     * 
     * @param texture       The texture for the object.
     * @param x             The x-coordinate of the object.
     * @param y             The y-coordinate of the object.
     * @param width         The width of the object.
     * @param height        The height of the object.
     * @param game          The game the object exists within.
     * @param gameViewport  The viewport of the game
     * @author Erikas Jovaisa
     */
    public InteractableObject(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
    }

    /**
     * returns whether object can still be interacted with
     * used for statue and nest
     * 
     * @return whether object can still be interacted with
     * @author Erikas Jovaisa
     */
    public boolean canInteract() {
        return useCount < MAX_USES;
    }

    public abstract void interact(Player player);

    /**
     * says what a normal use does
     * 
     * @param player    The player interacting with the object
     * @author Erikas Jovaisa
     */
    protected abstract void onUse(Player player);

    /**
     * returns points awarded for normal use 
     * might want to add more points for second use
     * 
     * @return points awarded for normal use
     * @author Erikas Jovaisa
     */
    protected abstract int getPoints();

    /**
     * update every frame
     * 
     * @param deltaTime the time between frames
     * @author Erikas Jovaisa
     */
    @Override
    public void update(float deltaTime) {
        //body is empty as just updating every frame
    }
}
