package gdx.asteroidsclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import gdx.asteroidsclone.managers.GameKeys;
import gdx.asteroidsclone.managers.InputProcessor;

public class Game extends ApplicationAdapter {

	public static int width;
	public static int height;
	public static OrthographicCamera camera;

	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getWidth();

		camera = new OrthographicCamera(width, height);
		camera.translate(width/2f, height/2f);
		camera.update();

		Gdx.input.setInputProcessor(new InputProcessor());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1); // Clears screen to black
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		GameKeys.update();
	}

	@Override
	public void resize(int width, int height) {

	}
	
	@Override
	public void dispose () {

	}
}
