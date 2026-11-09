package io.github.some_example_name.Pannels;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**small fixed piece of hud text
 *
 * @author Erikas
 */
public class TextBox extends Pannel {

    private String text;
    private float x, y;
    private BitmapFont font = new BitmapFont();

    /**
     * stores text and position
     *
     * @param text
     * @param x
     * @param y
     * @author Erikas
     */
    public TextBox(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    /** draws the text box
     *
     * @param batch
     * @author Erikas
     */
    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y);
    }

    /**
     *changes what text this shows
     * 
     * @param text
     * @author Erikas
     */
    public void setText(String text) {
        this.text = text;
    }
}
