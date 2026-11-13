package io.github.some_example_name.Pannels.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.github.some_example_name.Pannels.Button;

/**
 * title screen shown before game starts
 * place holder logo
 * bright start button
 * 
 * @author Erikas Jovaisa
 */
public class StartMenu extends Menu {

    /**holds sprite batch */
    private SpriteBatch batch;

    /**fixed hud gamera */
    private OrthographicCamera camera;

    /** holds the background image for start screen*/
    private Texture background = new Texture(Gdx.files.internal("start_background.jpg"));

    /** logo */
    private Texture logo = new Texture(Gdx.files.internal("placeholder_logo_240_120_px.png"));

    /** holds tutorial image bottom right */
    private Texture controlsLegend = new Texture(Gdx.files.internal("controls_legend.png"));

    /** button size temp
     *  attempted centering */
    private Button startButton = new Button(245, 212, 150, 45, Color.YELLOW, Color.BLACK);
    private BitmapFont font = new BitmapFont();

    /**
     * sprites and hud camera
     * @author Erikas Jovaisa
     */
    public StartMenu(SpriteBatch batch, OrthographicCamera camera) {
        this.batch = batch;
        this.camera = camera;
    }

    /**
     * drawing all things
     */
    public void draw() {
        batch.draw(background, 0, 0, 640, 480); //draw backround screen probs change to scale again
        batch.draw(logo, 200, 300, 240, 120);
        startButton.draw(batch, "START", font, isHoveringStartButton());
        batch.draw(controlsLegend, 350, 10, 280, 156); // draws controls thingy
    }

    /**
     *checks whether the mouse is currently over the start button, so draw() can show the
     *darker hover box - same unproject pattern as isStartButtonClicked, just without
     *requiring a click
     *
     * @return whether the mouse is currently over start button
     * @author Erikas Jovaisa
     */
    private boolean isHoveringStartButton() {
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse);
        return startButton.isHovered(mouse.x, mouse.y);
    }

    /**
     *checks whether player has clicked the start button
     *
     * @return whether player has clicked the start button
     * @author Erikas Jovaisa
     */
    public boolean isStartButtonClicked() {
        if (!Gdx.input.justTouched()) return false;
        Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touch);
        return startButton.isClicked(touch.x, touch.y);
    }
}
