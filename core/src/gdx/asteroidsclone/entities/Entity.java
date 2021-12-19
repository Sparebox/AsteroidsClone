package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public abstract class Entity {

    protected PolygonShape ps;
    protected Body body;
    protected BodyDef bd;
    protected FixtureDef fd;

    protected Entity() {}

    public abstract void update();

    public abstract void render(ShapeRenderer sr);

}
