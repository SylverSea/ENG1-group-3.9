package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector2;


public class MapItem extends GameObject {

    private static final float SCALE = 1 / 64f;

    private final Texture texture;
    private final Viewport gameViewport; 
    private final String itemID;

    private boolean visible = true;
    private int xCoord;
    private int yCoord;

    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture      - The Tile's texture.
     * @param x            - The MapItem's x-coordinate.
     * @param y            - The MapItem's y-coordinate.
     * @param gameViewport - The game's viewport.
     * @param position     - The MapItem's position
     */
    public MapItem(String itemID, Texture texture, int x, int y, Viewport gameViewport, Vector2 position, Game game) {
        super(texture, x, y, game);

        this.texture = texture;
        this.gameViewport = gameViewport;
        xCoord = x;
        yCoord = y;
        this.itemID = itemID;
    }

    //Getters and setters
    public void setX(int x) {
        xCoord = x;
    }
    public int getX(int x) {
        return xCoord;
    }
    public void setY(int y) {
        yCoord = y;
    }
    public int getY(int y) {
        return yCoord;
    }
    public boolean isVisible() {
        return visible;
    }
    public String getItemID() {
        return itemID;
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

    //despawns item from the map, returns the item
    public MapItem pickUp() {
        setInvisible();

        return this;
    }
    //Sets the item to visible, sets the x and y coordinates of the drop
    public void drop(int x, int y) {
        setVisible();

        xCoord = x;
        yCoord = y;
    }
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}
}