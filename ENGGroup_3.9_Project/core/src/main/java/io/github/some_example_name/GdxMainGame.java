package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.some_example_name.Pannels.Menus.PauseMenu;

import io.github.some_example_name.Sprites.GameObjects.Player;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GdxMainGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //initialize the pause menu
        pauseMenu = new PauseMenu(batch, camera);

        //spawns in the player
        player = new Player(400f, 240f);
    }

    @Override
    public void render() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused; // Toggle pause state when escape key is pressed
        }
        
        if (!isPaused) {
            player.Move(); // Update player movement only when the game is not paused
        }

        //locks the camera onto the players coordinates so that the camera is always over the player
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        //add teh camera to the batch projection matrix
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); //clears the screen before drawing loop
        batch.begin();//starts rendering from here

        //all drawing code needs to go in here
        player.draw(batch);

        if (isPaused) {
            pauseMenu.draw(); // Draw the pause menu when the game is paused
        }
        
        batch.end(); //ends rendering from here
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }

    
}
