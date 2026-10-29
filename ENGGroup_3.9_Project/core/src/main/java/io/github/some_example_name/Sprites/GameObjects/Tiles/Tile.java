package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Sprites.GameObjects.GameObject;

public class Tile extends GameObject {

    private static final float SCALE = 1 / 32f;

    private final Viewport gameViewport; 

    protected final Texture texture;
    protected final int xCoord;
    protected final int yCoord;
    protected boolean visible = true;

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
    public Tile(Texture texture, int x, int y, Viewport gameViewport, Game game) {
        super(texture, x, y, game);

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
    public boolean isVisible() {
        return visible;
    }

    //Changes the tile's visibility.
    public void setInvisible() {
        visible = false;
        this.setColor(1f, 1f, 1f, 0f);
    }
    public void setVisible() {
        visible = true;
        this.setColor(1f, 1f, 1f, 1f);
    }

    @Override
    public void update(float deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}