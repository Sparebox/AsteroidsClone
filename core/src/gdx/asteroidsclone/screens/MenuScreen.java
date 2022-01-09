package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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
import gdx.asteroidsclone.entities.AsteroidType;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.entities.MenuAsteroid;

import java.util.HashSet;


public class MenuScreen extends ScreenAdapter {

    public static final int BUTTON_WIDTH = 100;
    public static final int FONT_SIZE = 50;
    public static final Music THEME = Main.INSTANCE.assetManager.get(Assets.THEME_SFX);

    private static final int BG_ASTEROIDS_NUM = 20;

    private static World world;
    private static HashSet<MenuAsteroid> backgroundEntities;

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
    private ShapeRenderer sr = Main.INSTANCE.sr;
    private boolean closingApp;

    public MenuScreen() {
        stage = new Stage(new StretchViewport(Main.INSTANCE.guiWidth, Main.INSTANCE.guiHeight));
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
                disposeWorld();
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
                closingApp = true;
                disposeWorld();
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
        createBackgroundEntities();
    }

    @Override
    public void render(float deltaTime) {
        if(closingApp)
            return;
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            disposeWorld();
            Gdx.app.exit();
            return;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            disposeWorld();
            Main.INSTANCE.setScreen(new GameScreen());
            return;
        }
        Gdx.gl.glClearColor(0,0,0,1); // Clears screen with black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Line);
        renderBackground(sr, deltaTime);
        sr.end();
        stage.act(deltaTime);
        stage.draw();
    }

    private void renderBackground(ShapeRenderer sr, float deltaTime) {
        world.step(1f/GameScreen.FPS, GameScreen.VEL_ITERATIONS, GameScreen.POS_ITERATIONS);
        for(Entity e : backgroundEntities) {
            e.update(deltaTime);
            e.render(sr);
        }
    }

    private void createBackgroundEntities() {
        sr.setProjectionMatrix(stage.getCamera().combined);
        if(world != null)
            return;
        world = new World(Vector2.Zero, false);
        backgroundEntities = new HashSet<>();
        for(int i = 0; i < BG_ASTEROIDS_NUM; i++) {
            int x = MathUtils.random(0, (int)Main.INSTANCE.guiWidth);
            int y = MathUtils.random(0, (int)Main.INSTANCE.guiHeight + 500);
            Vector2 vel = new Vector2(0, -MathUtils.random(20,70));
            AsteroidType type = MathUtils.randomBoolean() ? AsteroidType.MEDIUM : AsteroidType.LARGE;
            MenuAsteroid asteroid = new MenuAsteroid(x, y, type);
            asteroid.getBd().angularVelocity =
                    MathUtils.randomBoolean() ? MathUtils.random(0f,1f) : -MathUtils.random(0f,1f);
            asteroid.getBd().linearVelocity.set(vel);
            asteroid.setBody(world.createBody(asteroid.getBd()));
            backgroundEntities.add(asteroid);
        }
    }

    private void disposeWorld() {
        for(var asteroid : backgroundEntities) {
            world.destroyBody(asteroid.getBody());
        }
        backgroundEntities.clear();
        world.dispose();
        world = null;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }

}
