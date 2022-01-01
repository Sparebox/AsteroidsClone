package gdx.asteroidsclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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

    public static final int PLAYER_SCALE = 5; // In meters

    private static final float THRUST = 2e3f; // In newtons
    private static final float TURNING_TORQUE = 5e3f; // In newton-meters
    private static final float DAMPING = 1f;
    private static final float ANGULAR_DAMPING = 5f;
    private static final float PARTICLE_RATE = 3f;
    private static final float FIRE_RATE = 0.1f;
    private static final int DMG_BLINK_COUNT = 10; // Times to blink when hit
    private static final int BLINK_INTERVAL = 100; // In milliseconds

    private Sound burnSFX;
    private Sound shootSFX;
    private Sound hitSFX;
    private Sound lostSFX;
    private long burnID;
    private Polygon shape;
    private int particleOutputTimer;
    private int fireRateTimer;
    private int score;
    private int lives = 3;
    private int blinkCounter;
    private long lastTime = System.currentTimeMillis();
    private boolean redOn = true;
    private boolean blinking;
    private boolean thrusting;

    public Player(int x, int y) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(x, y);
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

        burnSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/burn.wav"));
        shootSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
        hitSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
        lostSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/lost.wav"));
    }

    @Override
    public void update() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        float angle = body.getAngle() + MathUtils.PI / 2;
        Vector2 thrustVector = new Vector2(THRUST * MathUtils.cos(angle), THRUST * MathUtils.sin(angle));
        Vector2 inverseDir = Utils.invert(thrustVector).setLength(3f);
        Vector2 bulletVector = thrustVector.cpy().setLength(5f);

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyForceToCenter(thrustVector, true);
            particleOutputTimer++;
            if(particleOutputTimer > 1f / PARTICLE_RATE) {
                gameScreen.getEntitiesToAdd().add(new PlayerTrail( x + inverseDir.x,  y + inverseDir.y, this));
                particleOutputTimer = 0;
            }
            thrusting = true;
        } else
            thrusting = false;

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            burnID = burnSFX.play(0.1f);
            burnSFX.setLooping(burnID, true);
        } else if(!thrusting)
            burnSFX.stop(burnID);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            body.applyTorque(TURNING_TORQUE,true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            body.applyTorque(-TURNING_TORQUE,true);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireRateTimer++;
            if(fireRateTimer > 1f / FIRE_RATE && Bullet.bulletCount < 3) {
                gameScreen.getEntitiesToAdd().add(new Bullet(x + bulletVector.x, y + bulletVector.y, this));
                fireRateTimer = 0;
                shootSFX.play(0.1f);
            }
        }

        if(x > Main.INSTANCE.WORLD_WIDTH) {
            body.setTransform(0f, body.getPosition().y,body.getAngle());
        } else if(x < 0) {
            body.setTransform(Main.INSTANCE.WORLD_WIDTH, body.getPosition().y,body.getAngle());
        }

        if(y > Main.INSTANCE.WORLD_HEIGHT) {
            body.setTransform(body.getPosition().x, 0f, body.getAngle());
        } else if(y < 0) {
            body.setTransform(body.getPosition().x, Main.INSTANCE.WORLD_HEIGHT, body.getAngle());
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        sr.translate(x, y,0);
        sr.rotate(0,0,1,rot);
        if(blinking) {
            if(redOn)
                sr.setColor(Color.RED);
            else
                sr.setColor(Color.YELLOW);
            if(System.currentTimeMillis() - lastTime > BLINK_INTERVAL) {
                redOn = !redOn;
                lastTime = System.currentTimeMillis();
                blinkCounter++;
                if(blinkCounter == DMG_BLINK_COUNT) {
                    blinking = false;
                    blinkCounter = 0;
                }
            }
        } else
            sr.setColor(Color.YELLOW);
        sr.polygon(shape.getVertices());
        sr.setColor(Color.WHITE);
        sr.identity();
    }

    @Override
    public void dispose() {
        super.dispose();
        burnSFX.dispose();
    }

    public void hit() {
        hitSFX.play(0.1f);
        blinking = true;
        lives--;
        if(lives == 0) {
            lostSFX.play(0.1f);
            gameScreen.setGameOver(true);
        }

    }

    private float[] calculateVertices() {
        float[] vertices = new float[8];
        float scale = PLAYER_SCALE;
        vertices[0] = scale * MathUtils.cos(MathUtils.PI / 2);
        vertices[1] = scale * MathUtils.sin(MathUtils.PI / 2);
        vertices[2] = scale * MathUtils.cos(MathUtils.PI + MathUtils.PI / 4);
        vertices[3] = scale * MathUtils.sin(MathUtils.PI + MathUtils.PI / 4);
        vertices[4] = scale / 5 * MathUtils.cos(-MathUtils.PI / 2);
        vertices[5] = scale / 5 * MathUtils.sin(-MathUtils.PI / 2);
        vertices[6] = scale * MathUtils.cos(-MathUtils.PI / 4);
        vertices[7] = scale * MathUtils.sin(-MathUtils.PI / 4);
        return vertices;
    }

    private float[] calculateHitbox() {
        float[] vertices = new float[6];
        float scale = PLAYER_SCALE;
        vertices[0] = scale * MathUtils.cos(MathUtils.PI / 2);
        vertices[1] = scale * MathUtils.sin(MathUtils.PI / 2);
        vertices[2] = scale * MathUtils.cos(MathUtils.PI + MathUtils.PI / 4);
        vertices[3] = scale * MathUtils.sin(MathUtils.PI + MathUtils.PI / 4);
        vertices[4] = scale * MathUtils.cos(-MathUtils.PI / 4);
        vertices[5] = scale * MathUtils.sin(-MathUtils.PI / 4);
        return vertices;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

}

