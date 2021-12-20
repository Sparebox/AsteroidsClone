package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class Asteroid extends Entity {

    private Polygon shape;

    public Asteroid(int x, int y, AsteroidType type) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.body = GameScreen.world.createBody(this.bd);

        this.ps = new PolygonShape();
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 0f;
        this.fd.filter.categoryBits = ContactType.PLAYER.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;

        this.body.createFixture(this.fd);
        ps.dispose();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(ShapeRenderer sr) {

    }

    @Override
    public void dispose() {

    }


}

enum AsteroidType {
    SMALL, MEDIUM, LARGE;
}
