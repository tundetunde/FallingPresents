package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private BitmapFont shadow;
    TextButton.TextButtonStyle textButtonStyle;
    TextureAtlas buttonAtlas;
    private TextButton playButton;
    private TextButton leaderBoardButton;
    private TextButton rateButton;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    Stage stage;

    protected EndGame(GameStateManager gcm, Vector3 presentPosition, Vector3 trolleyPosition) {
        super(gcm);
        christmasPresent = new ChristmasPresent((int)presentPosition.x, (int)presentPosition.y);
        trolley = new Trolley((int)trolleyPosition.x, (int)trolleyPosition.y);
        background = AssetLoader.background;
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(1.2f, 1.2f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        buttonAtlas = AssetLoader.buttonAtlas;
        textButtonStyle = AssetLoader.normalTextButtonStyle;
        initializeButtons();
        stage.addActor(playButton);
        stage.addActor(rateButton);
        stage.addActor(leaderBoardButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void initializeButtons(){
        playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setPosition(cameraWidth / 6, cameraHeight / 3);
        //playButton.setSize(100, 30);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Button Clicked");
                gcm.set(new PlayGame(gcm));
            }
        });

        leaderBoardButton = new TextButton("Leaderboard", textButtonStyle);
        leaderBoardButton.setPosition((cameraWidth / 6) * 3 + 60, cameraHeight / 3);
        //leaderBoardButton.setSize(100, 30);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("LeaderBoard: Button Clicked");
                gcm.set(new PlayGame(gcm));
            }
        });

        rateButton = new TextButton("Rate", textButtonStyle);
        rateButton.setPosition(cameraWidth / 2 - (rateButton.getWidth() / 2), cameraHeight / 2);
        //leaderBoardButton.setSize(100, 30);
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
        shadow.draw(sb, over, (FallingPresentsGame.WIDTH / 4) - (over.length() * 27), (FallingPresentsGame.HEIGHT / 8) * 3);
        font.draw(sb, over, (FallingPresentsGame.WIDTH / 4) - (over.length() * 27), (FallingPresentsGame.HEIGHT / 8) * 3);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        shadow.dispose();
        stage.dispose();
    }
}
