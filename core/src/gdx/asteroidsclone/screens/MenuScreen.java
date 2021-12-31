package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import gdx.asteroidsclone.Main;


public class MenuScreen extends ScreenAdapter {

    public static final int BUTTON_WIDTH = 100;

    private Stage stage;
    private Table table;
    private TextButton startButton;
    private TextButton optionsButton;
    private TextButton exitButton;
    private Skin skin = Main.INSTANCE.skin;

    public MenuScreen() {
        stage = new Stage(new StretchViewport(Main.INSTANCE.UI_WIDTH, Main.INSTANCE.UI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        startButton = new TextButton("Start", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new GameScreen());
            }
        });
        optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new PreferencesScreen());
            }
        });
        exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(startButton).width(BUTTON_WIDTH);
        table.row().pad(10,0,10,0);
        table.add(optionsButton).width(BUTTON_WIDTH);
        table.row();
        table.add(exitButton).width(BUTTON_WIDTH);
    }

    @Override
    public void render(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        Gdx.gl.glClearColor(0,0,0,1); // Clears screen with black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }
    @Override
    public void dispose() {
        stage.dispose();
    }

}
