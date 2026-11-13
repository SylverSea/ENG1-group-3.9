package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MapItem extends GameObject {

    private final String itemID;

    private boolean visible = true;

    /**
     * Constructor for Tile, with the most detail that can be added.
     * 
     * @param texture      - The MapItem's texture.
     * @param x            - The MapItem's x-coordinate.
     * @param y            - The MapItem's y-coordinate.
     * @param gameViewport - The game's viewport.
     * @param position     - The MapItem's position
     */
    public MapItem(String itemID, int x, int y, Game game, Viewport gameViewport) throws IllegalArgumentException {
        super(new Texture(Gdx.files.internal(String.format(itemID+".png"))), x, y, game, gameViewport);

        this.itemID = itemID;

        String[] knownIDs = {"Bandage", "Egg"};
        boolean containsValidID = false;

        for(String ID : knownIDs) {
            if (ID == itemID) {containsValidID = true;}
        }

        if (!containsValidID) {throw new IllegalArgumentException(itemID+" is not a recognised item ID");}
    }

    /**
     * 
     * @param texture      - The MapItem's texture.
     * @param x            - The MapItem's x-coordinate.
     * @param y            - The MapItem's y-coordinate.
     * @param width        - The MapItem's width.
     * @param height       - The MapItem's height.
     * @param gameViewport - The game's viewport.
     * @param position     - The MapItem's position
     * @throws IllegalArgumentException
     */
    public MapItem(String itemID, int x, int y, int width, int height, Game game, Viewport gameViewport) throws IllegalArgumentException {
        super(new Texture(Gdx.files.internal(String.format(itemID+".png"))), x, y, width, height, game, gameViewport);

        this.itemID = itemID;

        String[] knownIDs = {"Bandage", "Egg"};
        boolean containsValidID = false;

        for(String ID : knownIDs) {
            if (ID == itemID) {containsValidID = true;}
        }

        if (!containsValidID) {throw new IllegalArgumentException(itemID+" is not a recognised item ID");}
    }

    /**
     * Returns whether the item is visible
     * 
     * @return whether the item is visible
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * Returns the item's ID
     * @return the item's ID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Sets the item to be invisible.
     */
    public void setInvisible() {
        visible = false;
        this.setColor(1f, 1f, 1f, 0f);
    }
    /**
     * Sets the item to be visible.
     */
    public void setVisible() {
        visible = true;
        this.setColor(1f, 1f, 1f, 1f);
    }

    /**
     * Despawns item from the map, returns the item
     * 
     * @return this MapItem
     */
    public MapItem pickUp() {
        setInvisible();

        return this;
    }
    /**
     * Sets the item to visible, sets the x and y coordinates of the drop
     * 
     * @param x x-coordinate of the drop
     * @param y y-coordinate of the drop
     */
    public void drop(int x, int y) {
        setVisible();

        setX(x);
        setY(y);
    }
    /**    (non-Javadoc)
     * @see io.github.some_example_name.Sprites.GameObjects.GameObject#update(float)
     */
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}
}