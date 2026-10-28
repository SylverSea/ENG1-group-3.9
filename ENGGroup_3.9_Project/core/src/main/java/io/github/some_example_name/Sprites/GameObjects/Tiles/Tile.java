package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Sprites.GameObjects.GameObject;

public class Tile extends GameObject {

    private static final float SCALE = 1 / 32f;

    protected final Texture texture;
    protected final int xCoord;
    protected final int yCoord;

    private final Viewport gameViewport; 

    /**
     * Constructor for Tile, with the least detail that can be added.
     * 
     * @param gameViewport The game's viewport.
     * 
     * @author Sky Burrows
     */
    public Tile(Viewport gameViewport) {
        super();

        xCoord = 0;
        yCoord = 0;
        texture = null;
        this.gameViewport = gameViewport;
    }
    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture      - The Tile's texture.
     * @param x            - The Tile's x-coordinate.
     * @param y            - The Tile's y-coordinate
     * @param gameViewport - The game's viewport.
     * @param position     - The Tile's Vector position.
     * 
     * @author Sky Burrows
     */
    public Tile(Texture texture, int x, int y, Viewport gameViewport) {
        super(texture, x, y);

        xCoord = x;
        yCoord = y;
        this.texture = texture;
        this.gameViewport = gameViewport;
    }

    //getters and setters for the basic class info
    public float getX() {
        return xCoord;
    }
    public float getY() {
        return yCoord;
    }
}