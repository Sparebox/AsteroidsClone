package gdx.asteroidsclone.entities.particles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.utils.Utils;

public class Debris extends Particle {

    public Debris(float x, float y) {
        this.radius = 1f;
        this.timeToLive = 1f;
        this.initVel = 20;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(x, y);
        this.bd.linearVelocity.set(new Vector2().setToRandomDirection().setLength(initVel));

        this.cs = new CircleShape();
        this.cs.setRadius(radius);

        this.fd = new FixtureDef();
        this.fd.shape = cs;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits  = ContactType.DEBRIS.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;
    }

    @Override
    public void update() {
        updateLifetime();
    }

    @Override
    public void render(ShapeRenderer sr) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        sr.circle(x, y, radius);
    }
}
