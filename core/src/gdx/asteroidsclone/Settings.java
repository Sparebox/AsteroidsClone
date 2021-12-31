package gdx.asteroidsclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

    private static final String PREFS_NAME = "AsteroidsClonePrefs";
    private static final String RESOLUTION = "resolution";

    private Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void setResolution(String resolution) {
        getPrefs().putString(RESOLUTION, resolution);
    }
}
