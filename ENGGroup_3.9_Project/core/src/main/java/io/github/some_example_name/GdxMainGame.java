package io.github.some_example_name;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Pannels.TextBox;
import io.github.some_example_name.Pannels.Menus.PauseMenu;
import io.github.some_example_name.Sprites.GameObjects.GameObject;
import io.github.some_example_name.Sprites.GameObjects.Goose;
import io.github.some_example_name.Sprites.GameObjects.InteractableObject;
import io.github.some_example_name.Sprites.GameObjects.MapItem;
import io.github.some_example_name.Sprites.GameObjects.Nest;
import io.github.some_example_name.Sprites.GameObjects.Player;
import io.github.some_example_name.Sprites.GameObjects.Statue;
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
    private ArrayList<InteractableObject> interactables = new ArrayList<>();
    /** list of items on map */
    private ArrayList<MapItem> pickups = new ArrayList<>();
    /** variable for pausing game */
    private boolean isPaused = true;
    /** variable for game starting */
    private boolean gameStarted = false;
    /** variable for player dying */
    private boolean gameOver = false;
    /** value for wether interact prompt appears */
    private boolean showPrompt = false;
    private PauseMenu pauseMenu;
    /** textbox dependant on if interact should pop up */
    private TextBox interactPrompt;
    private Viewport gameViewport = new ExtendViewport(400, 400);
    private MapItem bandage;

    /** timer value (5min) */
    private static final float ROUND_DURATION = 300f;
    /** range for player to interact with interactable */
    private static final float INTERACT_RANGE = 60f;
    private float timeRemaining = ROUND_DURATION;
    private int score = 0;
    private int eventsTriggered = 0;

    // fixed camera for hud text
    private OrthographicCamera hudCamera = new OrthographicCamera();
    /** font cant be built until create runs */
    private BitmapFont font;

    /**
     * Instantiates the camera and game objects.
     *
     * @author Oliver Quantrill-Scott
     * @author Daniel Bassett
     * @author James Garfield
     * @author Sky Burrows
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); //builds hud font
        //sets hud camera viewport and starting position
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        camera.position.set(400, 240, 0);  //spawns camera at starting pos
        camera.update();                          //self-explanatory

        //constructs pause menu for game to start at
        pauseMenu = new PauseMenu(batch, camera);

        //creates player and adds to gameObjects
        Texture playertexture = new Texture(Gdx.files.internal(String.format("player.png")));
        player = new Player(
            playertexture,
            0, 40,
            32, 64,
            this, gameViewport, 100 //creates players health (100 for testing unsure on nr)
        );
        gameObjects.add(player); // Add player to the list of game objects


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


        tiles.add(new NonWallTile(
            new Texture(Gdx.files.internal(String.format("Background_TEMP.png"))),
            -500, -500,
           1000, 1000,
            false,
            this, gameViewport)
        );

        
        gameObjects.add(new MapItem("Bandage", 50, 50, 25, 25, this, gameViewport));
        gameObjects.add(new MapItem("Egg", 00, 00, 10, 10, this, gameViewport));
        gameObjects.add(new MapItem("Egg", -50, -50, 10, 10, this, gameViewport));


        //matches hudcamera viewport to sceen pixel size so hud stays a fixed size regardless of camera.
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        /** creates the statue and nest and registers
         * 
         * @author Erikas Jovaisa 
         */
        interactables.add(new Statue(
            new Texture(Gdx.files.internal(String.format("statue.png"))),
            584, 473, //statue pos
            34, 72, //statue size
            this, gameViewport));
        interactables.add(new Nest(
            new Texture(Gdx.files.internal(String.format("nest.png"))),
            -449, 626, //nest pos
            102, 102, //statue size
            this, gameViewport));
        gameObjects.addAll(interactables); 

        //items position and size defined
        pickups.add(new MapItem("Bandage", 50, 50, 25, 25, this, gameViewport));
        pickups.add(new MapItem("Egg", 0, 0, 10, 10, this, gameViewport));
        pickups.add(new MapItem("Egg", -50, -50, 10, 10, this, gameViewport));
        gameObjects.addAll(pickups); //items spawned


        //rebuilds pause menu against hud camera

        pauseMenu = new PauseMenu(batch, hudCamera);

        //creates interact text box
        interactPrompt = new TextBox("Press E to interact", Gdx.graphics.getWidth() / 2f - 60, 130);
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && gameStarted && !gameOver) {
            isPaused = !isPaused; // Toggle pause state when escape key is pressed, but only mid-game
        }

            //unpauses game when button pressed
        if (isPaused && !gameOver && Gdx.input.justTouched() && pauseMenu.isPlayButtonClicked()) {
            isPaused = false;
            gameStarted = true;
        }

        if (!isPaused) {
            player.update(deltaTime); // Update player movement only when the game is not paused

            for (Goose goose : geese) {
                goose.update(deltaTime); // Update each goose's behavior
            }

            timeRemaining -= deltaTime; //counts down
            if (timeRemaining <= 0 || player.getCurrentHealth() <= 0) {
                gameOver = true;
            }

            boolean interactPressed = Gdx.input.isKeyJustPressed(Input.Keys.E);

            //scan interactables for nearest object
            InteractableObject nearest = null;
            float nearestDist = Float.MAX_VALUE;
            for (InteractableObject obj : interactables) {
                if (!obj.canInteract()) continue; //skips object (nest or statue) already used 3 times 
                float dist = player.getPosition().dst(obj.getPosition());
                if (dist < INTERACT_RANGE && dist < nearestDist) {
                    nearest = obj;
                    nearestDist = dist;
                }
            }
            showPrompt = nearest != null;
            //sets interact prompt based on nearest object
            if (nearest != null) {
                interactPrompt.setText(nearest instanceof Nest ? "Use egg to interact" : "Press E to interact");
            }
            if (nearest != null && interactPressed) {
                nearest.interact(player);
            }

            if (interactPressed) {
                checkItemPickups(); //calls item pickup on e press
            }

            //key binding for inv -adding
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                player.UseItem("bandage");
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

        batch.end(); //ends rendering from here

        //draws hud in second batch pass so hud doesnt move with player camera
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        font.draw(batch, "Time: " + (int) timeRemaining, 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Health: " + player.getCurrentHealth(), 10, Gdx.graphics.getHeight() - 30);
        font.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 50);
        font.draw(batch, "Events: " + eventsTriggered, 10, Gdx.graphics.getHeight() - 70);

        if (showPrompt) {
            interactPrompt.draw(batch);
        }

        if (gameOver) {
            font.draw(batch, "GAME OVER - Score: " + score, Gdx.graphics.getWidth() / 2f - 80, Gdx.graphics.getHeight() / 2f);
        }

        if (isPaused) {
            pauseMenu.draw(); //draws pause menu if paused
        }

        batch.end();
    }

    /**
     * spawns goose when player interacts a 3rd time
     *
     * @param position
     * @author Erikas Jovaisa 
     */
    public void spawnGoose(Vector2 position) {
        Goose goose = new Goose(
            new Texture(Gdx.files.internal("goose.png")),
            (int) position.x, (int) position.y,
            32, 32,
            player, this, gameViewport
        );
        geese.add(goose);
        gameObjects.add(goose);
    }

    /** function to pick up item when 
     * player stands over it and presses E and ensures if stood on 2 items player doesnt pick up 2 
     *
     * @author Erikas Jovaisa
     */
    private void checkItemPickups() {
        Iterator<MapItem> it = pickups.iterator();
        while (it.hasNext()) {
            MapItem item = it.next();
            if (player.getBoundingRectangle().overlaps(item.getBoundingRectangle())) {
                player.getInventory().addItem(item.getItemID().toLowerCase(), 1);
                gameObjects.remove(item); // removes picked up item
                it.remove();
                break; //stop after picking one item up
            }
        }
    }

    /**
     * temporary function to increment score 
     * at a (non finalised) amount
     * 
     * @param amount
     * @author Erikas Jovaisa
     */
    public void addScore(int amount) {
        score += amount;
    }

    /** increments events counter when player does an event
     *
     * @author Erikas Jovaisa
     */
    public void incrementEvents() {
        eventsTriggered++;
    }

    /** disposes of everything when closed (libgdx classic)
     *
     * @author Erikas Jovaisa
     */
    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }
}
