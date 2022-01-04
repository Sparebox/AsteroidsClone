package gdx.asteroidsclone.entities.particles;

import gdx.asteroidsclone.entities.Entity;

public abstract class Particle extends Entity {

    protected int initVel; // Meters per second
    protected float radius; // In meters
    protected float timeToLive; // Seconds
    protected float lifeTimer;
    protected long lastTime;

    protected void updateLifetime() {
        long diff = System.currentTimeMillis() - lastTime;
        if(diff > 100f) {
            lifeTimer += 0.1f;
            lastTime = System.currentTimeMillis();
        }
        if(lifeTimer > timeToLive) {
            dispose();
        }
    }


}
