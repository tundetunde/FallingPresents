package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class SplashScreen extends State{
    private BitmapFont font;
    private BitmapFont shadow;
    Texture bgTexture;
    TextureRegion textureRegion;
    Sprite bgSprite;

    protected SplashScreen(final GameStateManager gcm) {
        super(gcm);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        AssetLoader.load();
        bgTexture = new Texture("splash.png");
        bgTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textureRegion = new TextureRegion(bgTexture, 0, 0, 512, 301);
        bgSprite = new Sprite(bgTexture);
        //bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        font = new BitmapFont(Gdx.files.internal("images/text.fnt"));
        font.getData().setScale(1.2f, 1.2f);
        shadow = new BitmapFont(Gdx.files.internal("images/shadow.fnt"));
        shadow.getData().setScale(1.2f, 1.2f);
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
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        //bgSprite.draw(sb);
        sb.draw( textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        //String title = "DUAL DIGITAL";
        //shadow.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 25), (FallingPresentsGame.HEIGHT / 8) * 3);
        //font.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 25), (FallingPresentsGame.HEIGHT / 8) * 3);
        sb.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        shadow.dispose();
    }
}
