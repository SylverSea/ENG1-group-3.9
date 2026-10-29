package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class NonWallTile extends Tile {

    private boolean checksForPlayer = false;

    /**
     * Constructor for Tile, with the least detail that can be added.
     * 
     * @param gameViewport The game's viewport.
     * 
     * @author Sky Burrows
     */
    public NonWallTile(Viewport gameViewport) {
        super(gameViewport);
    }
    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture         - The NonWallTile's texture.
     * @param x               - The NonWallTile's x-coordinate.
     * @param y               - The NonWallTile's y-coordinate.
     * @param gameViewport    - The game's viewport.
     * @param checksForPlayer - Sets whether this is a detecting NonWallTIle or not.
     * 
     * @author Sky Burrows
     */
    public NonWallTile(Texture texture, int x, int y, Viewport gameViewport, boolean checksForPlayer) {
        super(texture, x, y, gameViewport);
        this.checksForPlayer = checksForPlayer;
    }

    //Getters and setters
    public void setPlayerChecking(boolean checksForPlayer) {
        this.checksForPlayer = checksForPlayer;
    }
    public boolean getPlayerChecking() {
        return checksForPlayer;
    }
}