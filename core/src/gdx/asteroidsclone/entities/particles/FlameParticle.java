package gdx.asteroidsclone.entities.particles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class FlameParticle extends Entity {

    private static final int RADIUS = 5; // In pixels
    private static final float TIME_TO_LIVE = 0.5f; // Seconds
    private static final int INIT_VEL = 50; // Meters per second

    private float lifeTimer;
    private long lastTime;

    public FlameParticle(int x, int y, Entity player) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.body = GameScreen.world.createBody(this.bd);
        float angle = player.getBody().getAngle() - MathUtils.PI / 2;
        int sign = random.nextInt(2) == 1 ? -1 : 1;
        float rand = sign * random.nextFloat() * (MathUtils.PI / 4);
        angle += rand;
        this.body.setLinearVelocity(new Vector2(INIT_VEL * MathUtils.cos(angle), INIT_VEL * MathUtils.sin(angle)));

        this.cs = new CircleShape();
        this.cs.setRadius(Utils.toWorld(RADIUS));

        this.fd = new FixtureDef();
        this.fd.shape = cs;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.TRAIL.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;

        this.body.createFixture(this.fd);
        this.cs.dispose();
    }

    @Override
    public void update() {
        long diff = System.currentTimeMillis() - lastTime;
        if(diff > 500f) {
            lifeTimer = lifeTimer + 0.5f;
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
