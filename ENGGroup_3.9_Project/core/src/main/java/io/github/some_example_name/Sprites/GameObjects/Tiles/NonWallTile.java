package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class NonWallTile extends Tile {

    private boolean checksForPlayer = false;

    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture         - The NonWallTile's texture.
     * @param x               - The NonWallTile's x-coordinate.
     * @param y               - The NonWallTile's y-coordinate.
     * @param width           - The NonWallTile's width.
     * @param height          - The NonWallTile's height.
     * @param checksForPlayer - Sets whether this is a detecting NonWallTile or not.
     * @param game            - The game.
     * @param gameViewport    - The game's viewport.
     * 
     * @author Sky Burrows
     */
    public NonWallTile(Texture texture, int x, int y, int width, int height, boolean checksForPlayer, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);

        this.checksForPlayer = checksForPlayer;
    }

    /**
     * Sets the check for the player
     * @param checksForPlayer the check for the player
     */
    public void setPlayerChecking(boolean checksForPlayer) {
        this.checksForPlayer = checksForPlayer;
    }
    /**
     * Returns the check for the player
     * @return the check for the player
     */
    public boolean getPlayerChecking() {
        return checksForPlayer;
    }
}