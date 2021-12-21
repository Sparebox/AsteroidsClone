package gdx.asteroidsclone.physics;

public enum ContactType {
    ASTEROID((short) 0b1), PLAYER((short) 0b10), TRAIL((short) 0b100), BULLET((short) 0b1000);

    public final short BIT;

    ContactType(short bit) {
        this.BIT = bit;
    }
}
