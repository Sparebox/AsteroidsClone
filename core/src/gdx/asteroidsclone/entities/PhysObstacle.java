package gdx.asteroidsclone.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import gdx.asteroidsclone.screens.GameScreen;
import gdx.asteroidsclone.utils.Utils;

public class PhysObstacle extends Entity {

    private float width;
    private float height;

    public PhysObstacle(int x, int y, int width, int height) {
        this.width = Utils.toWorld(width);
        this.height = Utils.toWorld(height);
        this.bd = new BodyDef();
        this.bd.type = BodyDef.BodyType.StaticBody;
        this.bd.position.set(Utils.toWorld(x), Utils.toWorld(y));
        this.bd.allowSleep = true;

        this.body = GameScreen.world.createBody(this.bd);

        this.ps = new PolygonShape();
        this.ps.setAsBox(this.width/2f,this.height/2f);
        this.fd = new FixtureDef();
        this.fd.shape = ps;
        this.fd.density = 1f;
        this.fd.friction = 0f;
        this.fd.restitution = 0f;

        this.body.createFixture(this.fd);
        ps.dispose();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(ShapeRenderer sr) {
        int x = Utils.toPixel(body.getPosition().x - width / 2);
        int y = Utils.toPixel(body.getPosition().y - height / 2);
        sr.rect(x, y, Utils.toPixel(width), Utils.toPixel(height));
    }
}
