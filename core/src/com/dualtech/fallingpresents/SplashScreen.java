package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class SplashScreen extends State{
    private SpriteBatch batch;
    OrthographicCamera camera;
    Texture bgTexture;
    Sprite bgSprite;

    protected SplashScreen(final GameStateManager gcm) {
        super(gcm);
        AssetLoader.load();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        bgTexture = AssetLoader.splash;
        bgSprite = new Sprite(bgTexture);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        /*Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                gcm.push(new Menu(gcm));
            }
        }, 3);*/
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch sb) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgTexture, 0, 0, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        batch.end();}

    @Override
    public void dispose() {}
}
