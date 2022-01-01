package gdx.asteroidsclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.screens.MenuScreen;

public class AsteroidFactory {

    private static final int ASTEROID_INTERVAL = 4000; // In milliseconds

    public static int asteroidCount;

    private GameScreen gameScreen;
    private int levelSpawned;
    private long lastTime = System.currentTimeMillis();
    private Level currentLevel = Level.LEVEL1;
    private Sound levelChangeSFX;
    private Sound winSFX;

    public AsteroidFactory(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        levelChangeSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/level_change.wav"));
        winSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/won.wav"));
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
                levelChangeSFX.play(0.1f);
                levelSpawned = 0;
            } else {
                winSFX.play(0.1f);
                Main.INSTANCE.setScreen(new MenuScreen());
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
        }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}



