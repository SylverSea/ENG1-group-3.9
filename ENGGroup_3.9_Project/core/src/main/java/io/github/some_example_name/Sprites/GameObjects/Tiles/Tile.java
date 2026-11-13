package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Sprites.GameObjects.GameObject;

public class Tile extends GameObject {

    protected final Texture texture;
    protected boolean visible = true;

    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture         - The Tile's texture.
     * @param x               - The Tile's x-coordinate.
     * @param y               - The Tile's y-coordinate.
     * @param width           - The Tile's width.
     * @param height          - The Tile's height.
     * @param checksForPlayer - Sets whether this is a detecting Tile or not.
     * @param game            - The game.
     * @param gameViewport    - The game's viewport.
     * 
     * @author Sky Burrows
     */
    public Tile(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
        
        this.texture = texture;
    }

    /**
     * Returns whether the tile is visible
     * @return whether the tile is visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the tile to be invisible.
     */
    public void setInvisible() {
        visible = false;
        this.setColor(1f, 1f, 1f, 0f);
    }
    /**
     * Sets the tile to be visible.
     */
    public void setVisible() {
        visible = true;
        this.setColor(1f, 1f, 1f, 1f);
    }

    /**    (non-Javadoc)
     * 
     * @see io.github.some_example_name.Sprites.GameObjects.GameObject#update(float)
     */
    @Override
    public void update(float deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /** If visible, draws tile
     * 
     * @see com.badlogic.gdx.graphics.g2d.Sprite#draw(com.badlogic.gdx.graphics.g2d.Batch)
     * @author Oliver Quantrill-Scott
     */
    @Override 
    public void draw(Batch batch, Vector2 position) {
        if (visible) {
            super.draw(batch, position);
        }
    }
    /**    (non-Javadoc)
     * 
     * @see com.badlogic.gdx.graphics.g2d.Sprite#draw(com.badlogic.gdx.graphics.g2d.Batch)
     */
    public void draw(Batch batch) {
        if (visible) {
            super.draw(batch);
        }
    }
}