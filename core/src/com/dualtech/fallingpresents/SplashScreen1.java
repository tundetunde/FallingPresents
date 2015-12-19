package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by tunde_000 on 14/12/2015.
 */
public class SplashScreen1 implements Screen {
    private SpriteBatch batch;
    private GameStateManager gsm;
    OrthographicCamera camera;
    Texture bgTexture;
    TextureRegion textureRegion;
    Sprite bgSprite;

    public SplashScreen1(final GameStateManager gsm){
        super();
        camera = new OrthographicCamera();
        //Gdx.gl.glClearColor(0, 1, 0, 1);
        this.gsm = gsm;
        batch = new SpriteBatch();
        bgTexture = AssetLoader.splash;
        textureRegion = new TextureRegion(bgTexture, 0, 0, 512, 301);
        bgSprite = new Sprite(bgTexture);
        //bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //bgSprite.draw(batch);
        //batch.draw( textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
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
