package io.github.some_example_name.Pannels.Menus;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.github.some_example_name.Pannels.Button;

public class PauseMenu extends Menu {

    // The batch used to draw the menu's images and text to the screen
    private SpriteBatch batch;

    // The camera, used to position the menu relative to what the player sees. Makes menu fixed on screen.
    private OrthographicCamera camera;

    //the PLAY/RESUME button - same button does both jobs, only the label changes

    private Button playButton = new Button(360, 220, 80, 30);
    private BitmapFont font = new BitmapFont();

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
    }

    /**
     * draws play button
     *
     * @author Erikas
     */
    public void draw() {
        playButton.draw(batch, "PLAY", font); 
    }

    /**
     *checks wether player has clicked button
     * 
     * @return
     * @author Erikas
     */
    public boolean isPlayButtonClicked() {
        if (!Gdx.input.justTouched()) return false;
        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch); 
        return playButton.isClicked(touch.x, touch.y);
    }
}
