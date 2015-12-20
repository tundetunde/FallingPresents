package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tunde_000 on 13/12/2015.
 */
public class EndGame extends State {
    private Texture background;
    private ChristmasPresent christmasPresent;
    private Trolley trolley;
    private BitmapFont font;
    private BitmapFont scorefont;
    private BitmapFont shadow;
    private Label.LabelStyle labelStyle;
    private ImageButton playButton,leaderBoardButton,rateButton, muteButton, shareButton;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    long score;
    Stage stage;
    private Label scoreBoard;

    protected EndGame(GameStateManager gcm, Vector3 presentPosition, Vector3 trolleyPosition, long score) {
        super(gcm);
        if(FallingPresentsGame.adsControl.isWifiConnected())
            FallingPresentsGame.adsControl.showBannerAd();
        christmasPresent = new ChristmasPresent((int)presentPosition.x, (int)presentPosition.y);
        trolley = new Trolley((int)trolleyPosition.x, (int)trolleyPosition.y);
        background = AssetLoader.background;
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        font = AssetLoader.font;
        font.getData().setScale(1.2f, 1.2f);
        shadow = AssetLoader.shadow;
        shadow.getData().setScale(1.2f, 1.2f);
        scorefont = new BitmapFont(Gdx.files.internal("text.fnt"));
        scorefont.getData().setScale(0.6f, 0.6f);
        stage = new Stage();
        labelStyle = new Label.LabelStyle(scorefont, Color.WHITE);
        initializeButtons();
        this.score = score;
        String scoreString = "Score: " + score + "\nHigh Score: " + AssetLoader.getHighScore();
        scoreBoard = new Label(scoreString, labelStyle);
        scoreBoard.setPosition((cameraWidth / 2) - (scoreBoard.getWidth() / 2), cameraHeight / 2 + 30);
        //scoreBoard.setSize(300, 500);
        stage.addActor(scoreBoard);
        stage.addActor(playButton);
        stage.addActor(shareButton);
        stage.addActor(muteButton);
        stage.addActor(rateButton);
        stage.addActor(leaderBoardButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void initializeButtons(){
        playButton = new ImageButton(AssetLoader.playStyle);
        playButton.setPosition(cameraWidth / 6 * 2, cameraHeight / 4 - 40);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Button Clicked");
                if(FallingPresentsGame.adsControl.isWifiConnected())
                    FallingPresentsGame.adsControl.hideBannerAd();
                gcm.set(new PlayGame(gcm));
            }
        });

        shareButton = new ImageButton(AssetLoader.playStyle);
        shareButton.setPosition(cameraWidth / 6 * 2, cameraHeight / 3);
        shareButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Share Button Clicked");
                if(FallingPresentsGame.adsControl.isWifiConnected())
                    FallingPresentsGame.adsControl.hideBannerAd();
            }
        });

        if(AssetLoader.isSoundOn()){muteButton = new ImageButton(AssetLoader.soundStyle);}
        else{muteButton = new ImageButton(AssetLoader.muteStyle);}
        muteButton.setPosition(cameraWidth - muteButton.getWidth() - 20, cameraHeight - muteButton.getHeight() - 20);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(AssetLoader.isSoundOn()){
                    AssetLoader.toggleSound(false);
                    muteButton.setStyle(AssetLoader.muteStyle);
                    System.out.println("Mute Clicked: Sound is off");
                }
                else{
                    AssetLoader.toggleSound(true);
                    muteButton.setStyle(AssetLoader.soundStyle);
                    System.out.println("Mute Clicked: Sound is on");
                }
            }
        });

        leaderBoardButton = new ImageButton(AssetLoader.scoreStyle);
        leaderBoardButton.setPosition((cameraWidth / 6) * 3 + 60, cameraHeight / 4 - 40);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("LeaderBoard: Button Clicked");
                FallingPresentsGame.resolver.submitScore(score);
            }
        });

        rateButton = new ImageButton(AssetLoader.rateStyle);
        rateButton.setPosition((cameraWidth / 6) * 3 + 60, cameraHeight / 3);
        rateButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Rate: Button Clicked");
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.dualtech.fallingpresents");
            }
        });
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(christmasPresent.getChristmasPresent(), christmasPresent.getPosition().x, christmasPresent.getPosition().y);
        sb.draw(trolley.getTrolley(), trolley.getPosition().x, trolley.getPosition().y);
        String over = "GAME OVER";
        shadow.draw(sb, over, (FallingPresentsGame.WIDTH / 4) - (over.length() * 27), (cameraHeight / 10) * 9);
        font.draw(sb, over, (FallingPresentsGame.WIDTH / 4) - (over.length() * 27), (cameraHeight / 10) * 9);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
