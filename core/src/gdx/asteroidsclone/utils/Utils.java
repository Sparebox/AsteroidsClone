package gdx.asteroidsclone.utils;

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
}
