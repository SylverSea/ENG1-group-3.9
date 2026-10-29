package io.github.some_example_name.Sprites.GameObjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

public class Statue extends GameObject {

    public Statue(Texture texture, int x, int y, int width, int height, Game game) {
        super(texture, x, y, width, height, game);
    }

    @Override
    public void update(float deltaTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}