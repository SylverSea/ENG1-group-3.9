package io.github.some_example_name.Sprites.GameObjects.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class NonWallTile extends Tile {

    private boolean checksForPlayer = false;
    private boolean visible = true; 

    public NonWallTile(Viewport gameViewport) {
        super(gameViewport);
    }
    public NonWallTile(Texture texture, int x, int y, Viewport gameViewport, boolean checksForPlayer) {
        super(texture, x, y, gameViewport);
        this.checksForPlayer = checksForPlayer;
    }

    public void setPlayerChecking(boolean checksForPlayer) {
        this.checksForPlayer = checksForPlayer;
    }
    public boolean getPlayerChecking() {
        return checksForPlayer;
    }

    public void setInvisible() {
        visible = false;
        this.setColor(1f, 1f, 1f, 0f);
    }
    public void setVisible() {
        visible = true;
        this.setColor(1f, 1f, 1f, 1f);
    }

    public boolean isVisible() {
        return visible;
    }
}