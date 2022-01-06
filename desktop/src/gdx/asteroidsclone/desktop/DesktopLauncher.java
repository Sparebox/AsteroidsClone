package gdx.asteroidsclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.asteroidsclone.Main;

import java.awt.Toolkit;

public class DesktopLauncher {

	public static void main(String[] args) {
		var config = new LwjglApplicationConfiguration();
		config.title = "Asteroids Clone";
		var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = screenSize.width;
		config.height = screenSize.height;
		config.fullscreen = true;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.useGL30 = true;
		config.vSyncEnabled = true;
		config.forceExit = false;
		new LwjglApplication(new Main(config.width, config.height), config);
	}
}
