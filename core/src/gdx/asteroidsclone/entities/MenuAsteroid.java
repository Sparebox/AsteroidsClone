package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import gdx.asteroidsclone.Main;

public class MenuAsteroid extends Asteroid {

    public MenuAsteroid(float x, float y, AsteroidType type) {
        super(x, y, type);
    }

    @Override
    public void update(float deltaTime) {
        float newX = MathUtils.random(0, Main.INSTANCE.GUI_WIDTH);
        float newY = MathUtils.random(Main.INSTANCE.GUI_HEIGHT + 50, Main.INSTANCE.GUI_HEIGHT + 500);
        if(body.getPosition().y < -40f) {
            body.setTransform(newX, newY, body.getAngle());
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        sr.translate(x, y,0);
        sr.rotate(0,0,1,rot);
        sr.polygon(shape.getVertices());
        sr.identity();
    }

}
