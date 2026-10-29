package io.github.some_example_name.Pannels.Menus;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenu extends Menu {

    // The batch used to draw the menu's images and text to the screen
    private SpriteBatch batch;

    // The camera, used to position the menu relative to what the player sees. Makes menu fixed on screen.
    private OrthographicCamera camera;

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

    public void draw() {
       //TODO: Implement the drawing for the pause menu using the batch and camera.
       //Can't do it right now as I cant test to position the menu correctly without the game running, won't run until errors solved in other classes --> specifically the GameObject class.  
    }
}