package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.*;
import gdx.asteroidsclone.entities.particles.Bullet;
import gdx.asteroidsclone.physics.CustomContactListener;

import java.util.HashSet;
import java.util.Set;

public class GameScreen extends ScreenAdapter {

    public static final int VEL_ITERATIONS = 6;
    public static final int POS_ITERATIONS = 3;
    public static final float FPS = 60f;

    public static World world;

    private final int FONT_SIZE = 30;
    private final Color FONT_COLOR = Color.WHITE;

    private Set<Entity> entities;
    private Set<Entity> entitiesToDelete;
    private Set<Entity> entitiesToAdd;
    private Player player;
    private Camera gameCamera;
    private Camera UIcamera;
    private ShapeRenderer sr = Main.INSTANCE.sr;
    private SpriteBatch sb = Main.INSTANCE.sb;
    private FreeTypeFontGenerator fontGenerator = Main.INSTANCE.fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = Main.INSTANCE.fontParameter;
    private BitmapFont font;
    private AsteroidFactory asteroidFactory;
    private Box2DDebugRenderer debugRenderer;
    private float secondsSinceStart;
    private boolean gameOver = false;
    private boolean gameVictory = false;

    public GameScreen() {
        Entity.gameScreen = this;
        gameCamera = new OrthographicCamera(Main.INSTANCE.WORLD_WIDTH, Main.INSTANCE.WORLD_HEIGHT);
        gameCamera.translate(Main.INSTANCE.WORLD_WIDTH / 2f, Main.INSTANCE.WORLD_HEIGHT / 2f, 0);
        gameCamera.update();
        UIcamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        UIcamera.translate(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        UIcamera.update();
        sr.setProjectionMatrix(gameCamera.combined);
        sb.setProjectionMatrix(UIcamera.combined);
        fontParameter.size = FONT_SIZE;
        fontParameter.color = FONT_COLOR;
        font = fontGenerator.generateFont(fontParameter);
        world = new World(Vector2.Zero, false);
        world.setContactListener(new CustomContactListener(this));
        debugRenderer = new Box2DDebugRenderer();
        asteroidFactory = new AsteroidFactory(this);
        entities = new HashSet<>();
        entitiesToDelete = new HashSet<>();
        entitiesToAdd = new HashSet<>();
        if(Main.SETTINGS.isBotEnabled())
            player = new Bot(Main.INSTANCE.WORLD_WIDTH / 2, Main.INSTANCE.WORLD_HEIGHT / 2);
        else
            player = new Player(Main.INSTANCE.WORLD_WIDTH / 2, Main.INSTANCE.WORLD_HEIGHT / 2);
        entitiesToAdd.add(player);
        Gdx.input.setCursorCatched(true);
        MenuScreen.THEME.stop();
    }

    private void update(float deltaTime) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Main.INSTANCE.setScreen(new MenuScreen());
            return;
        }
        world.step(1f / FPS, VEL_ITERATIONS, POS_ITERATIONS);
        updateEntities(deltaTime);
        asteroidFactory.update();
    }

    @Override
    public void render(float deltaTime) {
        if(gameOver) {
            if(player.getScore() > Main.SETTINGS.getTopScore() && !(player instanceof Bot))
                Main.SETTINGS.setTopScore(player.getScore());
            Main.INSTANCE.setScreen(new GameOverScreen(player, asteroidFactory.getCurrentLevel(), gameVictory, secondsSinceStart));
            return;
        }
        update(deltaTime);
        Gdx.gl.glClearColor(0,0,0,1); // Clears screen with black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for(Entity e : entities) {
            e.render(sr);
        }
        sr.end();
        sb.begin();
        for(Entity e : entities) {
            e.render(sb);
        }
        drawHUD();
        sb.end();
//        debugRenderer.setDrawVelocities(true);
//        debugRenderer.render(world, gameCamera.combined);
        secondsSinceStart += deltaTime;
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        for(Entity e : entities) {
            e.dispose();
        }
        for(Entity e : entitiesToDelete) {
            if(e.getBody() == null)
                continue;
            world.destroyBody(e.getBody());
            entities.remove(e);
        }
        entities.clear();
        entitiesToDelete.clear();
        font.dispose();
        debugRenderer.dispose();
        world.dispose();
    }

    public void endGame(boolean victory) {
        gameVictory = victory;
        gameOver = true;
    }

    private void updateEntities(float deltaTime) {
        if(!entitiesToDelete.isEmpty()) {
            for(Entity e : entitiesToDelete) {
                if(e.getBody() != null) {
                    world.destroyBody(e.getBody());
                    entities.remove(e);
                } else if(e instanceof ScoreIndicator)
                    entities.remove(e);
            }
            entitiesToDelete.clear();
        }
        if(!entitiesToAdd.isEmpty()) {
            for(Entity e : entitiesToAdd) {
                if(e.getBd() != null) {
                    e.setBody(world.createBody(e.getBd()));
                    e.getBody().createFixture(e.getFd());
                    e.getBody().setUserData(e);
                    if(e.getCs() != null)
                        e.getCs().dispose();
                    else if(e.getPs() != null)
                        e.getPs().dispose();
                    entities.add(e);
                } else if(e instanceof ScoreIndicator)
                    entities.add(e);
            }
            entitiesToAdd.clear();
        }
        for(Entity e : entities) {
            e.update(deltaTime);
        }
    }

    private void drawHUD() {
        font.draw(sb, "Score: "+player.getScore(), 10, Gdx.graphics.getHeight() - 5);
        font.draw(sb, "Lives: "+player.getLives(), 10, Gdx.graphics.getHeight() - 5 - font.getLineHeight());
        font.draw(sb, asteroidFactory.getCurrentLevel().toString(), 10,Gdx.graphics.getHeight() - 5 - 2 * font.getLineHeight());
    }

    // Getters and setters

    public boolean isGameOver() {
        return gameOver;
    }

    public float getSecondsSinceStart() {
        return secondsSinceStart;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Set<Entity> getEntitiesToDelete() {
        return entitiesToDelete;
    }

    public Set<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    public Player getPlayer() { // TODO: Add proper bot implementation
        return player;
    }

    public Camera getGameCamera() {
        return gameCamera;
    }

    public Camera getUIcamera() {
        return UIcamera;
    }
}
