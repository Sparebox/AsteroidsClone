package gdx.asteroidsclone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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

	public AssetManager assetManager;
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
		assetManager = new AssetManager();
		loadAssets();
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(Assets.FONT));
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
		assetManager.dispose();
	}

	private void loadAssets() {
		assetManager.load(Assets.SKIN, Skin.class);
		assetManager.load(Assets.THEME, Music.class);
		assetManager.load(Assets.LOST, Sound.class);
		assetManager.load(Assets.BREAK, Sound.class);
		assetManager.load(Assets.BURN, Sound.class);
		assetManager.load(Assets.HIT, Sound.class);
		assetManager.load(Assets.LEVEL_CHANGE, Sound.class);
		assetManager.load(Assets.SHOOT, Sound.class);
		assetManager.load(Assets.WON, Sound.class);
		assetManager.finishLoading();
	}

}
