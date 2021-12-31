package gdx.asteroidsclone.entities.particles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.utils.Utils;

public class Bullet extends Particle {

    public static int bulletCount = 0;

    public Bullet(float x, float y, Entity player) {
        this.radius = 0.5f;
        this.initVel = 200;
        bulletCount++;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(x, y);
        float angle = player.getBody().getAngle() + MathUtils.PI / 2;
        this.bd.linearVelocity.set(new Vector2(initVel * MathUtils.cos(angle), initVel * MathUtils.sin(angle)));

        this.cs = new CircleShape();
        this.cs.setRadius(radius);

        this.fd = new FixtureDef();
        this.fd.shape = cs;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.BULLET.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;
    }

    @Override
    public void update() {
        if(body == null)
            return;
        if(body.getPosition().x > Main.INSTANCE.WORLD_WIDTH ||
        body.getPosition().y > Main.INSTANCE.WORLD_HEIGHT ||
        body.getPosition().x < 0 || body.getPosition().y < 0) {
            dispose();
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        if(body == null)
            return;
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        sr.circle(x, y, radius);
    }

    @Override
    public void dispose() {
        gameScreen.getEntitiesToDelete().add(this);
        bulletCount--;
    }

}
