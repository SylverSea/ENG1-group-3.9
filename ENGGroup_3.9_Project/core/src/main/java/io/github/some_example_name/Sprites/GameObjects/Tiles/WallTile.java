package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WallTile extends Tile {

    public boolean hasCollision = true;

    /**
     * Constructor for Tile, with the least detail that can be added.
     * 
     * @param gameViewport The game's viewport.
     * 
     * @author Sky Burrows
     */
    public WallTile(Viewport gameViewport) {
        super(gameViewport);   
    }
    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture         - The NonWallTile's texture.
     * @param x               - The NonWallTile's x-coordinate.
     * @param y               - The NonWallTile's y-coordinate.
     * @param gameViewport    - The game's viewport.
     * 
     * @author Sky Burrows
     */
    public WallTile(Texture texture, int x, int y, Viewport gameViewport) {
        super(texture, x, y, gameViewport);
    }

    /**    
     * Makes Tile not rendered and removes collision, essentially removing it from the map.
     * 
     * @author Sky Burrows
     */
    public void deactivate() {
        hasCollision = false;
        visible = false;
        this.setColor(1f, 1f, 1f, 0f);
    }

    /**    
     * Makes Tile  rendered and adds collision.
     * 
     * @author Sky Burrows
     */
    public void activate() {
        hasCollision = true;
        visible = true;
        this.setColor(1f, 1f, 1f, 1f);
    }
}