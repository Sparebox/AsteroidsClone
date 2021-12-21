package gdx.asteroidsclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.asteroidsclone.Main;

public class DesktopLauncher {

	public static void main (String[] args) {
		var config = new LwjglApplicationConfiguration();
		config.title = "Asteroids Clone";
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.useGL30 = true;
		config.vSyncEnabled = true;
		new LwjglApplication(new Main(), config);
	}
}
