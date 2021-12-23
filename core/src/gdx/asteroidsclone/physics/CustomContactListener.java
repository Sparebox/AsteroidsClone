package gdx.asteroidsclone.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import gdx.asteroidsclone.entities.Asteroid;
import gdx.asteroidsclone.entities.Player;
import gdx.asteroidsclone.entities.particles.Bullet;
import gdx.asteroidsclone.screens.GameScreen;

public class CustomContactListener implements ContactListener {

    private static final int COOL_DOWN = 100; // In milliseconds

    private GameScreen gameScreen;
    private long lastTime = System.currentTimeMillis();

    public CustomContactListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        var a = contact.getFixtureA().getFilterData().categoryBits;
        var b = contact.getFixtureB().getFilterData().categoryBits;

        if(a == ContactType.BULLET.BIT && b == ContactType.ASTEROID.BIT) {
            var bullet = (Bullet) contact.getFixtureA().getBody().getUserData();
            Vector2 bulletDir = bullet.getBody().getLinearVelocity().cpy().setLength(1);
            ((Asteroid) contact.getFixtureB().getBody().getUserData()).hit(bulletDir);
            bullet.dispose();

        } else if(a == ContactType.ASTEROID.BIT && b == ContactType.BULLET.BIT) {
            var bullet = (Bullet) contact.getFixtureB().getBody().getUserData();
            Vector2 bulletDir = bullet.getBody().getLinearVelocity().cpy().setLength(1);
            ((Asteroid) contact.getFixtureA().getBody().getUserData()).hit(bulletDir);
            bullet.dispose();
        }


        if(a == ContactType.PLAYER.BIT && b == ContactType.ASTEROID.BIT) {
            if(System.currentTimeMillis() - lastTime > COOL_DOWN) {
                var p = (Player) contact.getFixtureA().getBody().getUserData();
                p.hit();
                lastTime = System.currentTimeMillis();
            }
        } else if(a == ContactType.ASTEROID.BIT && b == ContactType.PLAYER.BIT) {
            if(System.currentTimeMillis() - lastTime > COOL_DOWN) {
                var p = (Player) contact.getFixtureB().getBody().getUserData();
                p.hit();
                lastTime = System.currentTimeMillis();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
