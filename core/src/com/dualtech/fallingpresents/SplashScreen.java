package com.dualtech.fallingpresents;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class SplashScreen extends State{

    protected SplashScreen(final GameStateManager gcm) {
        super(gcm);
        AssetLoader.load();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                gcm.push(new Menu(gcm));
            }
        }, 3);
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch sb) {}

    @Override
    public void dispose() {}
}
