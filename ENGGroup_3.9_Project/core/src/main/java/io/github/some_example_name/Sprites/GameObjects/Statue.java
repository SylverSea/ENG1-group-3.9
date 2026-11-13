package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.GdxMainGame;
import io.github.some_example_name.Sprites.GameObjects.MovingObjects.Player;

public class Statue extends InteractableObject {

    public static final int POINTS = 25;

    /**
     * Generates the statue object
     * 
     * @param texture       The texture of the statue
     * @param x             The x-coordinate of the statue
     * @param y             The y-coordinate of the statue
     * @param width         The width of the statue
     * @param height        The height of the statue
     * @param game          The game
     * @param gameViewport  The game's viewport
     */

    public Statue(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
    }

    /**
     *calls when user presses e next to this object
     * first 2 uses give points 3rd spawns geese
     * 
     * @param player The player
     * @author Erikas Jovaisa
     */
    @Override
    public void interact(Player player) {
        if (!canInteract()) return;

        useCount++;

        if (useCount < MAX_USES) {
            onUse(player);
            GdxMainGame mainGame = (GdxMainGame) game;
            mainGame.addScore(getPoints());
            mainGame.incrementEvents();
        } else {
            ((GdxMainGame) game).spawnGoose(getRelativePosition()); //spawn goose on 3rd use
        }
    }

    /**gives player bandage when text is used
     * 
     * @param player the player
     * @author Erikas
     */
    @Override
    protected void onUse(Player player) {
        player.getInventory().addItem("bandage", 1);
    }

    /**
     * returns points awarded for using statue
     * 
     * @return points awarded for using statue
     * @author Erikas
     */
    @Override
    protected int getPoints() {
        return POINTS;
    }
}
