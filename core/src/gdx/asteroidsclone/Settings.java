package gdx.asteroidsclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

    private static final String PREFS_NAME = "AsteroidsClonePrefs";
    private static final String VOLUME = "volume";
    private static final String TOP_SCORE = "topScore";

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

    public void setTopScore(int score) {
        getPrefs().putInteger(TOP_SCORE, score);
        getPrefs().flush();
    }

    public int getTopScore() {
        return getPrefs().getInteger(TOP_SCORE, 0);
    }
}
