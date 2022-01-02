package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import gdx.asteroidsclone.Assets;
import gdx.asteroidsclone.Main;


public class MenuScreen extends ScreenAdapter {

    public static final int BUTTON_WIDTH = 100;
    public static final int FONT_SIZE = 50;
    public static final Music THEME = Main.INSTANCE.assetManager.get(Assets.THEME);

    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Skin skin = Main.INSTANCE.assetManager.get(Assets.SKIN);
    private FreeTypeFontGenerator fontGenerator = Main.INSTANCE.fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = Main.INSTANCE.fontParameter;
    private Label titleLabel;
    private Label authorLabel;
    private TextButton startButton;
    private TextButton optionsButton;
    private TextButton controlsButton;
    private TextButton exitButton;

    public MenuScreen() {
        stage = new Stage(new StretchViewport(Main.INSTANCE.GUI_WIDTH, Main.INSTANCE.GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        fontParameter.size = FONT_SIZE;
        font = fontGenerator.generateFont(fontParameter);
        var style = new Label.LabelStyle();
        style.font = font;
        titleLabel = new Label("Asteroids Clone", style);
        fontParameter.size = 20;
        style.font = fontGenerator.generateFont(fontParameter);
        authorLabel = new Label("Copyright Oskari Ojamaa 2022", style);
        authorLabel.setPosition(0,0);
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
        controlsButton = new TextButton("Controls", skin);
        controlsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new ControlsScreen());
            }
        });
        exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(titleLabel);
        table.row().pad(100,0,10,0);
        table.add(startButton).width(BUTTON_WIDTH);
        table.row().pad(0,0,10,0);
        table.add(optionsButton).width(BUTTON_WIDTH);
        table.row().pad(0,0,10,0);
        table.add(controlsButton).width(BUTTON_WIDTH);
        table.row().pad(0,0,10,0);
        table.add(exitButton).width(BUTTON_WIDTH);
        stage.addActor(authorLabel);
        THEME.play();
        THEME.setVolume(Main.SETTINGS.getVolume());
        THEME.setLooping(true);
        Gdx.input.setCursorCatched(false);
    }

    @Override
    public void render(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            Main.INSTANCE.setScreen(new GameScreen());
        Gdx.gl.glClearColor(0,0,0,1); // Clears screen with black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
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
