package gdx.asteroidsclone.utils;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    public static final float PPM = 8f; // Pixels per meter

    /**
     * Converts pixels into meters
     * @param pixels the length in pixels to convert
     * @return the amount in meters
     */
    public static float toWorld(float pixels) { // Converts pixels to meters
        return (pixels / PPM);
    }

    /**
     * Converts meters into pixels
     * @param meters the length in meters to convert
     * @return the amount in pixels
     */
    public static int toPixel(float meters) { // Converts meters to pixels
        return (int) (meters * PPM);
    }

    /**
     * Inverts a vector's direction
     * @param vec the vector to be inverted
     * @return a new inverted vector
     */
    public static Vector2 invert(Vector2 vec) {
        Vector2 newVec = vec.cpy();
        return newVec.scl(-1);
    }

}
