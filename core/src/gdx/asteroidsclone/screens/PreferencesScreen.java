package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import gdx.asteroidsclone.Assets;
import gdx.asteroidsclone.Main;

public class PreferencesScreen extends ScreenAdapter {

    private Stage stage;
    private Table table;
    private Skin skin = Main.INSTANCE.assetManager.get(Assets.SKIN);
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator = Main.INSTANCE.fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = Main.INSTANCE.fontParameter;
    private Label titleLabel;
    private Label volumeLabel;
    private Label volumeLevel;
    private Label difficultyLabel;
    private Label controlModeLabel;
    private Slider volumeSlider;
    private TextButton backButton;
    private TextButton resetHighScores;
    private SelectBox<String> difficultyBox;
    private SelectBox<String> controlBox;
    private CheckBox botBox;

    public PreferencesScreen() {
        stage = new Stage(new StretchViewport(Main.INSTANCE.guiWidth, Main.INSTANCE.guiHeight));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        fontParameter.size = MenuScreen.FONT_SIZE;
        font = fontGenerator.generateFont(fontParameter);
        var style = new Label.LabelStyle();
        style.font = font;
        titleLabel = new Label("Options", style);
        volumeLabel = new Label("Sound volume", skin);
        volumeLevel = new Label(Float.toString(Main.SETTINGS.getVolume()), skin);
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.SETTINGS.setVolume(volumeSlider.getValue());
                Main.INSTANCE.setScreen(new MenuScreen());
            }
        });
        resetHighScores = new TextButton("Reset highscores", skin);
        resetHighScores.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.SETTINGS.setTopScore(0);
                resetHighScores.setDisabled(true);
            }
        });
        volumeSlider = new Slider(0.0f, 1.0f, 0.1f, false, skin);
        volumeSlider.setValue(Main.SETTINGS.getVolume());
        volumeSlider.addListener((e) -> {
            volumeLevel.setText(Float.toString(MathUtils.floor(volumeSlider.getValue()*10)/10f));
            return false;
        });
        botBox = new CheckBox("Bot enabled", skin);
        botBox.setChecked(Main.SETTINGS.isBotEnabled());
        botBox.addListener((e) -> {
            Main.SETTINGS.setBotEnabled(botBox.isChecked());
            return false;
        });
        difficultyLabel = new Label("Difficulty:", skin);
        difficultyBox = new SelectBox<>(skin);
        difficultyBox.setItems("Easy", "Normal", "Hard");
        difficultyBox.setSelected(Main.SETTINGS.getDifficulty());
        difficultyBox.addListener((e) -> {
            Main.SETTINGS.setDifficulty(difficultyBox.getSelected());
            return false;
        });
        controlModeLabel = new Label("Control mode", skin);
        controlBox = new SelectBox<>(skin);
        controlBox.setItems("Arrow keys", "Mouse");
        controlBox.setSelected(Main.SETTINGS.getControlMode());
        controlBox.addListener((e) -> {
            Main.SETTINGS.setControlMode(controlBox.getSelected());
            return false;
        });
        table.add(titleLabel).colspan(3).pad(0,0,100,0);
        table.row();
        table.add(volumeLabel).pad(0,0,0,10).left();
        table.add(volumeLevel).pad(0,0,0,10).center().width(20);
        table.add(volumeSlider).right();
        table.row().pad(10,0,10,0);
        table.add(difficultyLabel).left();
        table.add(difficultyBox);
        table.row().pad(10,0,10,0);
        table.add(controlModeLabel).left();
        table.add(controlBox);
        table.row().pad(10,0,10,0);
        table.add(botBox).left();
        table.row().pad(10,0,10,0);
        table.add(resetHighScores).width(150).left();
        table.row().pad(10,0,10,0);
        table.add(backButton).width(MenuScreen.BUTTON_WIDTH).colspan(3);
    }

    @Override
    public void render(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Main.INSTANCE.setScreen(new MenuScreen());
        Gdx.gl30.glClearColor(0,0,0,1);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
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
