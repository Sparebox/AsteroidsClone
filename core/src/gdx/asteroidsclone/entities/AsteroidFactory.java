package gdx.asteroidsclone.entities;

import com.badlogic.gdx.math.MathUtils;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;

public class AsteroidFactory {

    private static final int ASTEROID_INTERVAL = 4000; // In milliseconds

    public static int asteroidCount;

    private GameScreen gameScreen;
    private int levelSpawned;
    private long lastTime = System.currentTimeMillis();
    private Level currentLevel = Level.LEVEL1;

    public AsteroidFactory(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {
        if(System.currentTimeMillis() - lastTime > ASTEROID_INTERVAL) {
            if(levelSpawned < currentLevel.ASTEROID_COUNT)
                spawnAsteroid();
            lastTime = System.currentTimeMillis();
        }

        if(asteroidCount == 0 && levelSpawned == currentLevel.ASTEROID_COUNT) {
            if(currentLevel.ordinal() < Level.LEVEl3.ordinal()) {
                currentLevel = Level.values()[currentLevel.ordinal() + 1];
                levelSpawned = 0;
                System.out.println("Level change");
            } else {
                System.out.println("Game won"); //TODO: Implement game over functionality
            }
        }
    }

    private void spawnAsteroid() {
        boolean onX = MathUtils.randomBoolean();
        int x = MathUtils.random(0, Main.INSTANCE.getScreenWidth() + 1);
        int y = MathUtils.random(0, Main.INSTANCE.getScreenHeight() + 1);
        if(onX)
            y = 0;
        else
            x = 0;
        gameScreen.getEntitiesToAdd().add(new Asteroid(x, y, currentLevel.TYPE));
        levelSpawned++;
        }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}



