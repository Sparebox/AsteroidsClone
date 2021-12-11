package gdx.asteroidsclone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.asteroidsclone.Game;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Asteroids clone";
		config.width = 1600;
		config.height = 800;
		config.resizable = false;

		new LwjglApplication(new Game(), config);
	}
}
