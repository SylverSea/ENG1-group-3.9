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

import io.github.some_example_name.Pannels.Hotbar;
import io.github.some_example_name.Pannels.TextBox;
import io.github.some_example_name.Pannels.Menus.PauseMenu;
import io.github.some_example_name.Pannels.Menus.StartMenu;
import io.github.some_example_name.Sprites.GameObjects.GameObject;
import io.github.some_example_name.Sprites.GameObjects.InteractableObject;
import io.github.some_example_name.Sprites.GameObjects.MapItem;
import io.github.some_example_name.Sprites.GameObjects.Nest;
import io.github.some_example_name.Sprites.GameObjects.Statue;
import io.github.some_example_name.Sprites.GameObjects.MovingObjects.Goose;
import io.github.some_example_name.Sprites.GameObjects.MovingObjects.Player;
import io.github.some_example_name.Sprites.GameObjects.Tiles.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GdxMainGame extends Game {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Player player;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Goose> geese = new ArrayList<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<InteractableObject> interactables = new ArrayList<>();
    /** list of items on map */
    private ArrayList<MapItem> pickups = new ArrayList<>();
    private ArrayList<GameObject> solidObjects = new ArrayList<>();
    /** variable for pausing game */
    private boolean isPaused = true;
    /** variable for game starting */
    private boolean gameStarted = false;
    /** variable for player dying */
    private boolean gameOver = false;
    /** value for wether interact prompt appears */
    private boolean showPrompt = false;
    private PauseMenu pauseMenu;
    /** declare start menu title screen*/
    private StartMenu startMenu;
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
    /** declares basic resolution width */
    private static final float HUD_BASE_WIDTH = 800f;
    /** declares basic resolution height  */
    private static final float HUD_BASE_HEIGHT = 480f;
    /** scaling value for hud */
    private float hudScale = 1f;

    /** declares hotbar field */
    private Hotbar hotbar;

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

        //Instanciate wall tiles and add to solidObjects list
        Texture wallTexture = new Texture(Gdx.files.internal("tile.png"));
        tiles.add(new WallTile(
            wallTexture,
            500, 200,
            40, 40,
            this, gameViewport));
        tiles.add(new WallTile(wallTexture,
            -810, -810,
            10, 1610,
            null, gameViewport));
        tiles.add(new WallTile(wallTexture,
            -810, 800,
            1610, 10,
            null, gameViewport));
        tiles.add(new WallTile(wallTexture,
            800, -800,
            10, 1550,
            null, gameViewport));
        tiles.add(new WallTile(
            wallTexture,
            -800,-810,
            1610, 10,
            this, gameViewport));
        solidObjects.addAll(tiles); // Add the wall tile to the list of solid objects

        //Instanciate non-wall tiles
        tiles.add(0, new NonWallTile(
            new Texture(Gdx.files.internal(String.format("Background_TEMP.png"))),
            -800, -800,
            1600, 1600,
            false,
            this, gameViewport)
        );
        
        //creates player and adds to gameObjects
        Texture playertexture = new Texture(Gdx.files.internal(String.format("player.png")));
        player = new Player(
            playertexture,
            0, 0,
            40, 80,
            this, gameViewport, 10, solidObjects); //creates players health
        gameObjects.add(player); // Add player to the list of game objects


        geese.add(new Goose(
            new Texture(Gdx.files.internal(String.format("goose.png"))),
            100, 100,
            48, 48,
            player,
            this, gameViewport, solidObjects));
        geese.add(new Goose(
            new Texture(Gdx.files.internal(String.format("goose.png"))),
            200, 150,
            48, 48,
            player,
            this, gameViewport, solidObjects));
        gameObjects.addAll(geese); // Add geese to the list of game objects

        //matches hudcamera viewport to sceen pixel size so hud stays a fixed size regardless of camera.
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        updateHudScale(); //calls update hud scale 

        hotbar = new Hotbar(batch, font); //constructs hotbar passing shared components

        /** creates the statue and nest and registers them
         * 
         * @author Erikas Jovaisa 
         */
        interactables.add(new Statue(
            new Texture(Gdx.files.internal(String.format("statue.png"))),
            600, 450, //statue pos
            34, 72, //statue size
            this, gameViewport));
        //
        interactables.add(new Nest(
            new Texture(Gdx.files.internal(String.format("nest.png"))),
            -600, 700, //nest pos
            102, 102, //statue size
            this, gameViewport));
        gameObjects.addAll(interactables); //add statue and nest to drawing list


        //spawn item position and size defined
        pickups.add(new MapItem("Bandage", 50, 50, 34, 34, this, gameViewport)); 
        pickups.add(new MapItem("Egg", 0, 0, 18, 18, this, gameViewport)); 
        pickups.add(new MapItem("Egg", -50, -50, 18, 18, this, gameViewport)); 
        pickups.add(new MapItem("Egg", -100, 50, 18, 18, this, gameViewport)); 
        gameObjects.addAll(pickups); //items spawned


        //rebuilds pause menu against hud camera

        pauseMenu = new PauseMenu(batch, hudCamera);
        startMenu = new StartMenu(batch, hudCamera); 

        //creates interact text box
        interactPrompt = new TextBox("Press E to interact", Gdx.graphics.getWidth() / 2f - 60, 130);
    }

    /**
     * Runs through one frame of the game
     *
     * @author Oliver Quantrill-Scott
     * @author James Garfield
     */
    @Override
    public void render() {

        float deltaTime = Gdx.graphics.getDeltaTime();

        //checks for click on start button, if clicked game unpauses
        if (!gameStarted && startMenu.isStartButtonClicked()) {
            gameStarted = true;
            isPaused = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && gameStarted && !gameOver) {
            isPaused = !isPaused; // Toggle pause state when escape key is pressed, but only mid-game
        }

            //unpauses when resume clicked
        if (gameStarted && isPaused && !gameOver && Gdx.input.justTouched() && pauseMenu.isPlayButtonClicked()) {
            isPaused = false;
        }

        if (!isPaused && gameStarted && !gameOver) {
            Update(deltaTime); // Update game state only when the game is not paused
        }

        Draw(); // Always draw the game state, regardless of pause state
    }

    /**
     * Updates the game state, including player and goose movements, interactions, and game over conditions.
     * 
     * @param deltaTime
     * @author Oliver Quantrill-Scott
     * @author James Garfield
     */
    public void Update(float deltaTime) {
        player.update(deltaTime); // Update player movement only when the game is not paused

        for (Goose goose : geese) {
            goose.update(deltaTime); // Update each goose's behavior
        }

        timeRemaining -= deltaTime; //counts down
        if (timeRemaining <= 0 || player.getCurrentHealth() <= 0) {
            gameOver = true;
        }

        boolean interactPressed = Gdx.input.isKeyJustPressed(Input.Keys.E);
        boolean eggKeyPressed = Gdx.input.isKeyJustPressed(Input.Keys.NUM_2);

        //scan interactables for nearest object
        InteractableObject nearest = null;
        float nearestDist = Float.MAX_VALUE;
        for (InteractableObject obj : interactables) {
            if (!obj.canInteract()) continue; //skips object (nest or statue) already used 3 times 
            float dist = player.getRelativePosition().dst(obj.getRelativePosition());
            if (dist < INTERACT_RANGE && dist < nearestDist) {
                nearest = obj;
                nearestDist = dist;
            }
        }
        showPrompt = nearest != null;
        //sets interact prompt based on nearest object
        if (nearest != null) {
            interactPrompt.setText(nearest instanceof Nest ? "Press 2 (egg) to interact" : "Press E to interact");
        }

        //nest interacet with by using ergg item slot over e others still use e
        if (nearest instanceof Nest) {
            if (eggKeyPressed) {
                nearest.interact(player);
            }
        } else if (nearest != null && interactPressed) {
            nearest.interact(player);
        }

        if (interactPressed) {
            checkItemPickups(); //calls item pickup on e press
        }

        //key bindings for inventory slots - 1 uses a bandage, 2 interacts with a nearby nest (handled above)
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.UseItem("bandage");
        }

        //f11 key check calling toggleFullscreen()
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }

        //Set camera position to player's position (adjusting for difference between Vector2 and Vector3)
        camera.position.set(player.getRelativePosition().x / 2, player.getRelativePosition().y / 2, 0);
        camera.update();
    }

    /**
     * Draws the game
     * 
     * @author Oliver Quantrill-Scott
     * @author James Garfield
     */
    public void Draw() {
        //add the camera to the batch projection matrix
        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(Color.PURPLE); //clears the screen before drawing loop

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

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(batch, new Vector2(camera.position.x, camera.position.y));
        }

        batch.end(); //ends rendering from here

        //draws hud in second batch pass so hud doesnt move with player camera
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        //wraps hud in game started 
        if (gameStarted) {
            float hudMargin = 10 * hudScale;
            float hudLineHeight = 20 * hudScale;
            font.draw(batch, "Time: " + (int) timeRemaining, hudMargin, Gdx.graphics.getHeight() - hudMargin);
            font.draw(batch, "Health: " + player.getCurrentHealth(), hudMargin, Gdx.graphics.getHeight() - hudMargin - hudLineHeight);
            font.draw(batch, "Score: " + score, hudMargin, Gdx.graphics.getHeight() - hudMargin - hudLineHeight * 2);
            font.draw(batch, "Events: " + eventsTriggered, hudMargin, Gdx.graphics.getHeight() - hudMargin - hudLineHeight * 3);
            hotbar.draw(player.getInventory(), player.getItemsUsedCount(), hudScale); //draw hotbar each frame

            if (showPrompt) {
                interactPrompt.draw(batch);
            }

            if (gameOver) {
                font.draw(batch, "GAME OVER - Score: " + score, Gdx.graphics.getWidth() / 2f - 80, Gdx.graphics.getHeight() / 2f);
            }
        }

        if (!gameStarted) {
            startMenu.draw(); //title screen before the game has started
        } else if (isPaused) {
            pauseMenu.draw(hudScale); //draws pause menu if paused mid-game
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
            48, 48,
            player, this, gameViewport, solidObjects
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

    /** re-syncs hudCamera and gameViewport to the new window size and re centers interact textbox */
    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
        hudCamera.setToOrtho(false, width, height);
        hudCamera.update();
        updateHudScale();
        if (interactPrompt != null) {
            interactPrompt.setPosition(width / 2f - 60, 130);
        }
    }

    /** switches between windowed and fullscreen */
    private void toggleFullscreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(640, 480);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }

    /** workous out hudScale and applies it to BitmapFont */
    private void updateHudScale() {
        hudScale = Math.min(Gdx.graphics.getWidth() / HUD_BASE_WIDTH, Gdx.graphics.getHeight() / HUD_BASE_HEIGHT);
        font.getData().setScale(hudScale);
    }

    /** disposes of everything when closed (libgdx classic)
     *
     * @author Erikas Jovaisa
     * @author Oliver Quantrill-Scott
     */
    @Override
    public void dispose() {
        batch.dispose();
        for (GameObject gameObject : gameObjects) {
            gameObject.dispose();
        }
        for (Tile tile : tiles) {
            tile.dispose();
        }
        font.dispose();
    }
}
