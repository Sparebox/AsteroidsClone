package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.MathUtils;
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
import gdx.asteroidsclone.entities.Bot;
import gdx.asteroidsclone.entities.Level;
import gdx.asteroidsclone.entities.Player;

public class GameOverScreen extends ScreenAdapter {

    private Stage stage;
    private Table table;
    private Skin skin = Main.INSTANCE.assetManager.get(Assets.SKIN);
    private TextButton backButton;

    public GameOverScreen(Player player, Level currentLevel, boolean victory, float timeSinceStart) {
        stage = new Stage(new StretchViewport(Main.INSTANCE.GUI_WIDTH, Main.INSTANCE.GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        var titleLabel = new Label("", skin);
        if(victory)
            titleLabel.setText("Game won");
        else
            titleLabel.setText("Game lost");
        var scoreLabel = new Label("Score:", skin);
        var topScoreLabel = new Label("Player top score:", skin);
        var levelLabel = new Label(currentLevel.toString(), skin);
        backButton = new TextButton("Main Menu", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.INSTANCE.setScreen(new MenuScreen());
            }
        });
        var scoreVal = new Label(Integer.toString(player.getScore()), skin);
        var topScoreVal = new Label(Integer.toString(Main.SETTINGS.getTopScore()), skin);
        var playerOrBot = new Label("", skin);
        if(player instanceof Bot)
            playerOrBot.setText("Bot");
        else
            playerOrBot.setText("Player");
        int seconds = MathUtils.floor(timeSinceStart % 60);
        int minutes = MathUtils.floor(seconds / 60f);
        var timeLabel = new Label("Time:", skin);
        var time = new Label(minutes+":"+seconds, skin);
        table.add(titleLabel).colspan(2);
        table.row().pad(20,0,0,0);
        table.add(levelLabel).colspan(2);
        table.row().pad(10,0,10,0);
        table.add(playerOrBot).colspan(2);
        table.row().pad(10,0,10,0);
        table.add(scoreLabel).left();
        table.add(scoreVal).pad(0,20,0,0);
        table.row().pad(10,0,10,0);
        table.add(topScoreLabel).left();
        table.add(topScoreVal).pad(0,20,0,0);
        table.row().pad(10,0,10,0);
        table.add(timeLabel).left();
        table.add(time).pad(0,20,0,0);
        table.row().pad(10,0,10,0);
        table.add(backButton).width(MenuScreen.BUTTON_WIDTH).colspan(2);
        Gdx.input.setCursorCatched(false);
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
    public void dispose() {
        stage.dispose();
    }
}
