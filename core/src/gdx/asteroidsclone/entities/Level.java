package gdx.asteroidsclone.entities;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Level {
    LEVEL1(8, AsteroidType.SMALL, "Level 1"),
    LEVEL2(6,AsteroidType.MEDIUM, "Level 2"),
    LEVEl3(4, AsteroidType.LARGE, "Level 3");

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
