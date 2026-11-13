package io.github.some_example_name.Pannels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends Pannel {

    private float x, y, width, height;
    /** valuse for buttons scale */
    private float scale = 1f;
    /** A single pixel, stretched to the button's size when drawn. */
    private Texture boxTexture;
    /** same box, a bit darker - shown while the mouse is hovering over the button */
    private Texture hoverBoxTexture;
    /** colour the label is drawn in */
    private Color textColor;

    /** stores size/pos, builds a grey box texture with white text - default look
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @author Erikas Jovaisa
     */
    public Button(float x, float y, float width, float height) {
        this(x, y, width, height, Color.GRAY, Color.WHITE);
    }

    /** stores size/pos
     * builds box texture in the given colour, label drawn in the given text colour
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param boxColor
     * @param textColor
     * @author Erikas Jovaisa
     */
    public Button(float x, float y, float width, float height, Color boxColor, Color textColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.textColor = textColor;

        boxTexture = buildBoxTexture(boxColor);
        hoverBoxTexture = buildBoxTexture(boxColor.cpy().mul(0.7f, 0.7f, 0.7f, 1f)); //same colour, darkened for hover
    }

    /**
     * builds a single coloured pixel, stretched to the button's size when drawn
     *
     * @param color
     * @return
     * @author Erikas Jovaisa
     */
    private Texture buildBoxTexture(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose(); // frees pixmap once texture is built
        return texture;
    }

    /**
     * returns buttons x coordinate
     * 
     * @return
     * @author Erikas Jovaisa
     */
    public float getX() {
        return x;
    }

    /**
     * returns buttons y coordinate
     * 
     * @return
     * @author Erikas Jovaisa
     */
    public float getY() {
        return y;
    }

    /**
     * checks if a point falls in buttons bounds and 
     * click bounds multiple by scale to adjust for screen size
     * 
     * @param touchX
     * @param touchY
     * @return
     * @author Erikas Jovaisa
     */
    public boolean isClicked(float touchX, float touchY) {
        return touchX >= x * scale && touchX <= x * scale + width * scale && touchY >= y * scale && touchY <= y * scale + height * scale;
    }

    /** button scale can be changed */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     *checks if a point (the mouse, every frame) falls in the buttons bounds - same test as
     *isClicked, just under a clearer name for hover checks
     *
     * @param pointX
     * @param pointY
     * @return
     * @author Erikas Jovaisa
     */
    public boolean isHovered(float pointX, float pointY) {
        return isClicked(pointX, pointY);
    }

    /**
     *draws the drey button box with label on top
     * 
     * @param batch
     * @param label
     * @param font
     * @author Erikas Jovaisa
     */
    public void draw(SpriteBatch batch, String label, BitmapFont font) {
        draw(batch, label, font, false);
    }

    /**
     *draws the button box (darker if hovered) with label on top
     *
     * @param batch
     * @param label
     * @param font
     * @param hovered true to draw the darker hover box instead of the normal one
     * @author Erikas Jovaisa
     */
    public void draw(SpriteBatch batch, String label, BitmapFont font, boolean hovered) {
        batch.draw(hovered ? hoverBoxTexture : boxTexture, x * scale, y * scale, width * scale, height * scale);
        font.setColor(textColor);
        font.draw(batch, label, x * scale + 20 * scale, y * scale + 20 * scale);
    }
}
