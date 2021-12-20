package gdx.asteroidsclone.entities;

public enum ContactType {
    ASTEROID((short) 0), PLAYER((short) 1), TRAIL((short) 2), BULLET((short) 3);

    public final short BIT;

    ContactType(short bit) {
        this.BIT = bit;
    }
}
