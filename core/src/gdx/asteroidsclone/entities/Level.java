package gdx.asteroidsclone.entities;

public enum Level {
    LEVEL1(8, AsteroidType.SMALL), LEVEL2(6,AsteroidType.MEDIUM), LEVEl3(4, AsteroidType.LARGE);

    public final int ASTEROID_COUNT;
    public final AsteroidType TYPE;

    Level(int count, AsteroidType type) {
        this.ASTEROID_COUNT = count;
        this.TYPE = type;
    }
}
