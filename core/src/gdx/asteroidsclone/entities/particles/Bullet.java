package gdx.asteroidsclone.entities.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.ContactType;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

import java.util.HashSet;

public class Bullet extends Entity {

    private static int bulletCount = 0;
    private static final float INIT_VEL = 200f; // Meters per second
    private static final int RADIUS = 2; // In pixels

    public Bullet(int x, int y, Entity player) {
        if(bulletCount > 2)
            return;
        bulletCount++;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.body = GameScreen.world.createBody(this.bd);
        float angle = player.getBody().getAngle() + MathUtils.PI / 2;
        this.body.setLinearVelocity(new Vector2(INIT_VEL * MathUtils.cos(angle), INIT_VEL * MathUtils.sin(angle)));

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
        if(body == null)
            return;
        if(Utils.toPixel(body.getPosition().x) > Main.INSTANCE.getScreenWidth() ||
        Utils.toPixel(body.getPosition().y) > Main.INSTANCE.getScreenHeight() ||
        body.getPosition().x < 0 || body.getPosition().y < 0) {
            dispose();
            bulletCount--;
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        if(body == null)
            return;
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        sr.circle(x, y, RADIUS);
    }

}
