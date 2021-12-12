package gdx.asteroidsclone.managers;

import gdx.asteroidsclone.gamestates.GameState;

public class GameStateManager {

    private GameState gameState;

    public GameStateManager() {

    }

    public void setState(State gameState) {

    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }
}
