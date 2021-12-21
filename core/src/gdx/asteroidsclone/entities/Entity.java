package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import gdx.asteroidsclone.screens.GameScreen;

import java.util.Random;

public abstract class Entity {

    public static GameScreen gameScreen;
    public static Random random = new Random();

    protected PolygonShape ps;
    protected CircleShape cs;
    protected Body body;
    protected BodyDef bd;
    protected FixtureDef fd;

    protected Entity() {}

    public abstract void update();

    public abstract void render(ShapeRenderer sr);

    public void dispose() {
        gameScreen.getEntitiesToDelete().add(this);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public BodyDef getBd() {
        return bd;
    }

    public void setBd(BodyDef bd) {
        this.bd = bd;
    }

    public FixtureDef getFd() {
        return fd;
    }

    public void setFd(FixtureDef fd) {
        this.fd = fd;
    }
}
