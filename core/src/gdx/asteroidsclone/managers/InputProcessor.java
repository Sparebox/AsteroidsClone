package gdx.asteroidsclone.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int key) {
        GameKeys.setKey(key, true);
        return true;
    }

    @Override
    public boolean keyUp(int key) {
        GameKeys.setKey(key, false);
        return true;
    }

}
