package gdx.asteroidsclone.physics;

import com.badlogic.gdx.physics.box2d.*;
import gdx.asteroidsclone.entities.Asteroid;
import gdx.asteroidsclone.entities.particles.Bullet;
import gdx.asteroidsclone.screens.GameScreen;

public class CustomContactListener implements ContactListener {

    private GameScreen gameScreen;

    public CustomContactListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        short a = contact.getFixtureA().getFilterData().categoryBits;
        short b = contact.getFixtureB().getFilterData().categoryBits;

        if(a == ContactType.BULLET.BIT && b == ContactType.ASTEROID.BIT) {
            ((Bullet) contact.getFixtureA().getBody().getUserData()).dispose();
            ((Asteroid) contact.getFixtureB().getBody().getUserData()).hit();

        } else if(a == ContactType.ASTEROID.BIT && b == ContactType.BULLET.BIT) {
            ((Bullet) contact.getFixtureB().getBody().getUserData()).dispose();
            ((Asteroid) contact.getFixtureA().getBody().getUserData()).hit();
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
