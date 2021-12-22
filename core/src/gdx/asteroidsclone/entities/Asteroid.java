package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.particles.Debris;
import gdx.asteroidsclone.physics.ContactType;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class Asteroid extends Entity {

    private static final int PARTICLES = 10; // Particle count when hit
    private static final int VERTICES = 7;
    private static final int INIT_VEL = 20; // Meters per second

    private Polygon shape;
    private AsteroidType type;

    public Asteroid(int x, int y, AsteroidType type) {
        long seed = System.nanoTime();
        this.type = type;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.bd.linearVelocity.set(new Vector2().setToRandomDirection().scl(INIT_VEL));

        this.ps = new PolygonShape();
        this.ps.set(calculateVertices(false, seed));
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.ASTEROID.BIT;
        this.fd.filter.maskBits = (short) (ContactType.PLAYER.BIT | ContactType.BULLET.BIT |
        ContactType.ASTEROID.BIT | ContactType.TRAIL.BIT | ContactType.DEBRIS.BIT);

        shape = new Polygon();
        shape.setOrigin(x, y);
        shape.setPosition(x, y);
        shape.setVertices(calculateVertices(true, seed));
    }

    @Override
    public void update() {
        if(Utils.toPixel(body.getPosition().x) > Main.INSTANCE.getScreenWidth() + 50) {
            body.setTransform(Utils.toWorld(-50),body.getPosition().y, body.getAngle());
        } else if(body.getPosition().x < Utils.toWorld(-50)) {
            body.setTransform(Utils.toWorld(Main.INSTANCE.getScreenWidth() + 50),body.getPosition().y, body.getAngle());
        }
        if(Utils.toPixel(body.getPosition().y) > Main.INSTANCE.getScreenHeight() + 50) {
            body.setTransform(body.getPosition().x, Utils.toWorld(-50), body.getAngle());
        } else if(body.getPosition().y < Utils.toWorld(-50)) {
            body.setTransform(body.getPosition().x, Utils.toWorld(Main.INSTANCE.getScreenHeight() + 50), body.getAngle());
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        sr.translate(x, y,0);
        sr.rotate(0,0,1,rot);
        sr.polygon(shape.getVertices());
        sr.identity();
    }

    public void hit() {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        for(int i = 0; i < PARTICLES; i++)
            gameScreen.getEntitiesToAdd().add(new Debris(x ,y));
        float newAngle;
        Player player = gameScreen.getPlayer();
        switch(type) {
            case SMALL:
                // 1 point per distance of 100 pixels
                float distance = Utils.toPixel(body.getPosition().dst(player.getBody().getPosition())) / 100f;
                player.setScore(player.getScore() + MathUtils.round (distance));
                break;
            case MEDIUM:
                player.setScore(player.getScore() + 5);
                for(int i = 0; i < 2; i++) {
                    var asteroid = new Asteroid(x, y, AsteroidType.SMALL);
                    newAngle = body.getAngle() + MathUtils.randomSign() * MathUtils.random(0f, MathUtils.PI / 4);
                    asteroid.getBd().linearVelocity.set(body.getLinearVelocity().cpy().setAngleRad(newAngle));
                    gameScreen.getEntitiesToAdd().add(asteroid);
                }
                break;
            case LARGE:
                player.setScore(player.getScore() + 10);
                for(int i = 0; i < 2; i++) {
                    var asteroid = new Asteroid(x, y, AsteroidType.MEDIUM);
                    newAngle = body.getAngle() + MathUtils.randomSign() * MathUtils.random(0f, MathUtils.PI / 4);
                    asteroid.getBd().linearVelocity.set(body.getLinearVelocity().cpy().setAngleRad(newAngle));
                    gameScreen.getEntitiesToAdd().add(asteroid);
                }
                break;
        }
        dispose();
    }

    private float[] calculateVertices(boolean inPixels, long seed) {
        float[] vertices = new float[VERTICES * 2];
        float angleDiff = (2 * MathUtils.PI) / VERTICES;
        int minRadius = 0; // Pixels
        int maxRadius = 0; // Pixels
        switch(type) {
            case SMALL:
                maxRadius = 30;
                minRadius = 10;
                break;
            case MEDIUM:
                maxRadius = 70;
                minRadius = 50;
                break;
            case LARGE:
                maxRadius = 120;
                minRadius = 70;
        }
        float currentAngle = 0;
        MathUtils.random.setSeed(seed);
        for(int i = 0; i < VERTICES * 2; i += 2) {
            float radius = MathUtils.random(minRadius, maxRadius);
            if(!inPixels)
                radius = Utils.toWorld(radius);
            vertices[i] = radius * MathUtils.cos(currentAngle);
            vertices[i + 1] = radius * MathUtils.sin(currentAngle);
            currentAngle += angleDiff;
        }
        return vertices;
    }
}
