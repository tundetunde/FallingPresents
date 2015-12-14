package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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
    //ParticleEffect snowEffect;

    protected PlayGame(GameStateManager gcm) {
        super(gcm);
        rand = new Random();
        christmasPresent = new ChristmasPresent(rand.nextInt((Game.WIDTH / 2)), 300);
        trolley = new Trolley(10, 0);
        background = new Texture("bgting1.png");
        camera.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        score = 0;
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.45f, .45f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.45f, .45f);
  /*      snowEffect = new ParticleEffect();
        snowEffect.load(Gdx.files.internal("snow_effect.txt"), Gdx.files.internal("particle.png"));
        snowEffect.getEmitters().first().setPosition(Game.WIDTH / 4, Game.HEIGHT / 2);*/
        //snowEffect.start();
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
            gcm.set(new EndGame(gcm, christmasPresent.getPosition(), trolley.getPosition()));
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
            }
        }

        trolley.update(dt);
        if(trolley.isCollide(christmasPresent.getBounds())){
            AssetLoader.coin.play();
            christmasPresent = new ChristmasPresent(rand.nextInt(Game.WIDTH / 2), 300);
            score++;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        //snowEffect.draw(sb, Gdx.graphics.getDeltaTime());
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(christmasPresent.getChristmasPresent(), christmasPresent.getPosition().x, christmasPresent.getPosition().y);
        sb.draw(trolley.getTrolley(), trolley.getPosition().x, trolley.getPosition().y);
        String scoreString = Long.toString(score);
        shadow.draw(sb, scoreString, Game.WIDTH / 4 - scoreString.length() * 10, (Game.HEIGHT / 8) * 3);
        font.draw(sb, scoreString, Game.WIDTH / 4 - scoreString.length() * 10, (Game.HEIGHT / 8) * 3);
        sb.end();
        /*if (snowEffect.isComplete())
            snowEffect.reset();*/
    }

    @Override
    public void dispose() {
        background.dispose();
        christmasPresent.dispose();
        trolley.dispose();
        font.dispose();
        shadow.dispose();
        AssetLoader.coin.dispose();
        System.out.println("Game is Over");
    }
    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
