package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by tunde_000 on 20/12/2015.
 */
public class PauseScreen extends State {
    private Texture background;
    private ChristmasPresent christmasPresent;
    private Trolley trolley;
    private BitmapFont font;
    private BitmapFont scorefont;
    private Label.LabelStyle labelStyle;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    long score;
    Stage stage;
    private Label scoreBoard;

    protected PauseScreen(GameStateManager gcm) {
        super(gcm);
        if(FallingPresentsGame.adsControl.isWifiConnected())
            FallingPresentsGame.adsControl.showBannerAd();
        trolley = new Trolley(PlayGame.trolleyX, PlayGame.trolleyY);
        background = AssetLoader.background;
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        font = AssetLoader.font;
        scorefont = AssetLoader.scoreFont;
        scorefont.getData().setScale(0.6f, 0.6f);
        stage = new Stage();
        labelStyle = new Label.LabelStyle(scorefont, Color.WHITE);
        this.score = score;
        String scoreString = "Score: " + score + "\nHigh Score: " + AssetLoader.getHighScore();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            gcm.pop();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(trolley.getTrolley(), trolley.getPosition().x, trolley.getPosition().y);
        String scoreString = "PAUSED";
        font.draw(sb, scoreString, FallingPresentsGame.WIDTH / 4 - scoreString.length() * 10, (FallingPresentsGame.HEIGHT / 8) * 3);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
