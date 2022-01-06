package gdx.asteroidsclone.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import gdx.asteroidsclone.Assets;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;

public class AsteroidFactory {

    private static int asteroidInterval = 4000; // In milliseconds
    private static final Sound LEVEL_CHANGE_SFX = Main.INSTANCE.assetManager.get(Assets.LEVEL_CHANGE);
    private static final Sound WIN_SFX = Main.INSTANCE.assetManager.get(Assets.WON);

    public static int asteroidCount;

    private GameScreen gameScreen;
    private int levelSpawned;
    private long lastTime = System.currentTimeMillis();
    private Level currentLevel;
    private int lastLevelOrdinal;

    public AsteroidFactory(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        switch(Main.SETTINGS.getDifficulty()) {
            case "Easy":
                currentLevel = Level.LEVEL1_EASY;
                lastLevelOrdinal = Level.LEVEl3_EASY.ordinal();
                break;
            case "Normal":
                currentLevel = Level.LEVEL1_NORMAL;
                lastLevelOrdinal = Level.LEVEl3_NORMAL.ordinal();
                break;
            case "Hard":
                currentLevel = Level.LEVEL1_HARD;
                lastLevelOrdinal = Level.LEVEl3_HARD.ordinal();
                asteroidInterval = 2000;
                break;
        }
    }

    public void update() {
        if(System.currentTimeMillis() - lastTime > asteroidInterval) {
            if(levelSpawned < currentLevel.ASTEROID_COUNT)
                spawnAsteroid();
            lastTime = System.currentTimeMillis();
        }

        if(asteroidCount == 0 && levelSpawned == currentLevel.ASTEROID_COUNT) {
            if(currentLevel.ordinal() < lastLevelOrdinal) {
                currentLevel = Level.values()[currentLevel.ordinal() + 1];
                LEVEL_CHANGE_SFX.play(Main.SETTINGS.getVolume());
                levelSpawned = 0;
            } else {
                WIN_SFX.play(Main.SETTINGS.getVolume());
                Player.BURN_SFX.stop();
                gameScreen.endGame(true);
            }
        }
    }

    private void spawnAsteroid() {
        boolean onX = MathUtils.randomBoolean();
        int x = MathUtils.random(25, Main.INSTANCE.WORLD_WIDTH - 25);
        int y = MathUtils.random(25, Main.INSTANCE.WORLD_HEIGHT - 25);
        if(onX)
            y = MathUtils.randomBoolean() ? -5 : Main.INSTANCE.WORLD_HEIGHT + 5;
        else
            x = MathUtils.randomBoolean() ? -5 : Main.INSTANCE.WORLD_WIDTH + 5;
        gameScreen.getEntitiesToAdd().add(new Asteroid(x, y, currentLevel.TYPE));
        levelSpawned++;
        asteroidCount++;
        }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}



