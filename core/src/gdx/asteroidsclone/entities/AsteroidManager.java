package gdx.asteroidsclone.entities;

import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;

public class AsteroidManager {

    private static final int ASTEROID_INTERVAL = 2; // In seconds

    private GameScreen gameScreen;
    private int asteroidTimer;
    private int asteroidCount;
    private long lastTime = System.currentTimeMillis();
    private Level currentLevel = Level.LEVEL1;

    public AsteroidManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {
        float diff = System.currentTimeMillis() - lastTime;
        if(diff > 1000) {
            lastTime = System.currentTimeMillis();
            asteroidTimer++;
        }
        if(asteroidTimer > ASTEROID_INTERVAL) {
            if(asteroidCount < currentLevel.ASTEROID_COUNT)
                spawnAsteroid();
            asteroidTimer = 0;
        }
    }

    private void spawnAsteroid() {
        boolean onX = Entity.random.nextBoolean();
        int x = Entity.random.nextInt(Main.INSTANCE.getScreenWidth() + 1);
        int y = Entity.random.nextInt(Main.INSTANCE.getScreenHeight() + 1);
        if(onX)
            y = 0;
        else
            x = 0;
        switch(currentLevel) {
            case LEVEL1:
                gameScreen.getEntitiesToAdd().add(new Asteroid(x, y, currentLevel.TYPE));
                System.out.println("Spawned");
                break;
            case LEVEL2:
                break;
            case LEVEl3:
                break;
        }
        asteroidCount++;
    }
}


