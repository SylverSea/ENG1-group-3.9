package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.GdxMainGame;
import io.github.some_example_name.Sprites.GameObjects.MovingObjects.Player;

public class Nest extends InteractableObject {

    public static final int POINTS = 10;

    /**
     * Generates the nest object
     * 
     * @param texture       The texture of the nest
     * @param x             The x-coordinate of the nest
     * @param y             The y-coordinate of the nest
     * @param width         The width of the nest
     * @param height        The height of the nest
     * @param game          The game
     * @param gameViewport  The game's viewport
     */
    public Nest(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
    }

    /**
     *consumes one egg from the player's inventory when the nest is used -
     *does nothing if the player has none (same safe no-op pattern as Player.UseItem)
     *
     * @param player The player
     * @author Erikas Jovaisa
     */
    @Override
    protected void onUse(Player player) {
        player.getInventory().useItem("egg");
    }

    /**
     * returns points awarded for using nest
     * 
     * @return points awarded for using nest
     * @author Erikas Jovaisa
     */
    @Override
    protected int getPoints() {
        return POINTS;
    }

    /**
     * overrides the base interact so the egg is still consumed on the 3rd use (the one
     * that spawns the goose instead of giving points) - the first 2 uses behave exactly
     * as the base class's interact()
     *
     * @param player The player
     * @author Erikas Jovaisa
     */
    @Override
    public void interact(Player player) {
        if (!canInteract()) return;

        useCount++;
        onUse(player); //consumes an egg every use, including the 3rd

        if (useCount < MAX_USES) {
            GdxMainGame mainGame = (GdxMainGame) game;
            mainGame.addScore(getPoints());
            mainGame.incrementEvents();
        } else {
            ((GdxMainGame) game).spawnGoose(getRelativePosition()); //spawn goose on 3rd use
        }
    }
}
