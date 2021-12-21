package gdx.asteroidsclone.entities.particles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class Debris extends Entity {

    private static final float TIME_TO_LIVE = 1f; // Seconds
    private static final int INIT_VEL = 20; // Meters per second
    private static final int RADIUS = 3; // In pixels

    private float lifeTimer;
    private long lastTime;

    public Debris(int x, int y) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.bd.linearVelocity.set(new Vector2().setToRandomDirection().setLength(INIT_VEL));

        this.cs = new CircleShape();
        this.cs.setRadius(Utils.toWorld(RADIUS));

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
        long diff = System.currentTimeMillis() - lastTime;
        if(diff > 500f) {
            lifeTimer += 0.5f;
            lastTime = System.currentTimeMillis();
        }
        if(lifeTimer > TIME_TO_LIVE) {
            dispose();
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        sr.circle(x, y, RADIUS);
    }
}
