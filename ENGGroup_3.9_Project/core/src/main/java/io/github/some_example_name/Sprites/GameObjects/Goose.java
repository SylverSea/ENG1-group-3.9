import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

import io.github.some_example_name.Sprites.GameObjects.GameObject;
import io.github.some_example_name.Sprites.GameObjects.Player;

public class Goose extends GameObject {
    public static final float RETREAT_TIME = 5f;
    public static final float ACTIVE_RANGE = 10f;

    private float retreatTime = 0f;
    private final Player player;

    public Goose(Texture texture, int x, int y, int width, int height, Player player, Game game) {
        super(texture, x, y, width, height, game);
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        float[] verticies = getVertices();
        float[] playerVerticies = player.getVertices();
        
        if(retreatTime > 0f) {
            retreat();
        } else {} //if overlapping: attack, if within active range, active, else: passive
    }

    private void movePassive() {

    }

    private void moveActive() {

    }

    private void attack() {
        player.TakeDamage();
        retreatTime = RETREAT_TIME;
    }

    private void retreat() {

    }
}