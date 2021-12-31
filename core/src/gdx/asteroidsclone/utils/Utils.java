package gdx.asteroidsclone.utils;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    /**
     * Inverts a vector's direction
     * @param vec the vector to be inverted
     * @return a new inverted vector
     */
    public static Vector2 invert(Vector2 vec) {
        return vec.cpy().scl(-1f);
    }

}
