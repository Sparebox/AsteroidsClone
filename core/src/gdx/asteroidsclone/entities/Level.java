package gdx.asteroidsclone.entities;

import java.util.Arrays;

public enum Level {
    LEVEL1_EASY(4, AsteroidType.SMALL, "Level 1"),
    LEVEL2_EASY(3,AsteroidType.MEDIUM, "Level 2"),
    LEVEl3_EASY(2, AsteroidType.LARGE, "Level 3"),

    LEVEL1_NORMAL(8, AsteroidType.SMALL, "Level 1"),
    LEVEL2_NORMAL(6,AsteroidType.MEDIUM, "Level 2"),
    LEVEl3_NORMAL(4, AsteroidType.LARGE, "Level 3"),

    LEVEL1_HARD(16, AsteroidType.SMALL, "Level 1"),
    LEVEL2_HARD(12,AsteroidType.MEDIUM, "Level 2"),
    LEVEl3_HARD(8, AsteroidType.LARGE, "Level 3");

    public final int ASTEROID_COUNT;
    public final AsteroidType TYPE;

    private final String string;

    Level(int count, AsteroidType type, String str) {
        this.ASTEROID_COUNT = count;
        this.TYPE = type;
        this.string = str;
    }

    @Override
    public String toString() {
        return this.string;
    }

    public static int getTotalAsteroidCount() {
        return Arrays.stream(Level.values()).mapToInt(x -> x.ASTEROID_COUNT).sum();
    }
}
