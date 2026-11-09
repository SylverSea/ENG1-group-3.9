package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Statue extends InteractableObject {

    public static final int POINTS = 25;

    public Statue(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport) {
        super(texture, x, y, width, height, game, gameViewport);
    }

    /**gives player bandage when text is used
     *
     * 
     * @param player
     * @author Erikas
     */
    @Override
    protected void onUse(Player player) {
        player.getInventory().addItem("bandage", 1);
    }

    /**
     * returns points awarded for using statue
     * 
     * @return
     * @author Erikas
     */
    @Override
    protected int getPoints() {
        return POINTS;
    }
}
