package io.github.some_example_name.Pannels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.Pannels.Menus.Inventory;

/**
 *holds the font+ sprites and 
 *the grey slot background texture when empty
 *
 * @author Erikas Jovaisa
 */
public class Hotbar extends Pannel {

    //loads textures and slots
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture slotTexture;
    private Texture bandageIcon;
    private Texture eggIcon;

    /**
     * builds a stretched grey pixel into background and bandage/egg icound
     */
    public Hotbar(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;

        Pixmap slotPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        slotPixmap.setColor(0.2f, 0.2f, 0.2f, 0.8f);
        slotPixmap.fill();
        slotTexture = new Texture(slotPixmap);
        slotPixmap.dispose();

        bandageIcon = new Texture(Gdx.files.internal("Bandage.png"));
        eggIcon = new Texture(Gdx.files.internal("Egg.png"));
    }

    /**
     * draws bandage and egg slots in fixed screen positions
     * can be changed to change by resolution
     */
    public void draw(Inventory inventory) {
        drawSlot(bandageIcon, inventory.getQuantity("bandage"), "1", 280, 10);
        drawSlot(eggIcon, inventory.getQuantity("egg"), "2", 330, 10);
    }

    /**
     * draws one box slot with its key label, only draws the icon and held amounut when ammount> 0
     */
    private void drawSlot(Texture icon, int quantity, String keyLabel, float x, float y) {
        batch.draw(slotTexture, x, y, 40, 40);
        font.draw(batch, keyLabel, x + 2, y + 38);
        if (quantity > 0) {
            batch.draw(icon, x + 4, y + 4, 32, 32);
            font.draw(batch, String.valueOf(quantity), x + 28, y + 14);
        }
    }
}
