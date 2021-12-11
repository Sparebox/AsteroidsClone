package gdx.asteroidsclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Game extends ApplicationAdapter {

	public static int width;
	public static int height;

	public static OrthographicCamera cam;

	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getWidth();

		cam = new OrthographicCamera(width, height);
		cam.translate(width/2f, height/2f);
		cam.update();
	}

	@Override
	public void render () {
		// Clears screen to black
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {

	}
	
	@Override
	public void dispose () {

	}
}
