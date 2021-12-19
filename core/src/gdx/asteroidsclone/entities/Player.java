package gdx.asteroidsclone.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class Player extends Entity {

    private static final float SPEED = Utils.toPixel(500f);
    private static final float TURNING_RATE = Utils.toPixel(300f);
    private static final float DAMPENING = 1f;
    private Polygon shape;
    private float hitboxWidth = Utils.toWorld(50);
    private float hitboxHeight = Utils.toWorld(50);

    public Player(int x, int y) {
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.bd.linearDamping = DAMPENING;
        this.bd.angularDamping = DAMPENING;

        this.body = GameScreen.world.createBody(this.bd);

        this.ps = new PolygonShape();
        this.ps.set(calculateHitbox());
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 0f;

        this.body.createFixture(this.fd);
        ps.dispose();

        shape = new Polygon();
        shape.setOrigin(x, y);
        shape.setPosition(x, y);
        shape.setVertices(calculateVertices());
    }

    @Override
    public void update() {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        float angle = body.getAngle() + MathUtils.PI / 2;
        Vector2 direction = new Vector2(SPEED * MathUtils.cos(angle), SPEED * MathUtils.sin(angle));
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            body.applyForceToCenter(direction, true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            body.applyTorque(TURNING_RATE,true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            body.applyTorque(-TURNING_RATE,true);

        if(x > Main.INSTANCE.getScreenWidth()) {
            body.setTransform(0f, body.getPosition().y,body.getAngle());
        } else if(x < 0) {
            body.setTransform(Utils.toWorld(Main.INSTANCE.getScreenWidth()), body.getPosition().y,body.getAngle());
        }

        if(y > Main.INSTANCE.getScreenHeight()) {
            body.setTransform(body.getPosition().x, 0f, body.getAngle());
        } else if(y < 0) {
            body.setTransform(body.getPosition().x, Utils.toWorld(Main.INSTANCE.getScreenHeight()), body.getAngle());
        }
//
    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x);
        int y = Utils.toPixel(body.getPosition().y);
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        sr.translate(x, y,0);
        sr.rotate(0,0,1,rot);
        sr.polygon(shape.getVertices());
        sr.identity();
    }

    private float[] calculateVertices() {
        float[] vertices = new float[8];
        float scale = Utils.toPixel(5);
        vertices[0] = scale * MathUtils.cos(MathUtils.PI / 2);
        vertices[1] = scale * MathUtils.sin(MathUtils.PI / 2);
        vertices[2] = scale * MathUtils.cos(MathUtils.PI + MathUtils.PI / 4);
        vertices[3] = scale * MathUtils.sin(MathUtils.PI + MathUtils.PI / 4);
        vertices[4] = scale / 3 * MathUtils.cos(-MathUtils.PI / 2);
        vertices[5] = scale / 3 * MathUtils.sin(-MathUtils.PI / 2);
        vertices[6] = scale * MathUtils.cos(-MathUtils.PI / 4);
        vertices[7] = scale * MathUtils.sin(-MathUtils.PI / 4);
        return vertices;
    }

    private float[] calculateHitbox() {
        float[] vertices = new float[6];
        float scale = Utils.toWorld(50);
        vertices[0] = scale * MathUtils.cos(MathUtils.PI / 2);
        vertices[1] = scale * MathUtils.sin(MathUtils.PI / 2);
        vertices[2] = scale * MathUtils.cos(MathUtils.PI + MathUtils.PI / 4);
        vertices[3] = scale * MathUtils.sin(MathUtils.PI + MathUtils.PI / 4);
        vertices[4] = scale * MathUtils.cos(-MathUtils.PI / 4);
        vertices[5] = scale * MathUtils.sin(-MathUtils.PI / 4);
        return vertices;
    }
}
