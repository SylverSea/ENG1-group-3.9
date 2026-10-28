<<<<<<< Updated upstream
package io.github.some_example_name.Sprites.GameObjects;

//import libraries to allow detection of key inputs and should make movement speed independent of framerate (fingers crossed)
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
//allows player to be rendered
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

=======
>>>>>>> Stashed changes
public class Player extends GameObject {

    private Texture playerTexture;//declare player texture

    public Player(float startX, float startY) {
        super(startX, startY);//constructer - initialises a new instance of player at specified coords using GameObject
        //load player image
        playerTexture = new Texture("player.png");
    }

    public void Move() {
        //calculate speed from delta time which makes it independent of frame rate (so no movement lag, yay)
        float speed = 100f * Gdx.graphics.getDeltaTime();

        //W move player up
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            y += speed;
        }

        //S moves player down
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= speed;
        }

        //A moves player left
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= speed;
        }

        //D moves player right
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            x += speed;
        }


    }
    //draw meathod allows player to draw itself
    public void draw(SpriteBatch batch) {
        batch.draw(playerTexture, x, y);
    }
    //deletes player texture when game closes to save memory
    public void dispose() {
        playerTexture.dispose();
    }

    public void Interact() {

    }
    public void UseItem() {
        
    }

    public void TakeDamage() {

    }
}