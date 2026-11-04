package io.github.some_example_name;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Pannels.Menus.PauseMenu;
import io.github.some_example_name.Sprites.GameObjects.GameObject;
import io.github.some_example_name.Sprites.GameObjects.Goose;
import io.github.some_example_name.Sprites.GameObjects.Player;
import io.github.some_example_name.Sprites.GameObjects.Tiles.NonWallTile;
import io.github.some_example_name.Sprites.GameObjects.Tiles.Tile;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GdxMainGame extends Game {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private ArrayList<NonWallTile> tiles = new ArrayList<>();
    private ArrayList<Goose> geese = new ArrayList<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private boolean isPaused = false;
    private PauseMenu pauseMenu;
    private Viewport gameViewport = new ExtendViewport(400, 400);

    @Override
    /**    
     * Instantiates the camera and game objects.
     * 
     * @author Oliver Quantrill-Scott
     * @author Daniel Bassett
     * @author James Garfield
     * @author Sky Burrows
     */
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
            32, 64,
            this, gameViewport, 0
        );
        gameObjects.add(player); // Add player to the list of game objects

        //spawns in the geese
        geese.add(new Goose(
            new Texture(Gdx.files.internal(String.format("goose.png"))),
            100, 100,
            32, 32,
            player,
            this, gameViewport));
        geese.add(new Goose(
            new Texture(Gdx.files.internal(String.format("goose.png"))),
            200, 150,
            32, 32,
            player,
            this, gameViewport));
        gameObjects.addAll(geese); // Add geese to the list of game objects

        //spawns in the tiles
        tiles.add(new NonWallTile(
            new Texture(Gdx.files.internal(String.format("Background_TEMP.png"))),
            -500, -500,
            1250, 1000,
            false,
            this, gameViewport)
        );

        pauseMenu = new PauseMenu(batch, camera);
    }

    /**    
     * Renders the basic textures.
     * 
     * @author Oliver Quantrill-Scott
     * @author James Garfield
     */
    @Override
    public void render() {

        float deltaTime = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused; // Toggle pause state when escape key is pressed
        }
        
        if (!isPaused) {
            player.update(deltaTime); // Update player movement only when the game is not paused
            
            for (Goose goose : geese) {
                goose.update(deltaTime); // Update each goose's behavior
            }
        }

        //Set camera position to player's position (adjusting for difference between Vector2 and Vector3)
        camera.position.set(player.getPosition().x / 2, player.getPosition().y / 2, 0);
        camera.update();                                                  

        //add the camera to the batch projection matrix
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(Color.GREEN); //clears the screen before drawing loop

        //batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();//starts rendering from here


        for (Tile tile : tiles) {
            tile.draw(batch, new Vector2(camera.position.x, camera.position.y));
        }

        //Bubble sorts the game objects based on their y-coordinate
        int n = gameObjects.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (gameObjects.get(j).getY() < gameObjects.get(j + 1).getY()) {
                    // Swap gameObjects[j] and gameObjects[j + 1]
                    GameObject temp = gameObjects.get(j);
                    gameObjects.set(j, gameObjects.get(j + 1));
                    gameObjects.set(j + 1, temp);
                }
            }
        }

        //draws the player using batch.draw instead of player.draw(batch) which seems to work better
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(batch, new Vector2(camera.position.x, camera.position.y));
        }

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