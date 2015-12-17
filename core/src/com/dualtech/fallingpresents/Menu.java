package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by tunde_000 on 12/12/2015.
 */
public class Menu extends State {

    private Texture background;
    private ImageButton playButton,leaderBoardButton,rateButton;
    TextureAtlas buttonAtlas;
    Stage stage;
    BitmapFont font;
    int cameraWidth = FallingPresentsGame.WIDTH / 2;
    int cameraHeight = FallingPresentsGame.HEIGHT / 2;
    private BitmapFont fontTitle;
    private BitmapFont shadow;

    public Menu(final GameStateManager gcm) {
        super(gcm);
        camera.setToOrtho(false, FallingPresentsGame.WIDTH / 2, FallingPresentsGame.HEIGHT / 2);
        background = AssetLoader.background;
        font = new BitmapFont();
        fontTitle = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontTitle.getData().setScale(1.2f, 1.2f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(1.2f, 1.2f);
        stage = new Stage();
        buttonAtlas = AssetLoader.buttonAtlas;
        initializeButtons();
        stage.addActor(playButton);
        stage.addActor(rateButton);
        stage.addActor(leaderBoardButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void initializeButtons(){
        playButton = new ImageButton(AssetLoader.playStyle);
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

        leaderBoardButton = new ImageButton(AssetLoader.scoreStyle);
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

        rateButton = new ImageButton(AssetLoader.rateStyle);
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
        /*if(Gdx.input.justTouched()){
            gcm.set(new PlayGame(gcm));
        }*/
    }

    @Override
    public void update(float dt) {
        handleInput();
        stage.act(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        String title = "MAIN MENU";
        shadow.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 24), (FallingPresentsGame.HEIGHT / 8) * 3);
        fontTitle.draw(sb, title, FallingPresentsGame.WIDTH / 4 - (title.length() * 24), (FallingPresentsGame.HEIGHT / 8) * 3);
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        //background.dispose();
        stage.dispose();
    }
}
