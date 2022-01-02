package gdx.asteroidsclone.entities.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.utils.Utils;

public class PlayerTrail extends Particle {

    public PlayerTrail(float x, float y, Entity player) {
        this.radius = 1f;
        this.timeToLive = 0.5f;
        this.initVel = 50;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(x, y);
        float angle = player.getBody().getAngle() - MathUtils.PI / 2;
        float rand = MathUtils.randomSign() * MathUtils.random(0f, MathUtils.PI / 4);
        angle += rand;
        this.bd.linearVelocity.set(new Vector2(initVel * MathUtils.cos(angle), initVel * MathUtils.sin(angle)));

        this.cs = new CircleShape();
        this.cs.setRadius(radius);

        this.fd = new FixtureDef();
        this.fd.shape = cs;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.TRAIL.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;
    }

    @Override
    public void update(float deltaTime) {
        updateLifetime();
    }

    @Override
    public void render(ShapeRenderer sr) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        sr.circle(x, y, radius);
    }

    @Override
    public void render(SpriteBatch sb) {}
}
