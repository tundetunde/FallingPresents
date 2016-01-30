package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    private Trolley trolley;
    private BitmapFont font, scorefont;
    private Label.LabelStyle labelStyle;
    private ImageButton playButton,leaderBoardButton,rateButton, muteButton, shareButton;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    long score;
    Stage stage;
    private Label scoreBoard;
    boolean isLeaderboardOn;

    protected EndGame(GameStateManager gcm, Vector3 presentPosition, Vector3 trolleyPosition, long score) {
        super(gcm);
        FallingPresentsGame.adsControl.showBannerAd();
        trolley = new Trolley((int)trolleyPosition.x, (int)trolleyPosition.y);
        background = AssetLoader.background;
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        font = AssetLoader.font;
        font.getData().setScale(1.2f, 1.2f);;
        scorefont = AssetLoader.scoreFont;
        scorefont.getData().setScale(0.8f, 0.8f);
        stage = new Stage();
        labelStyle = new Label.LabelStyle(scorefont, Color.WHITE);
        initializeButtons();
        this.score = score;
        String scoreString = "Score: " + score + "\nHigh Score: " + AssetLoader.getHighScore();
        scoreBoard = new Label(scoreString, labelStyle);
        scoreBoard.setPosition(((cameraWidth / 10) - 80), cameraHeight / 2 - 40);
        //scoreBoard.setSize(300, 500);
        stage.addActor(scoreBoard);
        stage.addActor(playButton);
        stage.addActor(shareButton);
        stage.addActor(muteButton);
        stage.addActor(rateButton);
        if(FallingPresentsGame.activityMethods.isLoggedInFB()){
            stage.addActor(leaderBoardButton);
            isLeaderboardOn = true;
        }else{
            isLeaderboardOn = false;
        }
        Gdx.input.setInputProcessor(stage);
        FallingPresentsGame.activityMethods.showFbButton();
        //FallingPresentsGame.adsControl.hideBannerAd();
    }

    public void initializeButtons(){
        playButton = new ImageButton(AssetLoader.playStyle);
        playButton.setPosition(cameraWidth / 6 * 2, cameraHeight / 2);
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

        shareButton = new ImageButton(AssetLoader.shareStyle);
        shareButton.setPosition(cameraWidth / 6 * 2, cameraHeight / 3 - 30);
        shareButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Share Button Clicked");
                if (FallingPresentsGame.adsControl.isWifiConnected())
                    FallingPresentsGame.adsControl.hideBannerAd();
                FallingPresentsGame.activityMethods.shareScore(score);
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
        leaderBoardButton.setPosition((cameraWidth / 6) * 3 + 60, cameraHeight / 3 - 30);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("LeaderBoard: Button Clicked");
                if(FallingPresentsGame.activityMethods.isLoggedInFB())
                    FallingPresentsGame.activityMethods.startLeaderboardActivity();
            }
        });

        rateButton = new ImageButton(AssetLoader.rateStyle);
        rateButton.setPosition((cameraWidth / 6) * 3 + 60, cameraHeight / 2);
        rateButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Rate: Button Clicked");
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.dualtech.fallingpresents.android");
            }
        });
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if(FallingPresentsGame.activityMethods.isLoggedInFB()){
            if(!isLeaderboardOn){
                stage.addActor(leaderBoardButton);
                isLeaderboardOn = true;
            }
        }else {
            if(isLeaderboardOn){
                leaderBoardButton.remove();
                isLeaderboardOn = false;
            }
        }
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(trolley.getTrolley(), trolley.getPosition().x, trolley.getPosition().y);
        String over = "Game Over";
        font.draw(sb, over, (FallingPresentsGame.WIDTH / 4) - (over.length() * 27), (cameraHeight / 10) * 8);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
