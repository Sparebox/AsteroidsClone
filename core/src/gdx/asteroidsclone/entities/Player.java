package gdx.asteroidsclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.particles.Bullet;
import gdx.asteroidsclone.entities.particles.PlayerTrail;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.utils.Utils;

public class Player extends Entity {

    private static final float THRUST = 2e3f; // In newtons
    private static final float TURNING_TORQUE = 2e3f; // In newton-meters
    private static final float DAMPING = 1f;
    private static final float ANGULAR_DAMPING = 5f;
    private static final float PARTICLE_RATE = 3f;
    private static final float FIRE_RATE = 0.1f;
    private static final int PLAYER_SCALE = 30; // In pixels

    private Polygon shape;
    private int particleOutputTimer;
    private int fireRateTimer;

    public Player(int x, int y) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.bd.linearDamping = DAMPING;
        this.bd.angularDamping = ANGULAR_DAMPING;

        this.ps = new PolygonShape();
        this.ps.set(calculateHitbox());
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.PLAYER.BIT;
        this.fd.filter.maskBits = ContactType.ASTEROID.BIT;

        shape = new Polygon();
        shape.setOrigin(x, y);
        shape.setPosition(x, y);
        shape.setVertices(calculateVertices());
    }

    @Override
    public void update() {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        float angle = body.getAngle() + MathUtils.PI / 2;
        Vector2 thrustVector = new Vector2(THRUST * MathUtils.cos(angle), THRUST * MathUtils.sin(angle));
        Vector2 inverseDir = Utils.invert(thrustVector).setLength(15f);
        Vector2 bulletVector = thrustVector.cpy().setLength(25f);

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyForceToCenter(thrustVector, true);
            particleOutputTimer++;
            if(particleOutputTimer > 1f / PARTICLE_RATE) {
                gameScreen.getEntitiesToAdd().add(new PlayerTrail((int) (x + inverseDir.x), (int) (y + inverseDir.y), this));
                particleOutputTimer = 0;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            body.applyTorque(TURNING_TORQUE,true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            body.applyTorque(-TURNING_TORQUE,true);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireRateTimer++;
            if(fireRateTimer > 1f / FIRE_RATE && Bullet.bulletCount < 3) {
                gameScreen.getEntitiesToAdd().add(new Bullet((int) (x + bulletVector.x), (int) (y + bulletVector.y), this));
                fireRateTimer = 0;
            }
        }

        if(x > Main.INSTANCE.getScreenWidth()) {
            body.setTransform(0f, body.getPosition().y,body.getAngle());
        } else if(x < 0) {
            body.setTransform(Utils.toWorld(Main.INSTANCE.getScreenWidth()), body.getPosition().y,body.getAngle());
        }

        if(y > Main.INSTANCE.getScreenHeight()) {
            body.setTransform(body.getPosition().x, 0f, body.getAngle());
        } else if(y < 0) {
            body.setTransform(body.getPosition().x, Utils.toWorld(Main.INSTANCE.getScreenHeight()), body.getAngle());
        }
//
    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        sr.translate(x, y,0);
        sr.rotate(0,0,1,rot);
        sr.setColor(Color.YELLOW);
        sr.polygon(shape.getVertices());
        sr.setColor(Color.WHITE);
        sr.identity();
    }

    private float[] calculateVertices() {
        float[] vertices = new float[8];
        float scale = PLAYER_SCALE;
        vertices[0] = scale * MathUtils.cos(MathUtils.PI / 2);
        vertices[1] = scale * MathUtils.sin(MathUtils.PI / 2);
        vertices[2] = scale * MathUtils.cos(MathUtils.PI + MathUtils.PI / 4);
        vertices[3] = scale * MathUtils.sin(MathUtils.PI + MathUtils.PI / 4);
        vertices[4] = scale / 3 * MathUtils.cos(-MathUtils.PI / 2);
        vertices[5] = scale / 3 * MathUtils.sin(-MathUtils.PI / 2);
        vertices[6] = scale * MathUtils.cos(-MathUtils.PI / 4);
        vertices[7] = scale * MathUtils.sin(-MathUtils.PI / 4);
        return vertices;
    }

    private float[] calculateHitbox() {
        float[] vertices = new float[6];
        float scale = Utils.toWorld(PLAYER_SCALE);
        vertices[0] = scale * MathUtils.cos(MathUtils.PI / 2);
        vertices[1] = scale * MathUtils.sin(MathUtils.PI / 2);
        vertices[2] = scale * MathUtils.cos(MathUtils.PI + MathUtils.PI / 4);
        vertices[3] = scale * MathUtils.sin(MathUtils.PI + MathUtils.PI / 4);
        vertices[4] = scale * MathUtils.cos(-MathUtils.PI / 4);
        vertices[5] = scale * MathUtils.sin(-MathUtils.PI / 4);
        return vertices;
    }
}
