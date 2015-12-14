package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class SplashScreen extends State{
    private BitmapFont font;
    private BitmapFont shadow;

    protected SplashScreen(final GameStateManager gcm) {
        super(gcm);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        AssetLoader.load();
        camera.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.45f, .45f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.45f, .45f);
        Timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                //changeScreen();
                gcm.push(new Menu(gcm));
            }

        }, 3);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        String title = "FALLING PRESENTS";
        shadow.draw(sb, title, Game.WIDTH / 4 - (title.length() * 8), (Game.HEIGHT / 8) * 3);
        font.draw(sb, title, Game.WIDTH / 4 - (title.length() * 8), (Game.HEIGHT / 8) * 3);
        sb.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        shadow.dispose();
    }
}
