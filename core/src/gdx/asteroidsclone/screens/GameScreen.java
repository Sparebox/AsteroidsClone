package gdx.asteroidsclone.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.entities.AsteroidManager;
import gdx.asteroidsclone.entities.Entity;
import gdx.asteroidsclone.entities.PhysObstacle;
import gdx.asteroidsclone.entities.Player;
import gdx.asteroidsclone.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class GameScreen extends ScreenAdapter {

    public static final int VEL_ITERATIONS = 6;
    public static final int POS_ITERATIONS = 3;
    public static final float FPS = 60f;

    public static World world;

    private Set<Entity> entities;
    private Set<Entity> entitiesToDelete;
    private Set<Entity> entitiesToAdd;
    private Player player;
    private Camera camera;
    private ShapeRenderer sr;
    private AsteroidManager asteroidManager;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(Camera camera) {
        Entity.gameScreen = this;
        this.camera = camera;
        this.camera.translate(Main.INSTANCE.getScreenWidth() / 2f, Main.INSTANCE.getScreenHeight() / 2f, 0);
        this.camera.update();
        this.sr = new ShapeRenderer();
        this.sr.setProjectionMatrix(camera.combined);
        world = new World(new Vector2(0, 0), false);
        this.debugRenderer = new Box2DDebugRenderer();
        this.asteroidManager = new AsteroidManager(this);
        this.entities = new HashSet<>();
        this.entitiesToDelete = new HashSet<>();
        this.entitiesToAdd = new HashSet<>();
        this.player = new Player(Main.INSTANCE.getScreenWidth() / 2, Main.INSTANCE.getScreenHeight() / 2);
        this.entities.add(player);
    }

    private void update() {
        world.step(1f / FPS, VEL_ITERATIONS, POS_ITERATIONS);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        camera.update();
        if(!entitiesToDelete.isEmpty()) {
            for(Entity e : entitiesToDelete) {
                world.destroyBody(e.getBody());
                entities.remove(e);
            }
            entitiesToDelete.clear();
        }
        if(!entitiesToAdd.isEmpty()) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }
        for(Entity e : entities) {
            e.update();
        }
        asteroidManager.update();
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
        //debugRenderer.render(world, camera.combined.scl(Utils.PPM));
    }

    @Override
    public void dispose() {
        sr.dispose();
        debugRenderer.dispose();
        for(Entity e : entities) {
            e.dispose();
        }
        for(Entity e : entitiesToDelete) {
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
}
