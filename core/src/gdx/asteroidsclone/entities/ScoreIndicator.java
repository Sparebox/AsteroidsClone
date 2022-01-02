package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import gdx.asteroidsclone.Main;

public class ScoreIndicator extends Entity {

    private static final Vector2 VELOCITY = new Vector2(0f, 100f); // Meters per second
    private static final int FONT_SIZE = 40;

    private static FreeTypeFontGenerator fontGenerator = Main.INSTANCE.fontGenerator;
    private static FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = Main.INSTANCE.fontParameter;

    private BitmapFont font;
    private String score;
    private Vector2 pos;

    public ScoreIndicator(float x, float y, int score) {
        this.score = Integer.toString(score);
        pos = new Vector2(x, y);
        fontParameter.size = FONT_SIZE;
        font = fontGenerator.generateFont(fontParameter);
    }

    @Override
    public void update(float deltaTime) {
        pos.add(VELOCITY.cpy().scl(deltaTime));
        if(pos.y > Main.INSTANCE.WORLD_HEIGHT)
            dispose();
    }

    @Override
    public void render(ShapeRenderer sr) {

    }

    @Override
    public void render(SpriteBatch sb) {
        Vector3 projectedPos = gameScreen.getGameCamera().project(new Vector3(pos.x, pos.y, 0f));
        font.draw(sb, score, projectedPos.x, projectedPos.y);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
