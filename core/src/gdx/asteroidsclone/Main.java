package gdx.asteroidsclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.screens.MenuScreen;

public class Main extends Game {

	public static Main INSTANCE;

	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private Camera camera;
	private int screenWidth;
	private int screenHeight;

	public Main() {
		INSTANCE = this;
	}

	@Override
	public void create() {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(screenWidth, screenHeight);
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

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
}
