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

    //private final int FONT_SIZE = 50;
//    private final Color FONT_COLOR = Color.WHITE;
//    private final Color SELECTED_COLOR = Color.RED;

    private Stage stage;
    private Table table;
    private TextButton startButton;
    private TextButton optionsButton;
    private TextButton exitButton;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;

    public MenuScreen() {
        stage = new Stage(new StretchViewport(500 * Main.INSTANCE.ASPECT_RATIO, 500));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        skin = new Skin(Gdx.files.internal("skins/skin.json"));
        startButton = new TextButton("Start", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new GameScreen());
            }
        });
        optionsButton = new TextButton("Options", skin);
        exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(startButton).width(100);
        table.row().pad(10,0,10,0);
        table.add(optionsButton).width(100);
        table.row();
        table.add(exitButton).width(100);
    }

    @Override
    public void render(float deltaTime) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
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
        skin.dispose();
        stage.dispose();
    }

}
