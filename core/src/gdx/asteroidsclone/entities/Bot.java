package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.particles.Bullet;
import gdx.asteroidsclone.screens.GameScreen;

import java.util.HashSet;

public class Bot extends Player {

    private Vector2 visualVector;

    public Bot(int x, int y) {
        super(x, y);
    }

    @Override
    public void update(float deltaTime) {
        if(gameScreen.isGameOver())
            return;
        float angle = body.getAngle() + MathUtils.PI / 2;
        var closestBody = findClosestAsteroid();
        if(closestBody != null) {
            shoot(closestBody, angle, deltaTime);
        }
        else if(body.getPosition().dst2(Main.INSTANCE.WORLD_WIDTH/2f, Main.INSTANCE.WORLD_HEIGHT/2f) > 30f * 30f)
            goToCenter(angle);
        checkAreaBounds(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void render(ShapeRenderer sr) {
        if(gameScreen.isGameOver())
            return;
        super.render(sr);
        if(visualVector != null) {
            sr.circle(visualVector.x, visualVector.y ,1f);
            sr.line(body.getPosition(), visualVector);
        }
    }

    private Body findClosestAsteroid() {
        var asteroidQuery = new AsteroidQuery();
        GameScreen.world.QueryAABB(asteroidQuery, 0f, 0f,
                Main.INSTANCE.WORLD_WIDTH, Main.INSTANCE.WORLD_HEIGHT);
        float closestDist2 = Float.POSITIVE_INFINITY;
        Body closestBody = null;
        for(Body asteroid : asteroidQuery.foundAsteroids) {
            float dist2 = body.getPosition().dst2(asteroid.getPosition());
            if(dist2 < closestDist2) {
                closestDist2 = dist2;
                closestBody = asteroid;
            }
        }
        return closestBody;
    }

    private void shoot(Body target, float angle, float deltaTime) {
        Vector2[] results = aimAt(target, angle, deltaTime);
        if(!results[0].isOnLine(results[1], 0.01f))
            return;
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        Vector2 bulletVector = new Vector2(5f * MathUtils.cos(angle), 5f * MathUtils.sin(angle));
        if(Bullet.bulletCount < 0)
            Bullet.bulletCount = 0;
        if(Bullet.bulletCount < 1) {
            gameScreen.getEntitiesToAdd().add(new Bullet(x + bulletVector.x, y + bulletVector.y, this));
            Bullet.bulletCount++;
            SHOOT_SFX.play(Main.SETTINGS.getVolume());
        }
    }

    private Vector2[] aimAt(Body target, float botAngle, float deltaTime) {
        Vector2 botDir = new Vector2(MathUtils.cos(botAngle), MathUtils.sin(botAngle));
        float dist = body.getPosition().cpy().add(botDir.cpy().scl(5f)).dst(target.getPosition());
        float timeToImpact = (dist * 1.7f) / Bullet.INIT_VEL; // Random coefficient applied
        Vector2 targetVel = target.getLinearVelocity().cpy();
        Vector2 leadingVector = target.getPosition().cpy().add(targetVel.cpy().scl(timeToImpact + deltaTime));
        Vector2 targetVector = leadingVector.cpy().sub(body.getPosition()).nor();
        float angleToTarget = targetVector.angleRad(botDir);
        if(angleToTarget < 0)
            body.applyTorque(-TURNING_TORQUE, true);
        else
            body.applyTorque(TURNING_TORQUE, true);
        visualVector = leadingVector;
        return new Vector2[] {botDir, targetVector};
    }

    private void goToCenter(float angle) {
        Vector2 center = new Vector2(Main.INSTANCE.WORLD_WIDTH / 2f, Main.INSTANCE.WORLD_HEIGHT / 2f);
        Vector2 toCenter = center.sub(body.getPosition()).nor();
        Vector2 direction = new Vector2(MathUtils.cos(angle), MathUtils.sin(angle));
        if(!direction.isOnLine(toCenter, 0.1f)) {
            if(direction.angleRad(toCenter) < 0)
                body.applyTorque(TURNING_TORQUE, true);
            else
                body.applyTorque(-TURNING_TORQUE, true);
        } else {
            forward(direction.scl(THRUST));
        }
    }

}

class AsteroidQuery implements QueryCallback {

    public HashSet<Body> foundAsteroids;

    public AsteroidQuery() {
        foundAsteroids = new HashSet<>();
    }

    @Override
    public boolean reportFixture(Fixture fixture) {
        if(fixture.getBody().getUserData() instanceof Asteroid) {
            foundAsteroids.add(fixture.getBody());
        }
        return true;
    }
}
