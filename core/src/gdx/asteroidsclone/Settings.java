package gdx.asteroidsclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

    private static final String PREFS_NAME = "AsteroidsClonePrefs";
    private static final String VOLUME = "volume";

    private Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void setVolume(float volume) {
        getPrefs().putFloat(VOLUME, volume);
        getPrefs().flush();
    }

    public float getVolume() {
        return getPrefs().getFloat(VOLUME, 1.0f);
    }

}
