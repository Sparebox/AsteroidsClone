package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import gdx.asteroidsclone.screens.GameScreen;

import java.util.Random;

public abstract class Entity {

    public static GameScreen gameScreen;

    protected PolygonShape ps;
    protected CircleShape cs;
    protected Body body;
    protected BodyDef bd;
    protected FixtureDef fd;
    protected boolean disposed;

    public abstract void update(float deltaTime);

    public abstract void render(ShapeRenderer sr);

    public abstract void render(SpriteBatch sb);

    public void dispose() {
        if(disposed)
            return;
        gameScreen.getEntitiesToDelete().add(this);
        disposed = true;
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

    public PolygonShape getPs() {
        return ps;
    }

    public void setPs(PolygonShape ps) {
        this.ps = ps;
    }

    public CircleShape getCs() {
        return cs;
    }

    public void setCs(CircleShape cs) {
        this.cs = cs;
    }
}
