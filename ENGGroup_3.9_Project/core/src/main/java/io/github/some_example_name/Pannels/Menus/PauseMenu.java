package io.github.some_example_name.Pannels.Menus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.github.some_example_name.Pannels.Button;

public class PauseMenu extends Menu {

    /** The batch used to draw the menu's images and text to the screen */
    private SpriteBatch batch;

    /** The camera, used to position the menu relative to what the player sees. Makes menu fixed on screen. */
    private OrthographicCamera camera;

    /**the resume button - starting the game is now handled by the separate title screen (StartMenu) */
    private Button playButton = new Button(360, 220, 80, 30);
    private BitmapFont font = new BitmapFont();

    /** semi-transparent black overlay - dims the game behind the pause menu so RESUME stands out */
    private Texture dimOverlay;

    /**
     * Constructor for the PauseMenu class.
     *
     * @param batch  The SpriteBatch used to draw the menu's images and text to the screen.
     * @param camera The OrthographicCamera used to position the menu relative to what the player sees.
     *
     * @author Oliver Lockett
     */

    public PauseMenu(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;

        //builds a single black pixel, stretched full-screen and drawn semi-transparent to dim the paused game
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0f, 0f, 0f, 0.6f);
        pixmap.fill();
        dimOverlay = new Texture(pixmap);
        pixmap.dispose(); //frees pixmap once texture is built
    }

    /**
     * draws resume button - this menu only ever shows after the game
     * has already started (the title screen has its own start button)
     *
     * @author Erikas Jovaisa
     */
    public void draw(float scale) {
        playButton.setScale(scale);
        font.getData().setScale(scale);
        batch.draw(dimOverlay, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //dims the whole screen, not just a fixed 640x480 area
        playButton.draw(batch, "RESUME", font);
    }

    /**
     *checks wether player has clicked button
     * 
     * @return
     * @author Erikas Jovaisa
     */
    public boolean isPlayButtonClicked() {
        if (!Gdx.input.justTouched()) return false;
        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch); 
        return playButton.isClicked(touch.x, touch.y);
    }
}
