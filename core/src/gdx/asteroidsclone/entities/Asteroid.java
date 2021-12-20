package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.Main;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class Asteroid extends Entity {

    private static final int VERTICES = 7;

    private Polygon shape;
    private AsteroidType type;

    public Asteroid(int x, int y, AsteroidType type) {
        long seed = System.currentTimeMillis();
        this.type = type;
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.DynamicBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.body = GameScreen.world.createBody(this.bd);

        this.ps = new PolygonShape();
        this.ps.set(calculateVertices(false, seed));
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 1f;
        this.fd.filter.groupIndex = 1;

        this.body.createFixture(this.fd);
        ps.dispose();

        shape = new Polygon();
        shape.setOrigin(x, y);
        shape.setPosition(x, y);
        shape.setVertices(calculateVertices(true, seed));
    }

    @Override
    public void update() {
        if(Utils.toPixel(body.getPosition().x) > Main.INSTANCE.getScreenWidth()) {
            body.setTransform(0f,body.getPosition().y, body.getAngle());
        } else if(body.getPosition().x < 0f) {
            body.setTransform(Utils.toWorld(Main.INSTANCE.getScreenWidth()),body.getPosition().y, body.getAngle());
        }
        if(Utils.toPixel(body.getPosition().y) > Main.INSTANCE.getScreenHeight()) {
            body.setTransform(body.getPosition().x, 0f, body.getAngle());
        } else if(body.getPosition().y < 0f) {
            body.setTransform(body.getPosition().x, Utils.toWorld(Main.INSTANCE.getScreenHeight()), body.getAngle());
        }
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

    private float[] calculateVertices(boolean inPixels, long seed) {
        float[] vertices = new float[VERTICES * 2];
        float angle = (2 * MathUtils.PI) / VERTICES;
        int minRadius = 0; // Pixels
        int maxRadius = 0; // Pixels
        switch(type) {
            case SMALL:
                maxRadius = 30;
                minRadius = 5;
                break;
            case MEDIUM:
                maxRadius = 70;
                minRadius = 50;
                break;
            case LARGE:
                maxRadius = 90;
                minRadius = 70;
        }
        float currentAngle = 0;
        random.setSeed(seed);
        for(int i = 0; i < VERTICES * 2; i += 2) {
            float radius = Math.min(maxRadius, Math.max(random.nextInt(maxRadius + 1), minRadius));
            if(!inPixels)
                radius = Utils.toWorld(radius);
            vertices[i] = radius * MathUtils.cos(currentAngle);
            vertices[i + 1] = radius * MathUtils.sin(currentAngle);
            currentAngle += angle;
        }
        return vertices;
    }
}
