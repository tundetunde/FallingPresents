package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tunde_000 on 12/12/2015.
 */
public class Menu extends State {

    private Texture background;
    private ImageButton playButton,leaderBoardButton,rateButton, muteButton, shareButton;
    Stage stage;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    private BitmapFont fontTitle;
    boolean isLeaderboardOn;

    public Menu(final GameStateManager gcm) {
        super(gcm);
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        background = AssetLoader.background;
        fontTitle = AssetLoader.font;
        fontTitle.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        initializeButtons();
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
        FallingPresentsGame.adsControl.hideBannerAd();
    }

    public void initializeButtons(){
        playButton = new ImageButton(AssetLoader.playStyle);
        playButton.setPosition(cameraWidth / 6 * 2, cameraHeight / 2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Button Clicked");
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
                FallingPresentsGame.activityMethods.shareScore();
            }
        });

        if(!AssetLoader.isSoundOn()){muteButton = new ImageButton(AssetLoader.muteStyle);}
        else{muteButton = new ImageButton(AssetLoader.soundStyle);}
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
                //Leaderboard.leaderboardList = FallingPresentsGame.activityMethods.postLeaderboard();
                //gcm.set(new Leaderboard(gcm));
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
    protected void handleInput() {}

    @Override
    public void update(float dt) {
        handleInput();
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
        String title = "Main Menu";
        fontTitle.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 28), (FallingPresentsGame.HEIGHT / 8) * 3);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
