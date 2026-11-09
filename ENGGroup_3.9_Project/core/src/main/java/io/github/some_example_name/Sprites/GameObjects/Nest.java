package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Nest extends InteractableObject {

    public static final int POINTS = 10;

    public Nest(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
    }

    /**
     *gives player egg when nest is used
     
     * @param player
     * @author Erikas
     */
    @Override
    protected void onUse(Player player) {
        player.getInventory().addItem("egg", 1);
    }

    /**
     * returns points awarded for using nest
     * 
     * @return
     * @author Erikas
     */
    @Override
    protected int getPoints() {
        return POINTS;
    }
}
