package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import gdx.asteroidsclone.Assets;
import gdx.asteroidsclone.Main;

public class ControlsScreen extends ScreenAdapter {

    private Stage stage;
    private Table table;
    private Skin skin = Main.INSTANCE.assetManager.get(Assets.SKIN);
    private FreeTypeFontGenerator fontGenerator = Main.INSTANCE.fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = Main.INSTANCE.fontParameter;
    private Label titleLabel;
    private TextButton backButton;
    private TextArea textArea;

    public ControlsScreen() {
        stage = new Stage(new StretchViewport(Main.INSTANCE.GUI_WIDTH, Main.INSTANCE.GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new MenuScreen());
            }
        });
        var style = new Label.LabelStyle();
        fontParameter.size = MenuScreen.FONT_SIZE;
        style.font = fontGenerator.generateFont(fontParameter);
        titleLabel = new Label("Controls", style);
        textArea = new TextArea("Forward: up arrow\n\nLeft: left arrow\n\nRight: right arrow\n\nSpace: fire",skin);
        textArea.setSize(200,200);
        textArea.setAlignment(Align.center);
        table.add(titleLabel);
        table.row().pad(10,0,10,0);
        table.add(textArea).size(textArea.getWidth(),textArea.getHeight());
        table.row().pad(10,0,10,0);
        table.add(backButton).width(MenuScreen.BUTTON_WIDTH);
    }

    @Override
    public void render(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Main.INSTANCE.setScreen(new MenuScreen());
        Gdx.gl30.glClearColor(0,0,0,1);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
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
