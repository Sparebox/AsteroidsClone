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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.*;
import gdx.asteroidsclone.physics.CustomContactListener;

import java.util.HashSet;
import java.util.Set;

public class GameScreen extends ScreenAdapter {

    public static final int VEL_ITERATIONS = 6;
    public static final int POS_ITERATIONS = 3;
    public static final float FPS = 60f;
    public static final int FONT_SIZE = 30;
    public static final Color FONT_COLOR = Color.WHITE;

    public static World world;

    private Set<Entity> entities;
    private Set<Entity> entitiesToDelete;
    private Set<Entity> entitiesToAdd;
    private Player player;
    private Camera gameCamera;
    private ShapeRenderer sr;
    private SpriteBatch sb;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private AsteroidFactory asteroidFactory;
    private Box2DDebugRenderer debugRenderer;
    private Camera UIcamera;

    public GameScreen(Camera camera) {
        Entity.gameScreen = this;
        this.gameCamera = camera;
        this.UIcamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.UIcamera.translate(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        this.UIcamera.update();
        this.sr = new ShapeRenderer();
        this.sr.setProjectionMatrix(gameCamera.combined);
        this.sb = new SpriteBatch();
        this.sb.setProjectionMatrix(UIcamera.combined);
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/VCR_OSD_MONO.ttf"));
        this.fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontParameter.size = FONT_SIZE;
        this.fontParameter.color = FONT_COLOR;
        this.font = fontGenerator.generateFont(this.fontParameter);
        world = new World(Vector2.Zero, false);
        world.setContactListener(new CustomContactListener(this));
        this.debugRenderer = new Box2DDebugRenderer();
        this.asteroidFactory = new AsteroidFactory(this);
        this.entities = new HashSet<>();
        this.entitiesToDelete = new HashSet<>();
        this.entitiesToAdd = new HashSet<>();
        this.player = new Player(Main.WORLD_WIDTH / 2, Main.WORLD_HEIGHT / 2);
        this.entitiesToAdd.add(player);
    }

    private void update() {
        world.step(1f / FPS, VEL_ITERATIONS, POS_ITERATIONS);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if(!entitiesToDelete.isEmpty()) {
            for(Entity e : entitiesToDelete) {
                world.destroyBody(e.getBody());
                entities.remove(e);
            }
            entitiesToDelete.clear();
        }
        if(!entitiesToAdd.isEmpty()) {
            for(Entity e : entitiesToAdd) {
                e.setBody(world.createBody(e.getBd()));
                e.getBody().createFixture(e.getFd());
                e.getBody().setUserData(e);
                if(e.getCs() != null)
                    e.getCs().dispose();
                else if(e.getPs() != null)
                    e.getPs().dispose();
                entities.add(e);
            }
            entitiesToAdd.clear();
        }
        for(Entity e : entities) {
            e.update();
        }
        asteroidFactory.update();
    }

    @Override
    public void render(float deltaTime) {
        update();
        Gdx.gl.glClearColor(0,0,0,1); // Clears screen with black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for(Entity e : entities) {
            e.render(sr);
        }
        sr.end();
        sb.begin();
        font.draw(sb, "Score: "+player.getScore(), 10, Gdx.graphics.getHeight() - 5);
        font.draw(sb, "Lives: "+player.getLives(), 10, Gdx.graphics.getHeight() - 5 - font.getLineHeight());
        font.draw(sb, asteroidFactory.getCurrentLevel().toString(), 10,Gdx.graphics.getHeight() - 5 - 2 * font.getLineHeight());
        sb.end();
        //debugRenderer.render(world, gameCamera.combined);
    }

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
        debugRenderer.dispose();
        for(Entity e : entities) {
            e.dispose();
        }
        for(Entity e : entitiesToDelete) {
            if(e.getBody() == null)
                continue;
            world.destroyBody(e.getBody());
            entities.remove(e);
        }
        entitiesToDelete.clear();
        world.dispose();
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

    public Player getPlayer() {
        return player;
    }
}
