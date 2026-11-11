package io.github.some_example_name.Sprites.GameObjects.MovingObjects;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.some_example_name.Sprites.GameObjects.GameObject;

public abstract class MovingObject extends GameObject {
    private ArrayList<GameObject> solidObjects = new ArrayList<>();


    public MovingObject(Texture texture, int x, int y, int width, int height, Game game, Viewport gameViewport, ArrayList<GameObject> solidObjects) {
        super(texture, x, y, width, height, game, gameViewport);
        this.solidObjects = solidObjects;
    }

    /**list of objects the object cant walk through -decding
     *
     * @param solidObjects
     * @author Erikas Jovaisa
     */
    public void setSolidObjects(ArrayList<GameObject> solidObjects) {
        this.solidObjects = solidObjects;
    }

    /**Checks whether position would overlap object
     *
     * @param x
     * @param y
     * @return Whether the position would overlap with given object
     * @author Erikas Jovaisa
     * @author Oliver Quantrill-Scott
     */
    public boolean collidesAt(float x, float y) {
        Rectangle testBounds = new Rectangle(x , y, getWidth(), 10); //Creates rectangle at base of object
        for (GameObject obj : solidObjects) {
            if (testBounds.overlaps(obj.getBoundingRectangle())) {
                return true;
            }
        }
        return false;
    }
}
