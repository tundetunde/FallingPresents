package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

/**
 * Created by tunde_000 on 12/12/2015.
 */
public class PlayGame extends State {
    private ChristmasPresent christmasPresent;
    private Trolley trolley;
    private Texture background;
    private Random rand;
    private static long score;
    private BitmapFont font;
    private BitmapFont shadow;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    Stage stage = new Stage();

    protected PlayGame(GameStateManager gcm) {
        super(gcm);
        rand = new Random();
        AssetLoader.setMotionControl(true);
        christmasPresent = new ChristmasPresent(rand.nextInt(cameraWidth), cameraHeight);
        trolley = new Trolley(10, 0);
        background = AssetLoader.background;
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        score = 0;
        font = AssetLoader.font;
        font.getData().setScale(1.2f, 1.2f);
        shadow = AssetLoader.shadow;
        shadow.getData().setScale(1.2f, 1.2f);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {
        float y = Gdx.input.getAccelerometerY();
        trolley.move(y);
    }

    @Override
    public void update(float dt) {
        handleInput();
        christmasPresent.update(dt);
        if(christmasPresent.isHitGround()){
            gcm.set(new EndGame(gcm, christmasPresent.getPosition(), trolley.getPosition(), score));
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
            }
        }

        trolley.update(dt);
        if(trolley.isCollide(christmasPresent.getBounds())){
            AssetLoader.coin.play();
            christmasPresent = new ChristmasPresent(rand.nextInt(cameraWidth), cameraHeight);
            score++;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(christmasPresent.getChristmasPresent(), christmasPresent.getPosition().x, christmasPresent.getPosition().y);
        sb.draw(trolley.getTrolley(), trolley.getPosition().x, trolley.getPosition().y);
        String scoreString = Long.toString(score);
        shadow.draw(sb, scoreString, FallingPresentsGame.WIDTH / 4 - scoreString.length() * 10, (FallingPresentsGame.HEIGHT / 8) * 3);
        font.draw(sb, scoreString, FallingPresentsGame.WIDTH / 4 - scoreString.length() * 10, (FallingPresentsGame.HEIGHT / 8) * 3);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        System.out.println("FallingPresentsGame is Over");
    }
    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
