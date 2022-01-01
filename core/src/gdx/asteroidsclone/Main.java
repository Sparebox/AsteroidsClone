package gdx.asteroidsclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import gdx.asteroidsclone.screens.MenuScreen;

public class Main extends Game {

	public static Main INSTANCE;
	public static Settings SETTINGS;

	public final float ASPECT_RATIO;
	public final int WORLD_WIDTH; // In meters
	public final int WORLD_HEIGHT;
	public final float GUI_WIDTH;
	public final float GUI_HEIGHT;

	public Skin skin;
	public ShapeRenderer sr;
	public SpriteBatch sb;
	public FreeTypeFontGenerator fontGenerator;
	public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

	public Main(int width, int height) {
		INSTANCE = this;
		SETTINGS = new Settings();
		ASPECT_RATIO = (float) width / (float) height;
		WORLD_HEIGHT = 200;
		WORLD_WIDTH = (int) (WORLD_HEIGHT * ASPECT_RATIO);
		GUI_HEIGHT = height;
		GUI_WIDTH = width;
	}

	@Override
	public void create() {
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VCR_OSD_MONO.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		skin = new Skin(Gdx.files.internal("skins/skin.json"));
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
		skin.dispose();
	}

}
