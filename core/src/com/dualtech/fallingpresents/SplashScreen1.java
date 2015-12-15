package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by tunde_000 on 14/12/2015.
 */
public class SplashScreen1 implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont shadow;
    private GameStateManager gsm;
    OrthographicCamera camera;

    public SplashScreen1(final GameStateManager gsm){
        super();
        camera = new OrthographicCamera();
        Gdx.gl.glClearColor(1, 1, 0, 1);
        this.gsm = gsm;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(1.2f, 1.2f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(1.2f, 1.2f);
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        String title = "DUAL DIGITAL";
        shadow.draw(batch, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 1000), (FallingPresentsGame.HEIGHT / 8) * 3);
        font.draw(batch, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 1000), (FallingPresentsGame.HEIGHT / 8) * 3);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
