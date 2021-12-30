package gdx.asteroidsclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;

public class AsteroidFactory {

    private static final int ASTEROID_INTERVAL = 4000; // In milliseconds

    public static int asteroidCount;

    private GameScreen gameScreen;
    private int levelSpawned;
    private long lastTime = System.currentTimeMillis();
    private Level currentLevel = Level.LEVEl3;

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
            } else {
                System.out.println("Game won"); //TODO: Implement game over functionality
                Gdx.app.exit();
            }
        }
    }

    private void spawnAsteroid() {
        boolean onX = MathUtils.randomBoolean();
        int x = MathUtils.random(0, Main.WORLD_WIDTH + 1);
        int y = MathUtils.random(0, Main.WORLD_HEIGHT + 1);
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



