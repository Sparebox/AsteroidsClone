package gdx.asteroidsclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import gdx.asteroidsclone.screens.GameOverScreen;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.screens.MenuScreen;
import gdx.asteroidsclone.utils.Utils;

public class Main extends Game {

	public static Main INSTANCE;

	public final float ASPECT_RATIO;
	public final int WORLD_WIDTH; // In meters
	public final int WORLD_HEIGHT;

	public ShapeRenderer sr;
	public SpriteBatch sb;
	public FreeTypeFontGenerator fontGenerator;
	public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

	public Main(int width, int height) {
		INSTANCE = this;
		ASPECT_RATIO = (float) width / (float) height;
		WORLD_WIDTH = (int) (200 * ASPECT_RATIO);
		WORLD_HEIGHT = 200;

	}

	@Override
	public void create() {
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VCR_OSD_MONO.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		setScreen(new MenuScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	
	@Override
	public void dispose() {
		getScreen().dispose();
		sr.dispose();
		sb.dispose();
		fontGenerator.dispose();
	}

}
