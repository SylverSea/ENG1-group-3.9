package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.some_example_name.Pannels.Menus.PauseMenu;

import io.github.some_example_name.Sprites.GameObjects.Player;
import io.github.some_example_name.Sprites.GameObjects.Tiles.NonWallTile;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GdxMainGame extends Game {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private boolean isPaused = false;
    private PauseMenu pauseMenu;
    private Viewport gameViewport = new ExtendViewport(400, 400);
    private NonWallTile tile1;
    private NonWallTile tile2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //initialize the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        camera.position.set(400, 240, 0);  //test code delete later, currently just spawns the camera in a starting position to be sure we could render player
        camera.update();                          //test
        
        //initialize the pause menu
        pauseMenu = new PauseMenu(batch, camera);

        //spawns in the player
        player = new Player(
            new Texture(Gdx.files.internal(String.format("player.png"))),
            0, 40,
            50, 40,
            this, gameViewport
        );

        tile1 = new NonWallTile(
            new Texture(Gdx.files.internal(String.format("tile.png"))),
            0, 0,
            50, 40,
            false,
            this, gameViewport);
        tile2 = new NonWallTile(
            new Texture(Gdx.files.internal(String.format("tile.png"))),
            50, 0,
            50, 40,
            false,
            this, gameViewport
        );
    }

    @Override
    public void render() {

        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused; // Toggle pause state when escape key is pressed
        }
        
        if (!isPaused) {
            player.update(deltaTime); // Update player movement only when the game is not paused
        }

        //locks the camera onto the players cenral coordinates so that the camera is always over the player
        camera.position.set(player.getX() + player.getWidth() / 2f,
        player.getY() + player.getHeight() / 2f,
        0);
        camera.update();                                                  

        //add the camera to the batch projection matrix
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(Color.GREEN); //clears the screen before drawing loop

        //batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();//starts rendering from here

        //all drawing code needs to go in here

        //draws the player using batch.draw instead of player.draw(batch) which seems to work better
        batch.draw(
            player.getTexture(),
            player.getX(),
            player.getY(),
            player.getWidth(),
            player.getHeight()
        );  

        batch.draw(
            tile1.getTexture(),
            tile1.getX(),
            tile1.getY(),
            tile1.getWidth(),
            tile1.getHeight()
        );
        batch.draw(
            tile2.getTexture(),
            tile2.getX(),
            tile2.getY(),
            tile2.getWidth(),
            tile2.getHeight()
        );

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