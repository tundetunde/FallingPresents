package com.dualtech.fallingpresents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.event.InputEvent;

/**
 * Created by tunde_000 on 12/12/2015.
 */
public class Menu extends State {

    private Texture background;
    private TextButton playButton;
    private TextButton leaderBoardButton;
    TextButton.TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    Stage stage;
    BitmapFont font;
    int cameraWidth = Game.WIDTH / 2;
    int cameraHeight = Game.HEIGHT / 2;

    public Menu(final GameStateManager gcm) {
        super(gcm);
        camera.setToOrtho(false, Game.WIDTH / 2, Game.HEIGHT / 2);
        background = new Texture("bgting1.png");
        font = new BitmapFont();
        skin = new Skin();
        stage = new Stage();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("ZSjxE");
        textButtonStyle.down = skin.getDrawable("ZSjxE");
        textButtonStyle.checked = skin.getDrawable("ZSjxE");
        initializeButtons();
        stage.addActor(playButton);
        stage.addActor(leaderBoardButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void initializeButtons(){
        playButton = new TextButton("PLAY", textButtonStyle);
        playButton.setPosition(cameraWidth / 6, 80);
        playButton.setSize(100, 30);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Button Clicked");
                gcm.set(new PlayGame(gcm));
            }
        });

        leaderBoardButton = new TextButton("Leaderboard", textButtonStyle);
        leaderBoardButton.setPosition((cameraWidth / 6) * 3 + 20, 80);
        leaderBoardButton.setSize(100, 30);
        leaderBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("LeaderBoard: Button Clicked");
                gcm.set(new PlayGame(gcm));
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
        sb.end();
        stage.getViewport().setCamera(camera);
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}
