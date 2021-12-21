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

    private static final int INIT_VEL = 20; // Meters per second
    private static final int RADIUS = 3; // In pixels

    public Debris(int x, int y) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.body = GameScreen.world.createBody(this.bd);
        this.body.setLinearVelocity(new Vector2().setLength(INIT_VEL).setToRandomDirection());

        this.cs = new CircleShape();
        this.cs.setRadius(Utils.toWorld(RADIUS));

        this.fd = new FixtureDef();
        this.fd.shape = cs;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;

        this.body.createFixture(this.fd);
        this.cs.dispose();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        sr.circle(x, y, RADIUS);
    }
}
