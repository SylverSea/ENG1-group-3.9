package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.GdxMainGame;

/**
 *base for anything interacted with by E keystroke
 * 
 * @author Erikas
 */
public abstract class InteractableObject extends GameObject {

    public static final int MAX_USES = 3;

    private int useCount = 0;

    /**
     *constructor with most detail that can be added
     * 
     * @param texture
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param gameViewport
     * @author Erikas
     */
    public InteractableObject(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
    }

    /**
     * returns wether object can still be interacted with
     * used for statue and nest
     * 
     * @return
     * @author Erikas
     */
    public boolean canInteract() {
        return useCount < MAX_USES;
    }

    /**
     *calls when user presses e next to this object
     * first 2 uses give points 3rd spawns geese
     * 
     * @param player
     * @author Erikas
     */
    public void interact(Player player) {
        if (!canInteract()) return;

        useCount++;

        if (useCount < MAX_USES) {
            onUse(player);
            GdxMainGame mainGame = (GdxMainGame) game;
            mainGame.addScore(getPoints());
            mainGame.incrementEvents();
        } else {
            ((GdxMainGame) game).spawnGoose(getPosition());
        }
    }

    /**
     * says what a normal use does
     * 
     * @param player
     * @author Erikas
     */
    protected abstract void onUse(Player player);

    /**
     * returns points awarded for normal use 
     * might want to add more points for second use
     * 
     * @return
     * @author Erikas
     */
    protected abstract int getPoints();

    /**
     *update every frame
     * 
     * @param deltaTime
     * @author Erikas
     */
    @Override
    public void update(float deltaTime) {
        //body is empty as just updating every frame
    }
}
