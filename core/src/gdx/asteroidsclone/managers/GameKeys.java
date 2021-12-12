package gdx.asteroidsclone.managers;

import com.badlogic.gdx.Input;

public class GameKeys {

    private static boolean[] keys = new boolean[255];
    private static boolean[] previousKeys = new boolean[255];

    public static void update() {
        for(int i = 0; i < 255; i++) {
            previousKeys[i] = keys[i];
        }
    }

    public static void setKey(int key, boolean b) {
        keys[key] = b;
    }

    public static boolean isDown(int key) {
        return keys[key];
    }

    public static boolean isPressed(int key) {
        return keys[key] && !previousKeys[key];
    }
}
