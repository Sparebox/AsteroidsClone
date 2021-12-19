package gdx.asteroidsclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.asteroidsclone.Main;

public class DesktopLauncher {

	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Asteroids Clone";
		config.width = 1600;
		config.height = 900;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.useGL30 = true;
		config.vSyncEnabled = true;
		new LwjglApplication(new Main(), config);
	}
}
