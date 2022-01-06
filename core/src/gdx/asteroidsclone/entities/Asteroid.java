package gdx.asteroidsclone.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.Assets;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.particles.Debris;
import gdx.asteroidsclone.physics.ContactType;

public class Asteroid extends Entity {

    private static final int PARTICLES = 10; // Particle count when hit
    private static final int VERTICES = 8; // 8 is max hitbox vertex count
    private static final int INIT_VEL = 20; // Meters per second
    private static final int VEL_LIMIT = 70; // Meters per second
    private static final Sound BREAK_SFX = Main.INSTANCE.assetManager.get(Assets.BREAK);

    protected Polygon shape;

    private AsteroidType type;

    public Asteroid(float x, float y, AsteroidType type) {
        long seed = System.nanoTime();
        this.type = type;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(x, y);
        this.bd.linearVelocity.set(new Vector2().setToRandomDirection().scl(INIT_VEL));

        this.ps = new PolygonShape();
        this.ps.set(calculateVertices(seed));
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 10f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.categoryBits = ContactType.ASTEROID.BIT;
        this.fd.filter.maskBits = (short) (ContactType.PLAYER.BIT | ContactType.BULLET.BIT |
        ContactType.ASTEROID.BIT | ContactType.TRAIL.BIT | ContactType.DEBRIS.BIT);

        shape = new Polygon();
        shape.setOrigin(x, y);
        shape.setPosition(x, y);
        shape.setVertices(calculateVertices(seed));
    }

    public Asteroid(Vector2 pos, AsteroidType type) {
        new Asteroid(pos.x, pos.y, type);
    }

    @Override
    public void update(float deltaTime) {
        if(gameScreen.isGameOver())
            return;
        int transitionBuffer = 0;  // The distance to appear and disappear from borders
        switch(type) {
            case SMALL:
                transitionBuffer = 10;
                break;
            case MEDIUM:
                transitionBuffer = 20;
                break;
            case LARGE:
                transitionBuffer = 40;
                break;
        }
        if(body.getPosition().x > Main.INSTANCE.WORLD_WIDTH + transitionBuffer) {
            body.setTransform(-transitionBuffer,body.getPosition().y, body.getAngle());
        } else if(body.getPosition().x < -transitionBuffer) {
            body.setTransform(Main.INSTANCE.WORLD_WIDTH + transitionBuffer,body.getPosition().y, body.getAngle());
        }
        if(body.getPosition().y > Main.INSTANCE.WORLD_HEIGHT + transitionBuffer) {
            body.setTransform(body.getPosition().x, -transitionBuffer, body.getAngle());
        } else if(body.getPosition().y < -transitionBuffer) {
            body.setTransform(body.getPosition().x, Main.INSTANCE.WORLD_HEIGHT + transitionBuffer, body.getAngle());
        }
        if(body.getLinearVelocity().len2() > VEL_LIMIT * VEL_LIMIT)
            body.setLinearVelocity(body.getLinearVelocity().setLength(VEL_LIMIT));
    }

    @Override
    public void render(ShapeRenderer sr) {
        if(gameScreen.isGameOver())
            return;
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        sr.translate(x, y,0);
        sr.rotate(0,0,1,rot);
        sr.polygon(shape.getVertices());
        sr.identity();
    }

    @Override
    public void render(SpriteBatch sb) {}

    @Override
    public void dispose() {
        super.dispose();
        AsteroidFactory.asteroidCount--;
    }

    public void hit(Vector2 bulletDir) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        for(int i = 0; i < PARTICLES; i++)
            gameScreen.getEntitiesToAdd().add(new Debris(x ,y));
        float newAngle;
        Player player = gameScreen.getPlayer();
        switch(type) {
            case SMALL:
                // 1 point per distance of 20 meters
                float distance = body.getPosition().dst(player.getBody().getPosition()) / 20f;
                player.setScore(player.getScore() + MathUtils.round(distance));
                gameScreen.getEntitiesToAdd().add(new ScoreIndicator(x, y, MathUtils.round(distance)));
                break;
            case MEDIUM:
                player.setScore(player.getScore() + 5);
                gameScreen.getEntitiesToAdd().add(new ScoreIndicator(x, y, 5));
                for(int i = 0; i < 2; i++) {
                    int sign = i % 2 == 0 ? 1 : -1;
                    int separation = 5;
                    var asteroid = new Asteroid(x, y, AsteroidType.SMALL);
                    newAngle =  bulletDir.angleRad() + sign * MathUtils.PI / 2;
                    asteroid.getBd().position.set(body.getPosition().cpy().add(
                            new Vector2(separation * MathUtils.cos(newAngle), separation * MathUtils.sin(newAngle))));
                    asteroid.getBd().linearVelocity.set(body.getLinearVelocity().cpy().setAngleRad(newAngle).scl(1.4f));
                    asteroid.getBd().angularVelocity = body.getAngularVelocity();
                    gameScreen.getEntitiesToAdd().add(asteroid);
                    AsteroidFactory.asteroidCount++;
                }
                break;
            case LARGE:
                player.setScore(player.getScore() + 10);
                gameScreen.getEntitiesToAdd().add(new ScoreIndicator(x, y, 10));
                for(int i = 0; i < 2; i++) {
                    int sign = i % 2 == 0 ? 1 : -1;
                    int separation = 15;
                    var asteroid = new Asteroid(x, y, AsteroidType.MEDIUM);
                    newAngle =  bulletDir.angleRad() + sign * MathUtils.PI / 2;
                    asteroid.getBd().position.set(body.getPosition().cpy().add(
                            new Vector2(separation * MathUtils.cos(newAngle), separation * MathUtils.sin(newAngle))));
                    asteroid.getBd().linearVelocity.set(body.getLinearVelocity().cpy().setAngleRad(newAngle).scl(1.1f));
                    asteroid.getBd().angularVelocity = body.getAngularVelocity();
                    gameScreen.getEntitiesToAdd().add(asteroid);
                    AsteroidFactory.asteroidCount++;
                }
                break;
        }
        BREAK_SFX.play(Main.SETTINGS.getVolume());
        dispose();
    }

    public float getSize() {
        return Math.max(shape.getScaleX(), shape.getScaleY());
    }

    private float[] calculateVertices(long seed) {
        float[] vertices = new float[VERTICES * 2];
        float angleDiff = (2 * MathUtils.PI) / VERTICES;
        int minRadius = 0; // Meters
        int maxRadius = 0;
        switch(type) {
            case SMALL:
                maxRadius = Player.PLAYER_SCALE;
                minRadius = 3;
                break;
            case MEDIUM:
                maxRadius = 20;
                minRadius = 10;
                break;
            case LARGE:
                maxRadius = 50;
                minRadius = 20;
        }
        float currentAngle = 0;
        MathUtils.random.setSeed(seed);
        for(int i = 0; i < VERTICES * 2; i += 2) {
            float radius = MathUtils.random(minRadius, maxRadius);
            vertices[i] = radius * MathUtils.cos(currentAngle);
            vertices[i + 1] = radius * MathUtils.sin(currentAngle);
            currentAngle += angleDiff;
        }
        return vertices;
    }

}
