package gdx.asteroidsclone.entities.particles;

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

    public PlayerTrail(int x, int y, Entity player) {
        this.radius = 5;
        this.timeToLive = 0.5f;
        this.initVel = 50;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        float angle = player.getBody().getAngle() - MathUtils.PI / 2;
        float rand = MathUtils.randomSign() * MathUtils.random(0f, MathUtils.PI / 4);
        angle += rand;
        this.bd.linearVelocity.set(new Vector2(initVel * MathUtils.cos(angle), initVel * MathUtils.sin(angle)));

        this.cs = new CircleShape();
        this.cs.setRadius(Utils.toWorld(radius));

        this.fd = new FixtureDef();
        this.fd.shape = cs;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.TRAIL.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;
    }

    @Override
    public void update() {
        updateLifetime();
    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        sr.circle(x, y, radius);
    }
}
