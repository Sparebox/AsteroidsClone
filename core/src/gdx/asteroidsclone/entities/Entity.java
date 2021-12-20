package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import gdx.asteroidsclone.screens.GameScreen;

import java.util.Random;

public abstract class Entity {

    public static GameScreen gameScreen;

    protected Random random = new Random();
    protected PolygonShape ps;
    protected CircleShape cs;
    protected Body body;
    protected BodyDef bd;
    protected FixtureDef fd;

    protected Entity() {}

    public abstract void update();

    public abstract void render(ShapeRenderer sr);

    public abstract void dispose();

    public Body getBody() {
        return body;
    }
}
