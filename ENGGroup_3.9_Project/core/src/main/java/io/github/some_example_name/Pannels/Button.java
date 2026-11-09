package io.github.some_example_name.Pannels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends Pannel {

    private float x, y, width, height;
    /** A single grey pixel, stretched to the button's size when drawn. */
    private Texture greyBox;

    /** stores size/pos 
     * builds grey box texture
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @author Erikas
     */
    public Button(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        //builds grey pixmap to stretch buttons background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        greyBox = new Texture(pixmap);
        pixmap.dispose(); // frees pixmap once texture is built
    }

    /**
     * returns buttons x coordinate
     * 
     * @return
     * @author Erikas
     */
    public float getX() {
        return x;
    }

    /**
     * returns buttons y coordinate
     * 
     * @return
     * @author Erikas
     */
    public float getY() {
        return y;
    }

    /**
     *checks if a point falls in buttons bounds
     * 
     * @param touchX
     * @param touchY
     * @return
     * @author Erikas
     */
    public boolean isClicked(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width && touchY >= y && touchY <= y + height;
    }

    /**
     *draws the drey button box with label on top
     * 
     * @param batch
     * @param label
     * @param font
     * @author Erikas
     */
    public void draw(SpriteBatch batch, String label, BitmapFont font) {
        batch.draw(greyBox, x, y, width, height);
        font.draw(batch, label, x + 20, y + 20);
    }
}
