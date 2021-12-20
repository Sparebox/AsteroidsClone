package gdx.asteroidsclone.entities;

import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;

public class AsteroidManager {

    private static final int ASTEROID_INTERVAL = 2; // In seconds

    private GameScreen gameScreen;
    private int asteroidTimer;
    private long lastTime;
    private Level currentLevel = Level.LEVEL1;

    public AsteroidManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {
        float diff = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        if(diff > 1000)
            asteroidTimer++;
        if(asteroidTimer > ASTEROID_INTERVAL) {
            spawnAsteroid();
            asteroidTimer = 0;
        }
    }

    private void spawnAsteroid() {
        switch(currentLevel) {
            case LEVEL1:
                gameScreen.getEntitiesToAdd().add(new Asteroid(Main.INSTANCE.getScreenWidth() / 2, Main.INSTANCE.getScreenHeight() / 2, currentLevel.TYPE));
                break;
            case LEVEL2:
                break;
            case LEVEl3:
                break;
        }
    }
}


