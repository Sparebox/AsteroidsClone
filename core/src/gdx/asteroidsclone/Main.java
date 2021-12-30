package gdx.asteroidsclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.screens.MenuScreen;
import gdx.asteroidsclone.utils.Utils;

public class Main extends Game {

	private static final float ASPECT_RATIO = 16f / 9f;

	public static final int WORLD_WIDTH = (int) (200 * ASPECT_RATIO); // In meters
	public static final int WORLD_HEIGHT = 200;


	public static Main INSTANCE;

	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private Camera camera;

	public Main() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
		camera.translate(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
		camera.update();
		gameScreen = new GameScreen(camera);
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		super.render();
	}

	
	@Override
	public void dispose() {
		gameScreen.dispose();
	}

}
