package gdx.asteroidsclone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.awt.*;

public class Settings {

    private static final String PREFS_NAME = "AsteroidsClonePrefs";
    private static final String VOLUME = "volume";
    private static final String TOP_SCORE = "topScore";
    private static final String BOT_ENABLED = "botEnabled";
    private static final String RESOLUTION = "resolution";
    private static final String DIFFICULTY = "difficulty";
    private static final String CONTROL_MODE = "controlMode";

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

    public void setBotEnabled(boolean enabled) {
        getPrefs().putBoolean(BOT_ENABLED, enabled);
        getPrefs().flush();
    }

    public boolean isBotEnabled() {
        return getPrefs().getBoolean(BOT_ENABLED, false);
    }

    public void setResolution(String resolution) {
        getPrefs().putString(RESOLUTION, resolution);
        getPrefs().flush();
    }

    public String getResolution() {
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return getPrefs().getString(RESOLUTION, screenSize.width+"x"+screenSize.height);
    }

    public void setDifficulty(String difficulty) {
        getPrefs().putString(DIFFICULTY, difficulty);
        getPrefs().flush();
    }

    public String getDifficulty() {
        return getPrefs().getString(DIFFICULTY, "Normal");
    }

    public void setControlMode(String mode) {
        getPrefs().putString(CONTROL_MODE, mode);
        getPrefs().flush();
    }

    public String getControlMode() {
        return getPrefs().getString(CONTROL_MODE, "arrows");
    }

    public int[] parseResolutionString(String str) {
        String width;
        String height;
        int xIndex = 0;
        for(char c : str.toCharArray()) {
            if(c == 'x') {
                xIndex = str.indexOf(c);
                break;
            }
        }
        width = str.substring(0, xIndex);
        height = str.substring(xIndex + 1);
        return new int[] {Integer.parseInt(width), Integer.parseInt(height)};
    }
}
